package models;

public class Persons {
    private int id;
    private String name;
    private String lastname;
    private String passwd;
    private Long createdDate;
    private int rating;
    private String status;
    private byte[] photo;
    private String number;
   // boolean hasAccount;


    //upd
    private Long birthday;

    public static final String ONLINE = "online";
    public static final String OFFLINE = "offline";


    public Persons(){}
    //get person to cursor
    public Persons(int id, String name, String passwd, Long createdDate) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.createdDate = createdDate;
    }

    public Persons(int id, String name, String lastname, String passwd, byte[] photo, String number, int rating, Long createdDate) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.lastname = lastname;
        this.rating = rating;
        this.photo = photo;
        this.number = number;
        this.createdDate = createdDate;
    }

    public Persons(String name, String lastname, String passwd,  byte[] photo, String number, int rating, Long createdDate) {
        this.passwd = passwd;
        this.name = name;
        this.lastname = lastname;
        this.rating = rating;
        this.photo = photo;
        this.number = number;
        this.createdDate = createdDate;
    }


    public Persons(int id, String name, String lastname, String passwd, String number, int rating,
                   Long createdDate) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.lastname = lastname;
        this.rating = rating;
        this.number = number;
        this.createdDate = createdDate;
    }


    public Persons(String name, String lastname, String number, String passwd, int rating) {
        this.name = name;
        this.lastname = lastname;
        this.number = number;
        this.passwd = passwd;
        this.rating = rating;
        Long created = DataConverter.getCurentDateInLong();
        this.createdDate = created;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public String getCreatedDateinString() {
        Long create = this.createdDate;
      String str =  DataConverter.convertLongToDataString(create);
        return str;
    }


    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

//    public boolean isHasAccount() {
//        return hasAccount;
//    }
//
//    public void setHasAccount(boolean hasAccount) {
//        this.hasAccount = hasAccount;
//    }
}
