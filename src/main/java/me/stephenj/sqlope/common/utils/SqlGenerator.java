package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.domain.DtDomain;
import me.stephenj.sqlope.domain.FkDomain;
import me.stephenj.sqlope.domain.TbDomain;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.DbExample;
import me.stephenj.sqlope.mbg.model.DtExample;
import me.stephenj.sqlope.mbg.model.Tb;
import me.stephenj.sqlope.mbg.model.TbExample;
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
        if (tbDomain.getFks() != null && tbDomain.getFks().size() > 0) {
            for (FkDomain fkDomain : tbDomain.getFks()) {
                TbExample tbExample = new TbExample();
                tbExample.createCriteria().andNameEqualTo(fkDomain.getTgTb());
                Tb tb = tbMapper.selectByExample(tbExample).get(0); //获取外键指向的table
                DbExample dbExample = new DbExample();
                dbExample.createCriteria().andIdEqualTo(tb.getDbid());
                if (dbMapper.selectByExample(dbExample).get(0).getName().equals(tbDomain.getName())) { //外键指向的表的数据库和本表数据库相同
                    DtExample dtExample = new DtExample();
                    dtExample.createCriteria().andNameEqualTo(fkDomain.getTgDt());
                    if (dtMapper.selectByExample(dtExample).get(0).getTbid() == tb.getId()) {
                        result.append(String.format(", CONSTRAINT %s_%s_%s_fk FOREIGN KEY (`id`) REFERENCES `tb` (`id`)",
                                tbDomain.getName(), fkDomain.getTgTb(), fkDomain.getTgDt()));
                        result.append(String.format("FOREIGN KEY (`%s`) REFERENCES `%s` (`%s`)",
                                fkDomain.getDt(), fkDomain.getTgTb(), fkDomain.getTgDt()));
                    }
                }
            }
        }
        result.append(") ").append(tail);
        return result.toString();
    }

    public String dropTb(String tbName) {
        return String.format("DROP TABLE `%s`;", tbName);
    }

    public String renameTb(String oldName, String newName) {
        return String.format("RENAME TABLE `%s` TO `%s`;", oldName, newName);
    }
}
