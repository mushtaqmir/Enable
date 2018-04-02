package com.example.mushtaqmir.app4;

/**
 * Created by Mushtaq.Mir on 3/29/2018.
 */

public class TemplateMessages {

    private String id;
    private String message;

    public TemplateMessages(){}

    public TemplateMessages(String message) {
        this.message=message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
