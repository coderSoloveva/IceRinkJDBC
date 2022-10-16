package ru.dao;

import org.springframework.stereotype.Component;
import ru.dto.request.DayDtoRequest;
import ru.dto.response.DayDtoResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Component
public class DayDao {

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

    public List<DayDtoResponse> getDays() throws SQLException {
        List<DayDtoResponse> dayDtoResponses = new ArrayList<>();
        String SQL = "SELECT * FROM day";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            DayDtoResponse dayDtoResponse = new DayDtoResponse();
            dayDtoResponse.setId(resultSet.getInt("id"));
            dayDtoResponse.setName(resultSet.getString("name"));
            dayDtoResponse.setStart_time(resultSet.getTime("start_time").toString());
            dayDtoResponse.setEnd_time(resultSet.getTime("end_time").toString());
            dayDtoResponses.add(dayDtoResponse);
        }
        return dayDtoResponses;
    }

    public DayDtoResponse getDayById(Integer id) throws SQLException {
        DayDtoResponse dayDtoResponse = new DayDtoResponse();
        String SQL = "SELECT * FROM day WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            dayDtoResponse.setId(resultSet.getInt("id"));
            dayDtoResponse.setName(resultSet.getString("name"));
            dayDtoResponse.setStart_time(resultSet.getTime("start_time").toString());
            dayDtoResponse.setEnd_time(resultSet.getTime("end_time").toString());
        }
        return dayDtoResponse;
    }

    public DayDtoResponse addDay(DayDtoRequest dayDtoRequest) throws SQLException {
        String SQL = "INSERT INTO day (name, start_time, end_time, is_holiday) VALUES (?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, String.valueOf(dayDtoRequest.getName()));
        preparedStatement.setTime(2, dayDtoRequest.getStart_time());
        preparedStatement.setTime(3, dayDtoRequest.getEnd_time());
        preparedStatement.setBoolean(4, dayDtoRequest.getIs_holiday());
        preparedStatement.executeUpdate();
        DayDtoResponse dayDtoResponse = dayDtoRequestToResponse(dayDtoRequest);
        ResultSet resultSetKeys = preparedStatement.getGeneratedKeys();
        if (resultSetKeys.next()){
            dayDtoResponse.setId(resultSetKeys.getInt("id"));
        }
        return dayDtoResponse;
    }

    private DayDtoResponse dayDtoRequestToResponse(DayDtoRequest dayDtoRequest) {
        DayDtoResponse dayDtoResponse = new DayDtoResponse();
        dayDtoResponse.setName(String.valueOf(dayDtoRequest.getName()));
        dayDtoResponse.setStart_time(dayDtoRequest.getStart_time().toString());
        dayDtoResponse.setEnd_time(dayDtoRequest.getEnd_time().toString());
        dayDtoResponse.setIs_holiday(dayDtoRequest.getIs_holiday());
        return dayDtoResponse;
    }

}
