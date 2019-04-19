package com.huarui.green.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huarui.common.config.Global;
import com.huarui.common.entity.sys.User;
import com.huarui.common.persistence.Page;
import com.huarui.common.utils.IdUtils;
import com.huarui.common.utils.StringUtils;
import com.huarui.green.dao.ProjectDao;
import com.huarui.green.entity.Project;
import com.huarui.green.entity.ProjectCamera;
import com.huarui.green.entity.ProjectCompany;
import com.huarui.green.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(interfaceClass = IProjectService.class, delay= Global.delayStart)
@Component
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private ProjectDao projectDao;

    @Override
    public String test(String args) {
        return "hello world , "+args;
    }

    @Override
    public Page<Project> findPage(Integer pageNo, Integer pageSize, Project project,User user) {

        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        project.setPage(page);
        List<Project> projectList = new ArrayList<>();
        if (user.isAdmin()){
            projectList = projectDao.findAllList(project);
        }else{
            project.getSqlMap().put("dsf", BaseServicImpl.dataScopeFilter(user, "o,f", "p"));
            projectList = projectDao.findList(project);
        }
        return page.setList(projectList);
    }

    @Override
    public List<Project> findList(Integer pageNo, Integer pageSize, Project project,User user) {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        project.setPage(page);

        List<Project> projectList = new ArrayList<>();
        if (user.isAdmin()){
            projectList = projectDao.findAllList(project);
        }else{
            project.getSqlMap().put("dsf", BaseServicImpl.dataScopeFilter(user, "o", "p"));
            projectList = projectDao.findList(project);
        }

        return projectList;
    }

    @Override
    public Project get(String id) {

        return projectDao.get(id);
    }

    @Override
    public String save(Project project){

        if (StringUtils.isBlank(project.getId())){

           /* Project one = projectDao.getByCode(project.getCode());
            if(one==null) {*/
                project.setId(IdUtils.uuid());
                project.setUpdateBy(new User(project.getUserId()));
                project.setCreateBy(new User(project.getUserId()));
                project.setUpdateDate(new Date());
                project.setCreateDate(project.getUpdateDate());
                projectDao.insert(project);
                insertData(project);
                return "新增成功";
            /*}else{
                return "该编号已经存在";
            }*/
        }else{
            project.setUpdateBy(new User(project.getUserId()));
            project.setUpdateDate(new Date());
            projectDao.update(project);
            projectDao.deleteCamera(project);
            projectDao.deleteCompany(project);
            insertData(project);
            return "编辑成功";
        }
    }

    public void insertData(Project project){
        if(StringUtils.isNotBlank(project.getCameraList())){
            List<ProjectCamera> list = JSONObject.parseArray(project.getCameraList(),ProjectCamera.class);
            for(ProjectCamera one : list){
                one.setId(IdUtils.uuid());
                one.setProjectId(project.getId());
                System.out.println(JSON.toJSONString(one));
                projectDao.insertCamera(one);

            }
        }
        if(StringUtils.isNotBlank(project.getCompanyList())){
            List<ProjectCompany> list = JSONObject.parseArray(project.getCompanyList(),ProjectCompany.class);
            for(ProjectCompany one : list){
                one.setId(IdUtils.uuid());
                one.setProjectId(project.getId());
                projectDao.insertCompany(one);
            }
        }
    }

    @Override
    public void delete(Project project){
        projectDao.delete(project);
    }

    @Override
    public void disable(Project project){
        projectDao.disable(project);
    }

    @Override
    public void enable(Project project){
        projectDao.enable(project);
    }

    @Override
    public List<Project> findListByKey(String service, User user) {
        Project project = new Project();
        project.setService(service);
        List<Project> projectList = new ArrayList<>();
        if (user.isAdmin()){
            projectList = projectDao.findAllList(project);
        }else{
            project.getSqlMap().put("dsf", BaseServicImpl.dataScopeFilter(user, "o,f", "p"));
            projectList = projectDao.findList(project);
        }
        return projectList;
    }


}
