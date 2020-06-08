package models;

import android.util.SparseLongArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Executor {
    private int id;
    private int personId;
    private int sectionId;
    private String specialztn;
    private String descriptn;
    private byte[] coverPhoto;

    private ArrayList<Service> services;

    public Executor() {
    };


    public Executor(int id, int personId, int sectionId, String specialztn, String descriptn) {
        this.id = id;
        this.personId = personId;
        this.sectionId = sectionId;
        this.specialztn = specialztn;
        this.descriptn = descriptn;
    }

    public Executor(int personId, int sectionId, String specialztn, String descriptn) {
        this.personId = personId;
        this.sectionId = sectionId;
        this.specialztn = specialztn;
        this.descriptn = descriptn;
    }

    public Executor(int sectionId, String specialztn, String descriptn) {
        this.sectionId = sectionId;
        this.specialztn = specialztn;
        this.descriptn = descriptn;
    }


    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSpecialztn() {
        return specialztn;
    }

    public void setSpecialztn(String specialztn) {
        this.specialztn = specialztn;
    }

    public String getDescriptn() {
        return descriptn;
    }

    public void setDescriptn(String descriptn) {
        this.descriptn = descriptn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }



    public String getServicesString() {
        String str = "";
        for (Service s : this.getServices()) {
            str += s.getTitle() + ",";
        }
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }

    public byte[] getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
