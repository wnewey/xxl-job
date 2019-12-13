package com.xxl.job.admin.core.dag;

import com.xxl.job.admin.core.model.XxlJobInfo;

/**
 * Node
 * @author newey
 * @date 2019/12/11
 * @version 1.0.0
 **/
public class Node {

    private String id;
    private String desc;
    private boolean active;

    private XxlJobInfo job;

    public Node(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public XxlJobInfo getJob() {
        return job;
    }

    public void setJob(XxlJobInfo job) {
        this.job = job;
    }

    public String parseToString() {
        return parseToString(false);
    }

    public String parseToString(boolean appendWrap) {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(getId()).append("(\"").append("[").append(getId()).append("] ").append(getDesc()).append("\")");
        if (appendWrap) {
            strbuf.append("\n");
        }
        return strbuf.toString();
    }
}
