package com.fixingsolutions.domain;

import java.util.ArrayList;
import java.util.List;

public class AjaxResponseBody {

    private String msg;
    private List<Object> result = new ArrayList<Object>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    public void setResult(Object obj){
        this.result.add(obj);
    }

}
