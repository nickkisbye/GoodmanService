package dk.goodmanservice.goodmanservice.Model;

import java.util.Date;

public class Appointment {

    private int id;
    private String name;
    private Date date;
    private Case obj;

    public Appointment() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Case getObj() {
        return obj;
    }

    public void setObj(Case obj) {
        this.obj = obj;
    }
}
