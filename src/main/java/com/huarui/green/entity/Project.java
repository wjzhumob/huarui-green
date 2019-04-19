package com.huarui.green.entity;

import com.huarui.common.entity.sys.DataEntity;

import java.util.List;

public class Project extends DataEntity<Project>{

    private String belongOffice;
    private String name;
    private String code;
    private String type;
    private String areaId;
    private String address;
    private String dischargePermit;
    private String evaluation;
    private String location;
    private String zoom;
    private String boundary;
    private String contact;
    private String contactNumber;
    private String service;
    private List<String> officeIdList;
    private String areaName;
    private String officeName;
    private String userId;
    private String status;
    private String createOffice;
    private String cameraList;
    private String companyList;
    private List<ProjectCamera> cameras;
    private List<ProjectCompany> companies;
    public List<ProjectCamera> getCameras() {
        return cameras;
    }

    public void setCameras(List<ProjectCamera> cameras) {
        this.cameras = cameras;
    }

    public List<ProjectCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<ProjectCompany> companies) {
        this.companies = companies;
    }

    public String getCompanyList() {
        return companyList;
    }

    public void setCompanyList(String companyList) {
        this.companyList = companyList;
    }

    public String getCameraList() {
        return cameraList;
    }

    public void setCameraList(String cameraList) {
        this.cameraList = cameraList;
    }

    public String getCreateOffice() {
        return createOffice;
    }

    public void setCreateOffice(String createOffice) {
        this.createOffice = createOffice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBelongOffice() {
        return belongOffice;
    }

    public void setBelongOffice(String belongOffice) {
        this.belongOffice = belongOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDischargePermit() {
        return dischargePermit;
    }

    public void setDischargePermit(String dischargePermit) {
        this.dischargePermit = dischargePermit;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<String> getOfficeIdList() {
        return officeIdList;
    }

    public void setOfficeIdList(List<String> officeIdList) {
        this.officeIdList = officeIdList;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
