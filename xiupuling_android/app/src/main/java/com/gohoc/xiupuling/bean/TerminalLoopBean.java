package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/7/11.
 */

public class TerminalLoopBean {

    private String id;
    private String name;
    private int moduleType;

    public TerminalLoopBean(String id, String name, int moduleType) {
        this.id = id;
        this.name = name;
        this.moduleType = moduleType;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
