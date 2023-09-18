package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Service.MessageService;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            if(message.getMessage_text() != "" && message.getMessage_text().length() < 255) {
                String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, message.getTime_posted_epoch());

                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_message_id = (int) pkeyResultSet.getInt("message_id");
                    return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                }     
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        
        return null;
    }

    public Message deleteMessage(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
                String sql1 = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql1);

                //write preparedStatement's setInt method here.
                preparedStatement.setInt(1, message_id);
                ResultSet rs = preparedStatement.executeQuery();


                String sql2 = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, message_id);

                preparedStatement2.executeUpdate();  

                
                if(rs != null) 
                {while(rs.next()){
                    Message deletedMessage = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                    return deletedMessage;
                }}
                
                


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        
        return null;
    }
}
