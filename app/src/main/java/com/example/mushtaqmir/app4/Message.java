package com.example.mushtaqmir.app4;

/**
 * Created by Mushtaq.Mir on 3/26/2018.
 */

public class Message {

    private String message;
    boolean employee;

    public Message(String msg,boolean isEmployee){
        message=msg;
        employee=isEmployee;
    }

    public String getMessage(){
        return message;
    }


    public void setMessage(String message){
        this.message = message;
    }

    public boolean isEmployee(){
        return employee;
    }

    public void getCustMesasage(boolean val){
        this.employee=val;
    }
}
