package com.xxl.job.admin.core.dag;

/**
 * Flow
 * @author newey
 * @date 2019/12/11
 * @version 1.0.0
 **/
public class Flow {

    private Node start;
    private Node stop;

    private String link;

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getStop() {
        return stop;
    }

    public void setStop(Node stop) {
        this.stop = stop;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String parseToString() {
        StringBuffer strbuf = new StringBuffer();
        if (start != null) {
            strbuf.append(start.parseToString());
            if (stop != null) {
                strbuf.append("-->");
                strbuf.append(stop.parseToString());
            }
            strbuf.append(";\n");
        }
        return strbuf.toString();
    }
}
