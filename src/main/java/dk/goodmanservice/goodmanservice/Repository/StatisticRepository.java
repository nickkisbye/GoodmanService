package dk.goodmanservice.goodmanservice.Repository;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
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

    public ResultSet totalPriceSum() throws SQLException {
        sql = "SELECT SUM(price) as total_price FROM cases WHERE fk_mode = 3";
        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    public ResultSet totalMonthlyEarnings() throws SQLException {
        sql = "SELECT MONTHNAME(endDate) as MonthName, month(endDate) as MonthNumber, SUM(price) as total_price" +
                " FROM cases WHERE YEAR(endDate) = YEAR(current_timestamp()) AND fk_mode = 3" +
                " GROUP BY MONTH(endDate)" +
                " ORDER BY MONTH(endDate)";
        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    public ResultSet totalYearlyEarnings() throws SQLException {
        sql = "SELECT YEAR(endDate) as YearNumber, SUM(price) as total_price" +
                " FROM cases WHERE YEAR(endDate) = YEAR(current_timestamp()) AND fk_mode = 3" +
                " GROUP BY YEAR(endDate)" +
                " ORDER BY YEAR(endDate)";
        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    public ResultSet averageEarnings() throws SQLException {
        sql = "SELECT AVG(price) as average_price from cases WHERE fk_mode = 3";
        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    public ResultSet employeeSaleList() throws SQLException {
        sql = "SELECT U.firstName, U.lastName, SUM(C.price) as total_price from junc_jobs" +
                " INNER JOIN users AS U ON junc_jobs.fk_employee = U.id" +
                " INNER JOIN cases AS C ON junc_jobs.fk_case = C.id WHERE C.fk_mode = 3" +
                " GROUP BY U.firstName" +
                " ORDER BY SUM(C.price) DESC LIMIT 10";
        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    public ResultSet topEmployee() throws SQLException {
        sql = "SELECT U.firstName, U.lastName, SUM(C.price) as total_price from junc_jobs" +
                " INNER JOIN users AS U ON junc_jobs.fk_employee = U.id" +
                " INNER JOIN cases AS C ON junc_jobs.fk_case = C.id WHERE C.fk_mode = 3" +
                " GROUP BY U.firstName" +
                " ORDER BY SUM(C.price) DESC LIMIT 1";
        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }


}
