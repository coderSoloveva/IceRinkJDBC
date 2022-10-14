package ru.dao;

import org.springframework.stereotype.Component;
import ru.dto.request.AreaDtoRequest;
import ru.dto.response.AreaDtoResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class AreaDao {

    private static Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/your_ice_rink";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

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

    public List<AreaDtoResponse> getAreas() throws SQLException {
        List<AreaDtoResponse> areaDtoResponses = new ArrayList<>();
        String SQL = "SELECT * FROM area";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            AreaDtoResponse areaDtoResponse = new AreaDtoResponse();
            areaDtoResponse.setId(resultSet.getInt("id"));
            areaDtoResponse.setName(resultSet.getString("name"));
            areaDtoResponse.setCityId(resultSet.getInt("city"));
            areaDtoResponses.add(areaDtoResponse);
        }
        return areaDtoResponses;
    }

    public AreaDtoResponse getAreaById(Integer id) throws SQLException {
        AreaDtoResponse areaDtoResponse = new AreaDtoResponse();
        String SQL = "SELECT * FROM area WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            areaDtoResponse.setId(resultSet.getInt("id"));
            areaDtoResponse.setName(resultSet.getString("name"));
            areaDtoResponse.setCityId(resultSet.getInt("city"));
        }
        return areaDtoResponse;
    }

    public AreaDtoResponse createArea(AreaDtoRequest areaDtoRequest) throws SQLException {
        String SQL = "INSERT INTO area (name, city) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, areaDtoRequest.getName());
        preparedStatement.setInt(2, areaDtoRequest.getCity_id());
        AreaDtoResponse areaDtoResponse = areaDtoRequestToResponse(areaDtoRequest);
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            areaDtoResponse.setId(id);
        }
        return areaDtoResponse;
    }

    public void deleteArea(Integer id) throws SQLException {
        String SQL = "DELETE FROM area WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(SQL);
    }

    private AreaDtoResponse areaDtoRequestToResponse(AreaDtoRequest areaDtoRequest){
        AreaDtoResponse areaDtoResponse = new AreaDtoResponse();
        areaDtoResponse.setCityId(areaDtoRequest.getCity_id());
        areaDtoResponse.setName(areaDtoRequest.getName());
        return areaDtoResponse;
    }
}
