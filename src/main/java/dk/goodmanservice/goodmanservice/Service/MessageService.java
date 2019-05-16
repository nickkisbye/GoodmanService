package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Message;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Component("MS")
public class MessageService implements IService<Message> {

    @Autowired
    private IRepository<Message> MR;

    @Override
    public String create(Message message) throws SQLException {
        if (message.getMsg().length() < 1 || message.getMsg().length() > 80) {
            return "Beskeden må højst være 80 karaktere";
        } else {
            MR.create(message);
            return "success";
        }
    }

    @Override
    public String edit(Message message) throws SQLException {
        if (message.getMsg().length() < 1 || message.getMsg().length() > 80) {
            return "Beskeden må højst være 80 karaktere";
        } else {
            MR.edit(message);
            return "success";
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        MR.delete(id);
    }

    @Override
    public List<Message> fetch(String option) throws SQLException {
        ResultSet rs = MR.fetch(option);
        List<Message> messageList = new ArrayList<>();
        while (rs.next()) {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setUserId(rs.getInt("users.id"));
            message.setMsg(rs.getString("msg"));
            message.setCreatedAt(rs.getString("created_at"));
            message.setUsername(rs.getString("users.firstName") + ' ' + rs.getString("users.lastName"));
            messageList.add(message);
        }
        return messageList;
    }

    @Override
    public Message findById(int id) throws SQLException {
        return null;
    }
}
