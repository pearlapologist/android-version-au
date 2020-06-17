package models;

import java.util.Calendar;

public class Notify {
    private int id;
    private int personId;
    private String text;
    private Long createdDate;
    private int sectionId;
    private int srcId;
    private int status;

    /*
    * разделы уведомлений:
    * 1. отклики на заказ
    * 2. новый отзыв на тебя или ответ на твой отзыв
    * 3. сообщения (возможно)
    *
    * */

    public Notify(int id, int personId, String text, Long createdDate,  int sectionId, int srcId, int status) {
        this.id = id;
        this.personId = personId;
        this.text = text;
        this.createdDate = createdDate;
        this.sectionId = sectionId;
        this.srcId = srcId;
        this.status = status;
    }

    public Notify(int personId, String text, Long createdDate,  int sectionId, int srcId, int status) {
        this.personId = personId;
        this.text = text;
        this.createdDate = createdDate;
        this.sectionId = sectionId;
        this.srcId = srcId;
        this.status =status;
    }


    public Notify() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
