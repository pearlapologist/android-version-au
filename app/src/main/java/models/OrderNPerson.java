package models;

public class OrderNPerson {
    private int id;
    private int orderId;
    private int customerId; //person
    private int executorId; //исполнитель


    public OrderNPerson(int id, int orderId, int customerId, int executorId) {
        this.id = id;
        this.customerId = customerId;
        this.executorId = executorId;
        this.orderId = orderId;
    }

    public OrderNPerson(int customerId, int orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
}
