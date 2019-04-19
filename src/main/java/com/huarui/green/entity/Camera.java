package com.huarui.green.entity;

import com.huarui.common.entity.sys.DataEntity;

public class Camera extends DataEntity<Camera>{

    private String name;
    private String place;
    private String status;
    private String camera_group;
    private String camera_type;
    private String camera_id;
    private String camera_ip;
    private String camera_port;
    private String groupId;
    private String groupName;
    private String projectId;
    private String deviceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCamera_group() {
        return camera_group;
    }

    public void setCamera_group(String camera_group) {
        this.camera_group = camera_group;
    }

    public String getCamera_type() {
        return camera_type;
    }

    public void setCamera_type(String camera_type) {
        this.camera_type = camera_type;
    }

    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }

    public String getCamera_ip() {
        return camera_ip;
    }

    public void setCamera_ip(String camera_ip) {
        this.camera_ip = camera_ip;
    }

    public String getCamera_port() {
        return camera_port;
    }

    public void setCamera_port(String camera_port) {
        this.camera_port = camera_port;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
