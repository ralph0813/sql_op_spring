package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.Exception.TableExistException;
import me.stephenj.sqlope.domain.DtDomain;
import me.stephenj.sqlope.domain.FkDomain;
import me.stephenj.sqlope.domain.TbDomain;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SqlRegistrator {
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private TbMapper tbMapper;
    @Autowired
    private DtMapper dtMapper;

    public int createTb(TbDomain tbDomain) throws TableExistException {
        DbExample dbExample = new DbExample();
        dbExample.createCriteria().andNameEqualTo(tbDomain.getDbName());
        Db db = dbMapper.selectByExample(dbExample).get(0);
        Tb tb = new Tb();
        tb.setDbid(db.getId());
        tb.setName(tbDomain.getName());
        TbExample tbExample = new TbExample();
        tbExample.createCriteria().andNameEqualTo(tbDomain.getName()).andDbidEqualTo(db.getId());
        if (!tbMapper.selectByExample(tbExample).isEmpty()){
            throw new TableExistException();
        }
        Optional.ofNullable(tbDomain.getFks())
                .ifPresent(fks -> {
                    if (fks.size() > 0) {
                        tb.setFk(fks.size());
                    }
                });
        tbMapper.insertSelective(tb);
        int tbId = tbMapper.selectByExample(tbExample).get(0).getId();

        for (DtDomain dtDomain : tbDomain.getDts()) {
            Dt dt = new Dt();
            dt.setTbid(tbId);
            dt.setName(dtDomain.getName());
            Optional<List<FkDomain>> fksOp = Optional.ofNullable(tbDomain.getFks());
            fksOp.ifPresent(fks -> {
                fks.stream()
                        .filter(fk -> fk.getDt().equals(dt.getName()))
                        .findFirst()
                        .ifPresent(fk -> {
                            TbExample tbExample_tg = new TbExample();
                            tbExample_tg.createCriteria().andDbidEqualTo(db.getId()).andNameEqualTo(fk.getTgTb());
                            int tgTbId = tbMapper.selectByExample(tbExample_tg).get(0).getId();
                            DtExample dtExample_tg = new DtExample();
                            dtExample_tg.createCriteria().andTbidEqualTo(tgTbId).andNameEqualTo(fk.getTgDt());
                            int tgDtId = dtMapper.selectByExample(dtExample_tg).get(0).getId();
                            dt.setFk(tgDtId);
                        });
            });
            dtMapper.insert(dt);
//            tbDomain.getFks().stream()
//                    .filter(fk -> fk.getDt().equals(dt.getName()))
//                    .findFirst()
//                    .ifPresent(fk -> {
//                        TbExample tbExample_tg = new TbExample();
//                        tbExample_tg.createCriteria().andDbidEqualTo(db.getId()).andNameEqualTo(fk.getTgTb());
//                        int tgTbId = tbMapper.selectByExample(tbExample_tg).get(0).getId();
//                        DtExample dtExample_tg = new DtExample();
//                        dtExample_tg.createCriteria().andTbidEqualTo(tgTbId).andNameEqualTo(fk.getTgDt());
//                        int tgDtId = dtMapper.selectByExample(dtExample_tg).get(0).getId();
//                        dt.setFk(tgDtId);
//                    });
        }

        return 1;
    }
}
