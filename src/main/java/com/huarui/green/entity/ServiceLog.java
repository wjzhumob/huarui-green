package com.huarui.green.entity;

import com.huarui.common.entity.sys.DataEntity;

import java.util.List;

public class ServiceLog extends DataEntity<ServiceLog>{
    private String projectId;
    private String serviceName;
    private String sourceType;
    private String sourceId;
    private String name;
    private String rate;
    private String status;
    private List<FlowLog> flowLogList;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FlowLog> getFlowLogList() {
        return flowLogList;
    }

    public void setFlowLogList(List<FlowLog> flowLogList) {
        this.flowLogList = flowLogList;
    }
}
