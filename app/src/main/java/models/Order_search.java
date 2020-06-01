package models;

import java.util.ArrayList;

public class Order_search {

    private int personId; //bother
    private int personId1ThatISend; //которые я создал
    private int personId2ThatIAccept; //которые я выполняю
    private ArrayList<String> statuses;

    public Order_search(int bother, int thatIsend1, int sendTome2, ArrayList<String> statuses) {
        this.personId = bother;
        this.personId1ThatISend = thatIsend1;
        this.personId2ThatIAccept = sendTome2;
        this.statuses = statuses;
    }

    public  Order_search(){
        this.statuses = new ArrayList<>();
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getPersonId1ThatISend() {
        return personId1ThatISend;
    }

    public void setPersonId1ThatISend(int personId1ThatISend) {
        this.personId1ThatISend = personId1ThatISend;
    }

    public int getPersonId2ThatIAccept() {
        return personId2ThatIAccept;
    }

    public void setPersonId2ThatIAccept(int personId2ThatIAccept) {
        this.personId2ThatIAccept = personId2ThatIAccept;
    }

    public ArrayList<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<String> statuses) {
        this.statuses = statuses;
    }
}
