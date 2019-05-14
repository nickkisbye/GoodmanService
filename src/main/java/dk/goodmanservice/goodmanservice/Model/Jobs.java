package dk.goodmanservice.goodmanservice.Model;

public class Jobs {
    private int id;
    private int caseId;
    private int employeeId;
    private User employees;
    private Case cases;

    public Jobs() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public User getEmployees() {
        return employees;
    }

    public void setEmployees(User employees) {
        this.employees = employees;
    }

    public Case getCases() {
        return cases;
    }

    public void setCases(Case cases) {
        this.cases = cases;
    }
}
