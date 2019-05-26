package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Repository
@Component("MR")
public class MessageRepository implements IRepository<Message> {

    private PreparedStatement preparedStatement;
    private Connection con;
    private String sql;
    private DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public MessageRepository() throws SQLException {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
    }

    /**
     Denne metode opretter et opslag i databasen.
     Calender.getInstance(); opretter den nuværende dato og sætter det ind i datbasen.
     */

    @Override
    public void create(Message message) throws SQLException {
        sql = "INSERT INTO posts (msg, fk_user, created_at) " +
                "VALUES (?,?,?)";

        Calendar calender = Calendar.getInstance();

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,message.getMsg());
        preparedStatement.setInt(2, message.getUserId());
        preparedStatement.setString(3, dateformat.format(calender.getTime()));
        preparedStatement.execute();
    }

    /**
     Bruges ikke, men er der hvis systemet skal udvides.
     */

    @Override
    public void edit(Message message) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM posts " +
                "WHERE id=?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    /**
     Denne metode henter alle opslag fra databasen. Hvis option er sat til "latest-10",
     hiver den kun de seneste 10 beskeder ud.
     */

    @Override
    public ResultSet fetch(String option) throws SQLException {
        sql = "SELECT * FROM posts " +
                "INNER JOIN users ON posts.fk_user = users.id " +
                "ORDER BY posts.id DESC";

        if(option.equals("latest-10")) {
            sql += " LIMIT 10";
        }
        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    /**
     Bruges ikke, men er der hvis systemet skal udvides.
     */

    @Override
    public ResultSet findById(int id) throws SQLException {
        return null;
    }
}
