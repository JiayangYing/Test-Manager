package com.server.core.pojo;

import java.io.Serializable;

public class Question implements Serializable {
    private String type;
    private String language;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getChoice() {
        return choice;
    }

    public void setChoice(String[] choice) {
        this.choice = choice;
    }

    private String content;
    private String[] choice;

    public Question(String type, String language, String content) {
        this.type = type;
        this.language = language;
        this.content = content;
        this.choice = null;
    }
    public Question(String type, String language, String content,String[] choice) {
        this.type = type;
        this.language = language;
        this.content = content;
        this.choice = choice;

    }
}
