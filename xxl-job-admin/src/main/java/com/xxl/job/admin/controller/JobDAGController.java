package com.xxl.job.admin.controller;

/**
 * JobDAGController
 * @author newey
 * @date 2019/12/10
 * @version 1.0.0
 **/

import com.xxl.job.admin.core.dag.Flow;
import com.xxl.job.admin.core.dag.Graph;
import com.xxl.job.admin.core.dag.Node;
import com.xxl.job.admin.core.dag.SubGraph;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.admin.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/jobdag")
public class JobDAGController {
    @Resource
    public XxlJobInfoDao xxlJobInfoDao;

    @Resource
    public XxlJobGroupDao xxlJobGroupDao;

    @RequestMapping
    public String index(HttpServletRequest request) {
        /** 查询当前用户权限**/
        XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        String permission = null;
        if (loginUser.getRole() == 2) {
            permission = loginUser.getPermission();
            if (permission != null) {
                permission = permission.trim();
            }
        }
        List<XxlJobInfo> list = xxlJobInfoDao.dagJobList(permission);
        Graph graph = new Graph();
        for (XxlJobInfo xxlJobInfo : list) {
            String groupId = String.valueOf(xxlJobInfo.getJobGroup());

            /**
             * 为便于跨组的调用关系的展示，子图只包含节点信息，流程信息全部放在主图中
             * 一个执行器为一个分组即一个子图
             */
            SubGraph subGraph = null;
            if (graph.getSubGraphs() != null && graph.getSubGraphs().get(groupId) != null) {
                subGraph = graph.getSubGraphs().get(groupId);
            } else {
                XxlJobGroup jobGroup = xxlJobGroupDao.load(xxlJobInfo.getJobGroup());
                if (jobGroup != null) {
                    subGraph = new SubGraph(String.valueOf(jobGroup.getId()), jobGroup.getAppName());
                    graph.addSubGraph(subGraph);
                }
            }
            String childIds = xxlJobInfo.getChildJobId();
            if (childIds != null && childIds.trim().length() > 0) {
                String[] childJobIds = childIds.split(",");
                for (String cid : childJobIds) {
                    XxlJobInfo childJob = xxlJobInfoDao.loadById(Integer.parseInt(cid));
                    if (childJob != null) {
                        Node start = new Node(String.valueOf(xxlJobInfo.getId()), xxlJobInfo.getJobDesc());
                        start.setActive(xxlJobInfo.getTriggerStatus() == 1);
                        start.setJob(xxlJobInfo);
                        Node stop = new Node(String.valueOf(childJob.getId()), childJob.getJobDesc());
                        stop.setActive(xxlJobInfo.getTriggerStatus() == 1);
                        stop.setJob(childJob);

                        subGraph.addNode(start);
                        if (String.valueOf(childJob.getJobGroup()).equals(subGraph.getId())) {
                            subGraph.addNode(stop);
                        }

                        Flow flow = new Flow();
                        flow.setStart(start);
                        flow.setStop(stop);
                        graph.addFlow(flow);
                    }
                }
            } else {
                Flow flow = new Flow();
                Node start = new Node(String.valueOf(xxlJobInfo.getId()), xxlJobInfo.getJobDesc());
                start.setActive(xxlJobInfo.getTriggerStatus() == 1);
                start.setJob(xxlJobInfo);
                flow.setStart(start);
                subGraph.addNode(start);
            }
        }
        request.setAttribute("graphstr", graph.parseToString());
        return "jobdag/jobdag.index";
    }
}
