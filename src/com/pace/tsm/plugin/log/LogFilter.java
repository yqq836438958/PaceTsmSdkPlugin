
package com.pace.tsm.plugin.log;

public class LogFilter {
    private String logFilterTag;
    private String logShowTag;
    private int status;

    public String getLogShowTag() {
        return this.logShowTag;
    }

    public void setLogShowTag(String logShowTag) {
        this.logShowTag = logShowTag;
    }

    public String getLogFilterTag() {
        return this.logFilterTag;
    }

    public void setLogFilterTag(String logFilterTag) {
        this.logFilterTag = logFilterTag;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
