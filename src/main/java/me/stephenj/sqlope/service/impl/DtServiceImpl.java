package me.stephenj.sqlope.service.impl;

import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.Dt;
import me.stephenj.sqlope.mbg.model.DtExample;
import me.stephenj.sqlope.mbg.model.Tb;
import me.stephenj.sqlope.service.DtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName DtServiceImpl.java
 * @Description
 * @author 张润天
 * @Time 2020/10/18 23:36
 * @Field :
 */
@Service
public class DtServiceImpl implements DtService {
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private TbMapper tbMapper;
    @Autowired
    private DtMapper dtMapper;

    @Override
    public List<Dt> listDts(int tbId) throws TableNotExistException {
        DtExample dtExample = new DtExample();
        dtExample.createCriteria().andTbidEqualTo(tbId);
        List<Dt> dts = dtMapper.selectByExample(dtExample);
        if (dts.isEmpty()) {
            throw new TableNotExistException();
        }
        return dts;
    }
}
