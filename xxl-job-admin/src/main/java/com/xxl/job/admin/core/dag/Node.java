package com.xxl.job.admin.core.dag;

/**
 * Node
 * @author newey
 * @date 2019/12/11
 * @version 1.0.0
 **/
public class Node {

    private String id;
    private String desc;

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
