package models;

public class Message {
    private int id;
    private int personId;
    private int whosends;
    private String text;

    public Message(){}
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getText() {
        return text;
    }

    public void setText(String gmessage) {
        this.text = gmessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Message(int id, int personId, String text) {
        this.id = id;
        this.personId = personId;
        this.text = text;
    }

    public int getWhosends() {
        return whosends;
    }

    public void setWhosends(int whosends) {
        this.whosends = whosends;
    }

    public Message(int id, int personId, int whosends, String text) {
        this.id = id;
        this.personId = personId;
        this.whosends = whosends;
        this.text = text;
    }

}
