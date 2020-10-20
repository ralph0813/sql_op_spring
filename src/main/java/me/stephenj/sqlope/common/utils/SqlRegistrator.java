package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.Exception.TableExistException;
import me.stephenj.sqlope.domain.DtDomain;
import me.stephenj.sqlope.domain.DtTemp;
import me.stephenj.sqlope.domain.TbDomain;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        tb.setDbId(db.getId());
        tb.setName(tbDomain.getName());
        TbExample tbExample = new TbExample();
        tbExample.createCriteria().andNameEqualTo(tbDomain.getName()).andDbIdEqualTo(db.getId());
        if (!tbMapper.selectByExample(tbExample).isEmpty()){
            throw new TableExistException("该数据表已经存在");
        }
        tb.setFk((int) tbDomain.getDts().stream().filter(DtDomain::isForeignkey).count());
        tbMapper.insertSelective(tb);
        int tbId = tbMapper.selectByExample(tbExample).get(0).getId();

        for (DtDomain dtDomain : tbDomain.getDts()) {
            Dt dt = new Dt();
            dt.setTbId(tbId);
            dt.setName(dtDomain.getName());
            if (dt.getName().equals(tbDomain.getPrimaryKey())) {
                dt.setPk(true);
            }
            if (dtDomain.isForeignkey()) {
                TbExample tbExample_tg = new TbExample();
                tbExample_tg.createCriteria().andDbIdEqualTo(db.getId()).andNameEqualTo(dtDomain.getTgTb());
                int tgTbId = tbMapper.selectByExample(tbExample_tg).get(0).getId();
                DtExample dtExample_tg = new DtExample();
                dtExample_tg.createCriteria().andTbIdEqualTo(tgTbId).andNameEqualTo(dtDomain.getTgDt());
                int tgDtId = dtMapper.selectByExample(dtExample_tg).get(0).getId();
                dt.setFk(tgDtId);
            }
            dtMapper.insert(dt);
        }
        return 1;
    }

    public int createDt(DtTemp dtTemp) {
        Dt dt = new Dt();
        dt.setTbId(dtTemp.getTbId());
        dt.setName(dtTemp.getName());
        if (dtTemp.isForeignkey()) {
            dt.setFk(dtTemp.getTgDtId());
        }
        dtMapper.insert(dt);
        return 1;
    }

    public int dropDt(DtTemp dtTemp) {
        dtMapper.deleteByPrimaryKey(dtTemp.getId());
        return 1;
    }

    public int modifyDt(DtTemp dtTemp) {
        Dt dt = dtMapper.selectByPrimaryKey(dtTemp.getId());
        dt.setName(dtTemp.getName());
        if (dtTemp.isForeignkey()) {
            dt.setFk(dtTemp.getTgDtId());
        }
        dtMapper.updateByPrimaryKeySelective(dt);
        return 1;
    }
}
