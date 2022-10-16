package ru.dao;

import org.springframework.stereotype.Component;
import ru.dto.request.CommentDtoRequest;
import ru.dto.response.CommentDtoResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDao {

    private static Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/your_ice_rink";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CommentDtoResponse> getComments() throws SQLException {
        List<CommentDtoResponse> commentDtoResponses = new ArrayList<>();
        String SQL = "SELECT * FROM comment";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            CommentDtoResponse commentDtoResponse = new CommentDtoResponse();
            commentDtoResponse.setId(resultSet.getInt("id"));
            commentDtoResponse.setText(resultSet.getString("text"));
            commentDtoResponse.setIce_rink_id(resultSet.getInt("ice_rink_id"));
            commentDtoResponse.setUser_name(resultSet.getString("user_name"));
            commentDtoResponse.setTime(resultSet.getString("time"));
            commentDtoResponses.add(commentDtoResponse);
        }
        return commentDtoResponses;
    }

    public CommentDtoResponse getCommentById(Integer id) throws SQLException {
        CommentDtoResponse commentDtoResponse = new CommentDtoResponse();
        String SQL = "SELECT * FROM comment WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            commentDtoResponse.setId(resultSet.getInt("id"));
            commentDtoResponse.setText(resultSet.getString("text"));
            commentDtoResponse.setIce_rink_id(resultSet.getInt("ice_rink_id"));
            commentDtoResponse.setUser_name(resultSet.getString("user_name"));
            commentDtoResponse.setTime(resultSet.getString("time"));
        }
        return commentDtoResponse;
    }

    public CommentDtoResponse addComment(CommentDtoRequest commentDtoRequest) throws SQLException {
        String SQL = "INSERT INTO comment (text, ice_rink_id, user_name, time) VALUES (?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, commentDtoRequest.getText());
        preparedStatement.setInt(2, commentDtoRequest.getIce_rink_id());
        preparedStatement.setString(3, commentDtoRequest.getUser_name());
        preparedStatement.setTimestamp(4, commentDtoRequest.getTime());
        preparedStatement.execute();
        CommentDtoResponse commentDtoResponse = commentDtoRequestToResponse(commentDtoRequest);
        ResultSet resultSetKeys = preparedStatement.getGeneratedKeys();
        if (resultSetKeys.next()){
            commentDtoResponse.setId(resultSetKeys.getInt("id"));
        }
        return commentDtoResponse;
    }

    private CommentDtoResponse commentDtoRequestToResponse(CommentDtoRequest commentDtoRequest) {
        CommentDtoResponse commentDtoResponse = new CommentDtoResponse();
        commentDtoResponse.setText(commentDtoRequest.getText());
        commentDtoResponse.setIce_rink_id(commentDtoRequest.getIce_rink_id());
        commentDtoResponse.setUser_name(commentDtoRequest.getUser_name());
        String time = formatter.format(commentDtoRequest.getTime());
        commentDtoResponse.setTime(time);
        return commentDtoResponse;
    }

}
