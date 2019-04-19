package com.huarui.green.service;

import com.huarui.common.entity.sys.User;
import com.huarui.common.persistence.Page;
import com.huarui.green.entity.Report;

public interface IReportService {

    Page findList(Integer pageNo, Integer pageSize,Report report,User user);

    void insertReport(Report report);

    void updateReport(Report report);

    void deleteReport(String id,String deploymentId);

    void verify(Report report,Boolean condition);

    void saveRate(String id,Integer rate);
}
