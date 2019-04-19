package com.huarui.green.dao;

import com.huarui.common.persistence.CrudDao;
import com.huarui.green.entity.FlowLog;
import com.huarui.green.entity.ServiceLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlowLogDao extends CrudDao<FlowLog> {
    void deleteFlowLog(String sourceId);

    List findServiceLogList(ServiceLog serviceLog);
}
