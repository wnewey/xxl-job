package com.xxl.job.admin.core.dag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DagInfo
 * @author newey
 * @date 2019/12/11
 * @version 1.0.0
 **/
public class Graph {

    public static String DIRECTION_TB = "TB";
    public static String DIRECTION_LR = "LR";

    private String direction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Graph() {
        this.direction = DIRECTION_LR;
    }

    public Graph(String direction) {
        this.direction = direction;
    }

    private Map<String, SubGraph> subGraphs;

    public Map<String, SubGraph> getSubGraphs() {
        return subGraphs;
    }

    public void setSubGraphs(Map<String, SubGraph> subGraphs) {
        this.subGraphs = subGraphs;
    }

    public Graph addSubGraph(SubGraph subGraph) {
        if (this.subGraphs == null) {
            this.subGraphs = new HashMap<>(16);
        }
        this.subGraphs.put(subGraph.getId(), subGraph);
        return this;
    }

    private List<Flow> flowList;

    public List<Flow> getFlowList() {
        return flowList;
    }

    public void setFlowList(List<Flow> flowList) {
        this.flowList = flowList;
    }

    public Graph addFlow(Flow flow) {
        if (this.flowList == null) {
            this.flowList = new ArrayList<>();
        }
        this.flowList.add(flow);
        return this;
    }

    /**
     * graph LR;
     * 		subgraph one
     *     a1;
     *     a2;
     *     end
     *     subgraph two
     *     b1;
     *     b2;
     *     end
     *     a1-->a2;
     *     a1-->b2;
     *     b1-->b2;
     * @return
     */
    public String parseToString() {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append("graph ").append(direction).append(";\n");
        if (subGraphs != null) {
            for (String subGraphId : subGraphs.keySet()) {
                SubGraph subGraph = subGraphs.get(subGraphId);
                strbuf.append(subGraph.parseToString());
            }
        }
        if (flowList != null) {
            for (Flow flow : flowList) {
                strbuf.append(flow.parseToString());
            }
        }
        return strbuf.toString();
    }

}
