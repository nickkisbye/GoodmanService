package dk.goodmanservice.goodmanservice.Repository;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class BucketRepository {

    private Connection con;
    private PreparedStatement preparedStatement;

    public BucketRepository() throws SQLException {
        this.con = DriverManager.getConnection(
                "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                "goodmanservicedb",
                "Ly02_scr-4ds");
    }

    /**
     Denne metode indsætter url'en og et case id i vores img table, så der skabes en relation mellem disse.
     */

    public void insertImage(String url, int id) throws SQLException {
        String sql = "INSERT INTO img (image, fk_cases) " +
                "VALUES (?,?)";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, url);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    /**
     Henter alle billederne ud der hører til det id der blivet parset igennem.
     */

    public ResultSet fetchImages(int id) throws SQLException {
        String sql = "SELECT image, id " +
                "from img " +
                "WHERE fk_cases=?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    /**
     sletter billedet fra en bestemt case.
     */

    public void deleteImage(int id) throws SQLException {
        String sql = "DELETE FROM img " +
                "WHERE id=?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

}
