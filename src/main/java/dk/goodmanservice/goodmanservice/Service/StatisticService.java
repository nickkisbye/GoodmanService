package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Statistic;
import dk.goodmanservice.goodmanservice.Repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticService {

    @Autowired
    private StatisticRepository SR;

    public List<Statistic> fetch(String option) throws SQLException {

        ResultSet rs = SR.fetch(option);
        List<Statistic> sList = new ArrayList<>();

        while (rs.next()) {

        }
        return sList;

    }

}
