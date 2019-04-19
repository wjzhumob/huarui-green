package com.huarui.green.service;

import com.huarui.common.persistence.Page;
import com.huarui.green.entity.Camera;

import java.io.IOException;
import java.security.DigestException;

public interface ICameraService {
    Page<Camera> findList(Integer pageNo, Integer pageSize, Camera camera);

     String sendCameraData(String projectId) throws IOException, DigestException;

     String getPlayUrl(String projectCode, String camNo);
}
