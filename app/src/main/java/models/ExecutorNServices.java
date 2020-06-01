package models;

public class ExecutorNServices {
    private int id;
    private int executorId;
    private int serviceId;

    public ExecutorNServices(int executorId, int serviceId) {
        this.executorId = executorId;
        this.serviceId = serviceId;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
