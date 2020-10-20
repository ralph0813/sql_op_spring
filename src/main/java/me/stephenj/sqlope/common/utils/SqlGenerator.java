package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.DtDomain;
import me.stephenj.sqlope.domain.DtParam;
import me.stephenj.sqlope.domain.DtTemp;
import me.stephenj.sqlope.domain.TbDomain;
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
                    tbExample.createCriteria().andDbidEqualTo(tbDomain.getDbId()).andNameEqualTo(dtDomain.getTgTb());
                    List<Tb> tbs_fk = tbMapper.selectByExample(tbExample);
                    if (!tbs_fk.isEmpty()) {
                        Tb tb_fk = tbs_fk.get(0);
                        DtExample dtExample = new DtExample();
                        dtExample.createCriteria().andTbidEqualTo(tb_fk.getId()).andNameEqualTo(dtDomain.getTgDt());
                        List<Dt> dts_fk = dtMapper.selectByExample(dtExample);
                        if (!dts_fk.isEmpty()) {
                            foreignKeyStr.append(String.format(", CONSTRAINT %s_%s_%s_fk ",
                                    tbDomain.getName(), dtDomain.getTgTb(), dtDomain.getTgDt()));
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
        result.append(String.format("%s_%s_%s_fk ",
                dtTemp.getTbName(), dtTemp.getTgTb(), dtTemp.getTgDt()));
        result.append(String.format("FOREIGN KEY (`%s`) REFERENCES `%s` (`%s`);",
                dtTemp.getName(), dtTemp.getTgTb(), dtTemp.getTgDt()));
        return result.toString();
    }
}
