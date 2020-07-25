package models;

import java.util.Calendar;

public class Order {

    private int customerId;

    private int id;
    private String title;
    private int sectionId;
    private double price;
    private Long created_date;
    private Long deadline;
    private String description;
    private boolean isAnonNote;

public Order(){}

    public Order(int id, int customerID, String title, int section, double price, String description,Long deadline,  Long created_date) {
        this.id = id;
        this.customerId = customerID;
        this.title = title;
        this.sectionId = section;
        this.price = price;
        this.created_date = created_date;
        this.deadline = deadline;
        this.description = description;
    }

    public Order( String title, int customerID, int section, double price, String description,Long deadline,  Long created_date) {
        this.title = title;
       this.customerId = customerID;
        this.sectionId = section;
        this.price = price;
        this.created_date = created_date;
        this.deadline = deadline;
        this.description = description;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSection() {
        return sectionId;
    }

    public void setSection(int section) {
        this.sectionId = section;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Long created_date) {
        this.created_date = created_date;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public boolean isAnonNote() {
        return isAnonNote;
    }

    public void setIsAnonNote(boolean anonNote) {
        isAnonNote = anonNote;
    }
}
