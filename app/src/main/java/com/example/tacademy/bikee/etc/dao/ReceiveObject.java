package com.example.tacademy.bikee.etc.dao;

import java.util.List;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class ReceiveObject {
    public int code = -1;
    public boolean success = false;
    public List<Result> result = null;
    public Object err = null;
    public String msg = null;

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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Object getErr() {
        return err;
    }

    public void setErr(Object err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
