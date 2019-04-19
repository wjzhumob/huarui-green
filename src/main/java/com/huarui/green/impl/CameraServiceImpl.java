package com.huarui.green.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.huarui.common.config.Global;
import com.huarui.common.persistence.Page;
import com.huarui.common.service.BaseService;
import com.huarui.common.utils.IdUtils;
import com.huarui.green.dao.CameraDao;
import com.huarui.green.dao.ProjectDao;
import com.huarui.green.entity.Camera;
import com.huarui.green.entity.CameraData;
import com.huarui.green.service.ICameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Service(interfaceClass = ICameraService.class, delay = Global.delayStart)
@Component
public class CameraServiceImpl implements ICameraService {
    @Autowired
    private CameraDao dao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public Page<Camera> findList(Integer pageNo, Integer pageSize, Camera camera) {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        camera.setPage(page);
        page.setList(dao.findList(camera));
        return page;
    }

    @Override
    public String sendCameraData(String projectId){
        String projectCode = projectDao.getCodeById(projectId);
        List<CameraData> list = dao.findCameraData(projectCode);
        for(CameraData one :list){
            one.setProjectCode(projectCode);
        }
        //sendData(list,projectCode,"cameraUrl");
        return "执行成功";
    }

    @Override
    public String getPlayUrl(String projectCode, String camNo) {
        System.out.println(projectCode);
        System.out.println(camNo);

        return "ws://183.230.39.118:8011/70249d3e7f54b64570cbcfe70ce1f94786533e0a";
    }
}
