package com.huarui.green.dao;

import com.huarui.common.persistence.CrudDao;
import com.huarui.green.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportDao extends CrudDao<Report> {
    void saveRate(@Param(value="id") String id,@Param(value="rate")  Integer rate);

    void deleteReport(String deploymentId);

    void changeStatus(String id);
}
