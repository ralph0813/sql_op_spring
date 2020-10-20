package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.Exception.DataNotCompleteException;
import me.stephenj.sqlope.Exception.DataNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.DtDomain;
import me.stephenj.sqlope.domain.DtTemp;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.Dt;
import me.stephenj.sqlope.mbg.model.DtExample;
import me.stephenj.sqlope.mbg.model.Tb;
import me.stephenj.sqlope.mbg.model.TbExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName SqlCheck.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 10:52
 * @Field :
 */
@Service
public class SqlCheck {
    @Autowired
    private TbMapper tbMapper;
    @Autowired
    private DtMapper dtMapper;
    @Autowired
    private DbMapper dbMapper;

    public boolean checkCreateDt(DtTemp dtTemp) throws TableNotExistException, DataNotCompleteException, DataNotExistException, DataExistException {
        Optional<Tb> tbOp = Optional.ofNullable(tbMapper.selectByPrimaryKey(dtTemp.getTbId()));
        if (!tbOp.isPresent()) {
            throw new TableNotExistException("该表不存在");
        } else {
            Tb tb = tbOp.get();
            dtTemp.setTbName(tb.getName());
            dtTemp.setDbId(tb.getDbid());
            dtTemp.setDbName(dbMapper.selectByPrimaryKey(dtTemp.getDbId()).getName());
        }
        if (dtTemp.getName().isEmpty() || dtTemp.getType().isEmpty()
            || (dtTemp.isForeignkey() && (dtTemp.getTgDt().isEmpty() || dtTemp.getTgTb().isEmpty()))) {
            throw new DataNotCompleteException("数据不完整，没有列名或类型或外键");
        }
        if (dtTemp.isForeignkey()) {
            TbExample tbExample = new TbExample();
            tbExample.createCriteria().andDbidEqualTo(tbOp.get().getDbid()).andNameEqualTo(dtTemp.getTgTb());
            List<Tb> tbs = tbMapper.selectByExample(tbExample);
            if (tbs.isEmpty()) {
                throw new TableNotExistException("外键指向的表不存在");
            }
            Tb tgTb = tbs.get(0);
            DtExample dtExample = new DtExample();
            dtExample.createCriteria().andTbidEqualTo(tgTb.getId()).andNameEqualTo(dtTemp.getTgDt());
            List<Dt> dts = dtMapper.selectByExample(dtExample);
            if (dts.isEmpty()) {
                throw new DataNotExistException("外键指向的数据列不存在");
            } else {
                dtTemp.setTgDtId(dts.get(0).getId());
            }
        }
        return true;
    }
}
