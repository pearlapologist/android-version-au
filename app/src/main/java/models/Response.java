package models;

public class Response {
    private int id;
    private int orderId;
    private int personId;
    private String text;
    private Double price;
    private Long createdDate;

    public Response(int id, int orderId, int personId, String text, Double price, Long createdDate) {
        this.id = id;
        this.orderId = orderId;
        this.personId = personId;
        this.text = text;
        this.price = price;
        this.createdDate = createdDate;
    }

    public Response(int orderId, int personId, String text, Double price, Long createdDate) {
        this.orderId = orderId;
        this.personId = personId;
        this.text = text;
        this.price = price;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
}
