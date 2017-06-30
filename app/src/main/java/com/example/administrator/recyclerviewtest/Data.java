package com.example.administrator.recyclerviewtest;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Data {
    private String content;
    private List<String> commands;

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
