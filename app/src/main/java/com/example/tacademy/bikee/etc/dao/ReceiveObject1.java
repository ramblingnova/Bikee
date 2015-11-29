package com.example.tacademy.bikee.etc.dao;

import java.util.List;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class ReceiveObject1 {
    private int code = -1;
    private boolean success = false;
    private List<Result1> result = null;
    private Err err = null;
    private String msg = null;
    private List<String> stack = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Result1> getResult() {
        return result;
    }

    public void setResult(List<Result1> result) {
        this.result = result;
    }

//    public Object getErr() {
//        return err;
//    }
//
//    public void setErr(Object err) {
//        this.err = err;
//    }

    public Err getErr() {
        return err;
    }

    public void setErr(Err err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }
}
