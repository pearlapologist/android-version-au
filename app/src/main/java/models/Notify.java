package models;

import java.util.Calendar;

public class Notify {
    private int id;
    private int personid;
    private String text;
    private Long createdDate;

    public Notify(int id, int personid, String text, Long createdDate) {
        this.id = id;
        this.personid = personid;
        this.text = text;
        this.createdDate = createdDate;
    }

    public Notify() {
    }

    public Notify(int personid, String text, Long createdDate) {
        this.personid = personid;
        this.text = text;
        this.createdDate = createdDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
}
