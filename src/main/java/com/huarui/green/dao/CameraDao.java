package com.huarui.green.dao;

import com.huarui.common.persistence.CrudDao;
import com.huarui.green.entity.Camera;
import com.huarui.green.entity.CameraData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CameraDao extends CrudDao<Camera>{

    List<CameraData> findCameraData(String projectCode);
}
