package com.huarui.green.dao;

import com.huarui.common.persistence.CrudDao;
import com.huarui.green.entity.Project;
import com.huarui.green.entity.ProjectCamera;
import com.huarui.green.entity.ProjectCompany;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectDao extends CrudDao<Project>{
    Project getByCode(String code);

    String getCodeById(String id);

    void disable(Project project);

    void enable(Project project);

    void insertCamera(ProjectCamera one);

    void insertCompany(ProjectCompany one);

    void deleteCamera(Project project);

    void deleteCompany(Project project);
}
