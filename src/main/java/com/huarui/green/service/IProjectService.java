package com.huarui.green.service;

import com.huarui.common.entity.sys.User;
import com.huarui.common.persistence.Page;
import com.huarui.green.entity.Project;

import java.util.List;

public interface IProjectService {
    String test(String args);

    Page findPage(Integer pageNo, Integer pageSize, Project project,User user);

    List<Project> findList(Integer pageNo, Integer pageSize, Project project,User user);

    Project get(String id);

    String save(Project project);

    void delete(Project project);

    void disable(Project project);

    void enable(Project project);

    List<Project> findListByKey(String service,User user);

}
