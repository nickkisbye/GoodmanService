package dk.goodmanservice.goodmanservice.Model;

public class Invoice {

    private int id;
    private Case obj;

    public Invoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Case getObj() {
        return obj;
    }

    public void setObj(Case obj) {
        this.obj = obj;
    }
}

