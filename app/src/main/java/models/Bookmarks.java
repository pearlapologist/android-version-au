package models;

public class Bookmarks {
    private int id;
    private int personId;
    private int executorId;
    private int orderId;



    public Bookmarks(){}

    public Bookmarks(int id,  int personId, int executorId, int orderId) {
        this.id= id;
        this.executorId = executorId;
        this.personId = personId;
        this.orderId = orderId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
