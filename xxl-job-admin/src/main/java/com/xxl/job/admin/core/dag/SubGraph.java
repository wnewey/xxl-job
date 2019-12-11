package com.xxl.job.admin.core.dag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SubGraph
 * @author newey
 * @date 2019/12/11
 * @version 1.0.0
 **/
public class SubGraph {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubGraph(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private Map<String, Node> nodes;

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public SubGraph addNode(Node node) {
        if (this.nodes == null) {
            this.nodes = new HashMap<>(16);
        }
        this.nodes.put(node.getId(), node);
        return this;
    }

    @Deprecated
    private List<Flow> flowList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Deprecated
    public List<Flow> getFlowList() {
        return flowList;
    }

    @Deprecated
    public void setFlowList(List<Flow> flowList) {
        this.flowList = flowList;
    }

    @Deprecated
    public SubGraph addFlow(Flow flow) {
        if (this.flowList == null) {
            this.flowList = new ArrayList<>();
        }
        this.flowList.add(flow);
        return this;
    }

    public String parseToString() {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append("subgraph ").append(getName()).append("\n");
        if (nodes != null) {
            for (String nodeId : nodes.keySet()) {
                Node node = nodes.get(nodeId);
                strbuf.append("\t").append(node.parseToString(true));
            }
        }
        strbuf.append("end;\n");
        return strbuf.toString();
    }
}
