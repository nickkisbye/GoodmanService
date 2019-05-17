package dk.goodmanservice.goodmanservice.Model;

import java.sql.Date;
import java.util.ArrayList;

public class Case {

    private int id;
    private int customerId;
    private String description;
    private int price;
    private Date creationDate;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private int mode;
    private User customer;
    private ArrayList<User> employeeList;
    private ArrayList<String> imageList;

    public Case() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public ArrayList<User> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(User employee) {
        employeeList.add(employee);
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEmployeeList(ArrayList<User> employeeList) {
        this.employeeList = employeeList;
    }
}
