package com.huarui.green.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.huarui.common.config.Global;
import com.huarui.common.config.annotation.GenericDubbo;
import com.huarui.common.config.annotation.GenericReference;
import com.huarui.common.entity.sys.User;
import com.huarui.common.persistence.Page;
import com.huarui.common.utils.IdUtils;
import com.huarui.green.dao.FlowLogDao;
import com.huarui.green.dao.ReportDao;
import com.huarui.green.entity.FlowLog;
import com.huarui.green.entity.Report;
import com.huarui.green.service.IReportService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = IReportService.class, delay = Global.delayStart)
@Component
@Transactional
public class ReportServiceImpl implements IReportService {
    @Autowired
    private ReportDao dao;
    @Autowired
    private FlowLogDao flowLogDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    public static final String infoArchivesService = "dubbo://192.168.14:20814";

    @GenericReference(remote = infoArchivesService, interfaceName = "com.huarui.info.service.IDocxService")
    private GenericDubbo genericDubbo;

    @Override
    public Page findList(Integer pageNo, Integer pageSize, Report report, User user) {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        report.setPage(page);

        if (user.isAdmin()) {
            report.getSqlMap().put("dsf", "");
        } else {
            report.getSqlMap().put("dsf", BaseServicImpl.dataScopeFilter(user, "o,f", "p"));
        }
        List<Report> reportList = dao.findList(report);
        for (Report one : reportList) {
            Task task = taskService.createTaskQuery().taskCandidateUser(report.getUserId()).processInstanceBusinessKey(one.getDeploymentId()).singleResult();
            if (task != null) {
                one.setTaskId(task.getId());
                one.setTaskDefinitionKey(task.getTaskDefinitionKey());
            }
        }

        return page.setList(reportList);
    }

    @Override
    public void insertReport(Report report) {
        report.setId(IdUtils.uuid());
        report.setCreateBy(new User(report.getUserId()));
        report.setCreateDate(new Date());
        flowLogDao.insert(new FlowLog(report.getId(), report.getType(), null, null, report.getUserId(), null, "项目开始", null, report.getProjectId(), report.getServiceName()));


        dao.insert(report);
    }

    @Override
    public void verify(Report report, Boolean condition) {
        Task task = taskService.createTaskQuery().taskId(report.getTaskId()).singleResult();
        flowLogDao.insert(new FlowLog(report.getId(), report.getType(), task.getId(), task.getName(), report.getUserId(), null, "审核" + (condition ? "通过" : "拒绝"), null, report.getProjectId(), report.getServiceName()));
        int d = calculationProgress(task, report.getRate());
        int c = condition == true ? report.getRate() + d : report.getRate() - d;
        saveRate(report.getId(), c);

        taskService.setVariable(report.getTaskId(), "condition", condition);
        taskService.complete(report.getTaskId());
    }

    @Override
    public void updateReport(Report report) {
        Task task = taskService.createTaskQuery().taskId(report.getTaskId()).singleResult();
        if (report.getSubmit()) {
            if (isFinished(report.getDeploymentId())) {//已结束，改变状态
                saveRate(report.getId(), 100);
            } else {//更新状态
                int d = calculationProgress(task, report.getRate());
                if (d != 0) {
                    int c = report.getRate() + d;
                    saveRate(report.getId(), c);
                }
            }
            taskService.complete(report.getTaskId());
        }
        List<Object> newList = new ArrayList<>();
        List<String> docList = new ArrayList<>();
        getFiles(newList, docList, report.getTempFiles(), report.getResourceId());
        getFiles(newList, docList, report.getOtherFiles(), report.getResourceId());

        for(String file : docList){
            Map<String,String> map = JSON.parseObject(file,Map.class);
            System.out.println(JSON.toJSONString(map));
            /*genericDubbo.invoke("addFromService",
                    new String[]{ String.class.getName(),String.class.getName(),String.class.getName()},
                    new Object[]{ map.get("name"), map.get("url"), report.getUserId()});*/
        }
        flowLogDao.insert(new FlowLog(report.getId(), report.getType(), task.getId(), task.getName(), report.getUserId(), null, report.getSubmit()==true?"保存并提交文档":"保存文档", JSON.toJSONString(newList), report.getProjectId(), report.getServiceName()));

        ProcessInstance rpi = runtimeService.createProcessInstanceQuery().deploymentId(report.getDeploymentId()).singleResult();
        if (rpi == null) {
            dao.changeStatus(report.getId());
        }

        dao.update(report);
    }

    public boolean isFinished(String deploymentId) {

        return historyService.createHistoricProcessInstanceQuery().finished().deploymentId(deploymentId).count() > 0;
    }

    public void getFiles(List<Object> newList, List<String> docList ,String files, String resourceId) {
        Map<String, String> map = JSON.parseObject(files, Map.class);
        if (map != null && map.size() > 0) {
            Object params = map.get(resourceId);
            if (params != null) {
                List<Map<String, Object>> mapList = JSON.parseObject(params.toString(), List.class);
                for (Map<String, Object> one : mapList) {
                    if (one.get("path") != null) {
                        newList.add(one.get("path"));
                    }

                    if(one.get("isSave") == null){
                        if(one.get("isNew")==null){
                            docList.add(one.get("path").toString());

                        }else if(!(Boolean)one.get("isNew")){
                            docList.add(one.get("path").toString());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void deleteReport(String id,String deploymentId) {
        dao.deleteReport(id);
        flowLogDao.deleteFlowLog(id);

        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().deploymentId(deploymentId).list();
        if (list.size() > 0) {
            for (HistoricProcessInstance hpi : list) {
                String processInstanceId = hpi.getId();
                ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                if (pi == null) {
                    //该流程实例已经完成了
                    historyService.deleteHistoricProcessInstance(processInstanceId);
                } else {
                    //该流程实例未结束的
                    runtimeService.deleteProcessInstance(processInstanceId, "");
                    historyService.deleteHistoricProcessInstance(processInstanceId);//(顺序不能换)
                }
            }
        }
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public void saveRate(String id, Integer rate) {
        dao.saveRate(id, rate);
    }

    private int calculationProgress(Task task, Integer rate) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        int totalStep = 0;
        int currentStep = 0;
        ArrayList<UserTask> userTaskList = new ArrayList();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof UserTask) {
                UserTask u = (UserTask) flowElement;
                userTaskList.add(u);
            }
        }
        if (userTaskList != null) {
            totalStep = userTaskList.size();
            for (int i = 0; i < userTaskList.size(); i++) {
                if (userTaskList.get(i).getId().equals(task.getTaskDefinitionKey())) {
                    currentStep = i;
                    break;
                }
            }
        }
        if (totalStep != 0) {
            double a = 100 - rate;
            double b = totalStep - currentStep;
            double e = a / b; //剩下的进度，除以剩下的步数，得到剩下每步多少
            return (int) e;
        }
        return 0;
    }

}
