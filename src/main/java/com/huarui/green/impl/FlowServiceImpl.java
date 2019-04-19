package com.huarui.green.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.huarui.common.config.Global;
import com.huarui.common.persistence.Page;
import com.huarui.common.utils.IdUtils;
import com.huarui.green.dao.FlowLogDao;
import com.huarui.green.entity.FlowLog;
import com.huarui.green.entity.ServiceLog;
import com.huarui.green.service.IFlowLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Service(interfaceClass = IFlowLogService.class, delay= Global.delayStart)
@Component
public class FlowServiceImpl implements IFlowLogService {

    @Autowired
    private FlowLogDao dao;

    @Override
    public void insert(FlowLog flowLog) {
        flowLog.setId(IdUtils.uuid());
        flowLog.setCreateDate(new Date());
        dao.insert(flowLog);
    }

    @Override
    public Page findList(String sourceId,Integer pageNo,Integer pageSize) {
        FlowLog flowLog = new FlowLog();
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        flowLog.setPage(page);
        flowLog.setSourceId(sourceId);
        return page.setList(dao.findList(flowLog));
    }
}
