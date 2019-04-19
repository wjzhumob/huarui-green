package com.huarui.green.service;


import com.huarui.common.persistence.Page;
import com.huarui.green.entity.FlowLog;

public interface IFlowLogService {
    void insert(FlowLog flowLog);

    Page findList(String sourceId, Integer pageNo, Integer pageSize);
}
