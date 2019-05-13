package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Statistic;
import dk.goodmanservice.goodmanservice.Repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository SR;

    public List<Statistic> fetch(String option) throws SQLException {
        List<Statistic> sList = new ArrayList<>();
        ResultSet rs = null;

        switch (option) {
            case "total":
                rs = SR.totalPriceSum();
                while (rs.next()) {
                    Statistic statistic = new Statistic();
                    statistic.setPriceSum(rs.getInt("total_price"));
                    sList.add(statistic);
                }
                break;
            case "monthly":
                rs = SR.totalMonthlyEarnings();
                while (rs.next()) {
                    Statistic statistic = new Statistic();
                    statistic.setMonthName(rs.getString("MonthName"));
                    statistic.setMonthNumber(rs.getInt("MonthNumber"));
                    statistic.setPriceSum(rs.getInt("total_price"));
                    sList.add(statistic);
                }
                break;
            case "yearly":
                rs = SR.totalYearlyEarnings();
                while (rs.next()) {
                    Statistic statistic = new Statistic();
                    statistic.setYearNumber(rs.getInt("YearNumber"));
                    statistic.setPriceSum(rs.getInt("total_price"));
                    sList.add(statistic);
                }
                break;
            case "average":
                rs = SR.averageEarnings();
                while (rs.next()) {
                    Statistic statistic = new Statistic();
                    statistic.setPriceSum(rs.getInt("average_price"));
                    sList.add(statistic);
                }
                break;
            case "employee-top10":
                rs = SR.employeeSaleList();
                while (rs.next()) {
                    Statistic statistic = new Statistic();
                    statistic.setEmployeeFirstName(rs.getString("U.firstName"));
                    statistic.setEmployeeLastName(rs.getString("U.lastname"));
                    statistic.setPriceSum(rs.getInt("total_price"));
                    sList.add(statistic);
                }
                break;
            case "top-employee":
                rs = SR.topEmployee();
                while (rs.next()) {
                    Statistic statistic = new Statistic();
                    statistic.setEmployeeFirstName(rs.getString("U.firstName"));
                    statistic.setEmployeeLastName(rs.getString("U.lastname"));
                    statistic.setPriceSum(rs.getInt("total_price"));
                    sList.add(statistic);
                }
                break;
        }
        return sList;

    }

}
