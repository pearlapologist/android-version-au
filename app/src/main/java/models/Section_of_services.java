package models;

public class Section_of_services {
    private int id;
    private String title;


    public Section_of_services() {
    }

    public Section_of_services(int id, String title) {
        this.id = id;
        this.title = title;
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
}
