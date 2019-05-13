package dk.goodmanservice.goodmanservice.Repository;

import java.sql.*;

public class StatisticRepository {

    private Connection con;
    private PreparedStatement preparedStatement;
    private String sql;

    public StatisticRepository() throws SQLException {
        this.con = DriverManager.getConnection(
                "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                "goodmanservicedb",
                "Ly02_scr-4ds");
    }

    public ResultSet fetch(String option) throws SQLException {

        switch (option) {
            case "total":
                sql = "SELECT SUM(price) as total_price FROM cases WHERE fk_mode = 3";
                break;
            case "monthly":
                //Hvis "monthname" ikke virker, prøv "datename"
                sql = "SELECT MONTHNAME(endDate) as MonthName, month(endDate) as MonthNumber, SUM(price) as total_price" +
                        " FROM cases WHERE YEAR(endDate) = YEAR(current_timestamp()) AND fk_mode = 3" +
                        " GROUP BY MONTH(endDate)" +
                        " ORDER BY MONTH(endDate)";
                break;
            case "yearly":
                sql = "SELECT YEAR(endDate) as YearNumber, SUM(price) as total_price" +
                        " FROM cases WHERE YEAR(endDate) = YEAR(current_timestamp()) AND fk_mode = 3" +
                        " GROUP BY YEAR(endDate)" +
                        " ORDER BY YEAR(endDate)";
                break;
            case "average":
                sql = "SELECT AVG(price) from cases WHERE fk_mode = 3";
                break;
            case "employee-sale-list":
                sql = "SELECT U.firstName, U.lastName, SUM(C.price) as total_price from junc_jobs" +
                        " INNER JOIN users AS U ON junc_jobs.fk_employee = U.id" +
                        " INNER JOIN cases AS C ON junc_jobs.fk_case = C.id WHERE C.fk_mode = 3" +
                        " GROUP BY U.firstName" +
                        " ORDER BY SUM(C.price) DESC LIMIT 10";
                break;
            case "top-employee":
                sql = "SELECT U.firstName, U.lastName, SUM(C.price) as total_price from junc_jobs" +
                        " INNER JOIN users AS U ON junc_jobs.fk_employee = U.id" +
                        " INNER JOIN cases AS C ON junc_jobs.fk_case = C.id WHERE C.fk_mode = 3" +
                        " GROUP BY U.firstName" +
                        " ORDER BY SUM(C.price) DESC LIMIT 1";
                break;
        }

        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }


}
