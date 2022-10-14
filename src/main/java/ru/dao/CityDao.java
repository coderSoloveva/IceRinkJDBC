package ru.dao;

import org.springframework.stereotype.Component;
import ru.dto.request.CityDtoRequest;
import ru.dto.response.CityDtoResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityDao {

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

    public List<CityDtoResponse> getСities() throws SQLException {
        List<CityDtoResponse> cityDtoResponses = new ArrayList<>();
        String SQL = "SELECT * FROM city";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CityDtoResponse cityDtoResponse = new CityDtoResponse();
            cityDtoResponse.setId(resultSet.getInt("id"));
            cityDtoResponse.setName(resultSet.getString("name"));
            cityDtoResponses.add(cityDtoResponse);
        }
        return cityDtoResponses;
    }

    public CityDtoResponse getCityById(Integer id) throws SQLException {
        CityDtoResponse cityDtoResponse = new CityDtoResponse();
        String SQL = "SELECT * FROM city WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            cityDtoResponse.setId(resultSet.getInt("id"));
            cityDtoResponse.setName(resultSet.getString("name"));
        }
        return cityDtoResponse;
    }

    public CityDtoResponse createCity(CityDtoRequest cityDtoRequest) throws SQLException {
        String SQL = "INSERT INTO city (name) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, cityDtoRequest.getName());
        CityDtoResponse cityDtoResponse = new CityDtoResponse();
        cityDtoResponse.setName(cityDtoRequest.getName());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            cityDtoResponse.setId(id);
        }
        return cityDtoResponse;
    }

    public void deleteCity(Integer id) throws SQLException {
        String SQL = "DELETE FROM city WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(SQL);
    }
}
