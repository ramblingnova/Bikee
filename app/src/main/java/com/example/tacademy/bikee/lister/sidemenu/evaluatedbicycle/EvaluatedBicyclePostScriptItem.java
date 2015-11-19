package com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class EvaluatedBicyclePostScriptItem {
    private String mail;
    private String name;
    private int point;
    private String body;
    private String date;

    public EvaluatedBicyclePostScriptItem(String mail, String name, int point, String body, String date) {
        this.mail = mail;
        this.name = name;
        this.point = point;
        this.body = body;
        this.date = date;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
