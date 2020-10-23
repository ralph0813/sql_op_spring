package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.domain.*;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SqlGenerator {
    @Autowired
    private TbMapper tbMapper;
    @Autowired
    private DtMapper dtMapper;
    @Autowired
    private DbMapper dbMapper;

    private static String head = "CREATE TABLE IF NOT EXISTS";
    private static String tail = "ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";
    public String createTb(TbDomain tbDomain) throws NullPointerException{
        StringBuilder foreignKeyStr = new StringBuilder("");
        StringBuilder result = new StringBuilder(String.format(head + " `%s` (", tbDomain.getName()));
        Optional<List<DtDomain>> dtsOp = Optional.ofNullable(tbDomain.getDts());
        if (!dtsOp.isPresent()) {
            return "-2";
        }
        dtsOp.ifPresent(dts -> {
            dts.stream().forEach(dtDomain -> {
                result.append(String.format("`%s` %s ", dtDomain.getName(), dtDomain.getType()));
                if (dtDomain.isAutoIncrement()) {
                    result.append("AUTO_INCREMENT ");
                }
                if (tbDomain.getDts().indexOf(dtDomain) < tbDomain.getDts().size() - 1) {
                    result.append(", ");
                }
                if (dtDomain.isForeignkey()) {
                    dtDomain.setForeignkey(false);
                    TbExample tbExample = new TbExample();
                    tbExample.createCriteria().andDbIdEqualTo(tbDomain.getDbId()).andNameEqualTo(dtDomain.getTgTb());
                    List<Tb> tbs_fk = tbMapper.selectByExample(tbExample);
                    if (!tbs_fk.isEmpty()) {
                        Tb tb_fk = tbs_fk.get(0);
                        DtExample dtExample = new DtExample();
                        dtExample.createCriteria().andTbIdEqualTo(tb_fk.getId()).andNameEqualTo(dtDomain.getTgDt());
                        List<Dt> dts_fk = dtMapper.selectByExample(dtExample);
                        if (!dts_fk.isEmpty()) {
                            foreignKeyStr.append(String.format(", CONSTRAINT `%s_%s_fk` ",
                                    tbDomain.getName(), dtDomain.getName()));
                            foreignKeyStr.append(String.format("FOREIGN KEY (`%s`) REFERENCES `%s` (`%s`)",
                                    dtDomain.getName(), dtDomain.getTgTb(), dtDomain.getTgDt()));
                            dtDomain.setForeignkey(true);
                        }
                    }
                }
            });
        });
//        for (DtDomain dtDomain : tbDomain.getDts()) {
//            result.append(String.format("`%s` %s ", dtDomain.getName(), dtDomain.getType()));
//            if (dtDomain.isAutoIncrement()) {
//                result.append("AUTO_INCREMENT ");
//            }
//            if (tbDomain.getDts().indexOf(dtDomain) < tbDomain.getDts().size() - 1) {
//                result.append(", ");
//            }
//        }
        List<String> names = tbDomain.getDts().stream().map(dtDomain -> dtDomain.getName()).collect(Collectors.toList());
        if (tbDomain.getPrimaryKey() != null && names.contains(tbDomain.getPrimaryKey())) {
            result.append(String.format(", PRIMARY KEY (`%s`)", tbDomain.getPrimaryKey()));
        }

        result.append(foreignKeyStr);
        result.append(") ").append(tail);
        return result.toString();
    }

    public String dropTb(String tbName) {
        return String.format("DROP TABLE `%s`;", tbName);
    }

    public String renameTb(String oldName, String newName) {
        return String.format("RENAME TABLE `%s` TO `%s`;", oldName, newName);
    }

    public String createDt(DtTemp dtTemp) {
        return String.format("ALTER TABLE `%s` ADD COLUMN `%s` %s;",
                dtTemp.getTbName(), dtTemp.getName(), dtTemp.getType());
    }

    //FOREIGN KEY (`nid`) REFERENCES `mytb1` (`id`);
    public String createFk(DtTemp dtTemp) {
        StringBuilder result = new StringBuilder(String.format("ALTER TABLE `%s` ADD CONSTRAINT ", dtTemp.getTbName()));
        result.append(String.format("`%s_%s_fk` ",
                dtTemp.getTbName(), dtTemp.getName()));
        result.append(String.format("FOREIGN KEY (`%s`) REFERENCES `%s` (`%s`);",
                dtTemp.getName(), dtTemp.getTgTb(), dtTemp.getTgDt()));
        return result.toString();
    }

    public String dropDt(DtTemp dtTemp) {
        return String.format("ALTER TABLE `%s` DROP COLUMN `%s`;",
                dtTemp.getTbName(), dtTemp.getName());
    }

    public String modifyDt(DtTemp dtTemp) {
        return String.format("ALTER TABLE `%s` CHANGE `%s` `%s` %s;",
                dtTemp.getTbName(), dtTemp.getOldName(), dtTemp.getName(), dtTemp.getType());
    }

    public String deleteFk(DtTemp dtTemp) {
        return String.format("ALTER TABLE `%s` DROP FOREIGN KEY `%s_%s_fk`;",
                dtTemp.getTbName(), dtTemp.getTbName(), dtTemp.getOldName());
    }

    public String listRc(RcListParam rcListParam) {
        Optional<List<ConditionCell>> conditionCells = Optional.ofNullable(rcListParam.getConditions());
        StringBuilder condition = new StringBuilder(" WHERE '1' = '1'");
        if (conditionCells.isPresent()) {
            for (ConditionCell conditionCell: conditionCells.get()) {
                StringBuilder oneCase = new StringBuilder();
                if (conditionCell.getLogic() == 0) {
                    oneCase.append(" AND");
                } else {
                    oneCase.append(" OR");
                }
                oneCase.append(String.format(" `%s` %s '%s'",
                        conditionCell.getName(), conditionCell.getSymbol(), conditionCell.getValue()));
                condition.append(oneCase);
            }
        }
        return String.format("SELECT * FROM `%s`" + condition.toString(),
                rcListParam.getName());
    }

    public String addRc(RcAddParam rcAddParam) {
        StringBuilder result = new StringBuilder(String.format("INSERT INTO `%s` (",
                rcAddParam.getName()));
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<ResultCell> row = rcAddParam.getRow();
        for (ResultCell resultCell: row) {
            names.append(String.format("`%s`", resultCell.getName()));
            values.append(String.format("'%s'", resultCell.getValue()));
            if (row.indexOf(resultCell) < row.size() - 1) {
                names.append(", ");
                values.append(", ");
            }
        }
        result.append(names);
        result.append(") VALUE (");
        result.append(values);
        result.append(");");
        return result.toString();
    }

    public String updateRc(RcUpdateParam rcUpdateParam) {
        StringBuilder result = new StringBuilder(String.format("UPDATE `%s` SET ",
                rcUpdateParam.getName()));
        StringBuilder values = new StringBuilder();
        StringBuilder conditionValues = new StringBuilder();
        List<ResultCell> row = rcUpdateParam.getRow();
        for (ResultCell resultCell: row) {
            values.append(String.format("`%s` = '%s'", resultCell.getName(), resultCell.getValue()));
            if (row.indexOf(resultCell) < row.size() - 1) {
                values.append(", ");
            }
        }
        List<ResultCell> conditions = rcUpdateParam.getConditions();
        for (ResultCell resultCell: conditions) {
            conditionValues.append(String.format("`%s` = '%s'", resultCell.getName(), resultCell.getValue()));
            if (conditions.indexOf(resultCell) < conditions.size() - 1) {
                conditionValues.append(" AND ");
            }
        }
        result.append(values);
        result.append(" WHERE ");
        result.append(conditionValues);
        result.append(";");
        return result.toString();
    }

    public String deleteRc(RcDeleteParam rcDeleteParam) {
        StringBuilder result = new StringBuilder(String.format("DELETE FROM `%s` WHERE ",
                rcDeleteParam.getName()));
        StringBuilder conditionValues = new StringBuilder();
        List<ResultCell> conditions = rcDeleteParam.getConditions();
        for (ResultCell resultCell: conditions) {
            conditionValues.append(String.format("`%s` = '%s'", resultCell.getName(), resultCell.getValue()));
            if (conditions.indexOf(resultCell) < conditions.size() - 1) {
                conditionValues.append(" AND ");
            }
        }
        result.append(conditionValues);
        result.append(";");
        return result.toString();
    }
}
