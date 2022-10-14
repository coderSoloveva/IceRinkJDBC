package ru.dao;

import org.springframework.stereotype.Component;
import ru.dto.request.ClosingDateDto;
import ru.dto.request.IceRinkDtoRequest;
import ru.dto.response.IceRinkDtoResponse;

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
public class IceRinkDao {

    private static Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/your_ice_rink";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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

    public List<IceRinkDtoResponse> getIceRinks() {
        List<IceRinkDtoResponse> iceRinkDtoResponses = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM ice_rink";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                IceRinkDtoResponse iceRinkDtoResponse = new IceRinkDtoResponse();
                setParamsIceRinkDtoResponse(resultSet, iceRinkDtoResponse);
                iceRinkDtoResponses.add(iceRinkDtoResponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iceRinkDtoResponses;
    }

    public IceRinkDtoResponse getIceRinkById(Integer id) {
        IceRinkDtoResponse iceRinkDtoResponse = new IceRinkDtoResponse();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM ice_rink";
            SQL += " WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next())
                setParamsIceRinkDtoResponse(resultSet, iceRinkDtoResponse);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iceRinkDtoResponse;
    }

    public IceRinkDtoResponse createIceRink(IceRinkDtoRequest iceRinkDtoRequest) {
        IceRinkDtoResponse iceRinkDtoResponse = iceRinkDtoRequestToResponse(iceRinkDtoRequest);
        PreparedStatement preparedStatement = null;
        String SQL = "INSERT INTO ice_rink ";
        if (iceRinkDtoRequest.getClosing_date() == null) {
            SQL += "(name, city_id, area_id, opening_date, description)";
            SQL += "VALUES (?,?,?,?,?)";
        } else {
            SQL += "VALUES (?,?,?,?,?,?)";
        }
        try {
            preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setString(i++, iceRinkDtoRequest.getName());
            preparedStatement.setInt(i++, iceRinkDtoRequest.getCity_id());
            preparedStatement.setInt(i++, iceRinkDtoRequest.getArea_id());
            preparedStatement.setDate(i++, iceRinkDtoRequest.getOpening_date());
            if (iceRinkDtoRequest.getClosing_date() != null)
                preparedStatement.setDate(i++, iceRinkDtoRequest.getClosing_date());
            preparedStatement.setString(i, iceRinkDtoRequest.getDescription());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    iceRinkDtoResponse.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iceRinkDtoResponse;
    }


    public void addClosingDateIceRink(ClosingDateDto closingDateDto) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "UPDATE ice_rink SET closing_date = \'" + closingDateDto.getClosing_date() + "\'";
            SQL += " WHERE id = " + closingDateDto.getId();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIceRinkById(Integer id) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "DELETE FROM ice_rink WHERE id = " + id;
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private IceRinkDtoResponse iceRinkDtoRequestToResponse(IceRinkDtoRequest iceRinkDtoRequest){
        IceRinkDtoResponse iceRinkDtoResponse = new IceRinkDtoResponse();
        iceRinkDtoResponse.setName(iceRinkDtoRequest.getName());
        iceRinkDtoResponse.setCity_id(iceRinkDtoRequest.getCity_id());
        iceRinkDtoResponse.setArea_id(iceRinkDtoRequest.getArea_id());
        iceRinkDtoResponse.setOpening_date(formatter.format(iceRinkDtoRequest.getOpening_date()));
        if (iceRinkDtoRequest.getClosing_date() != null)
            iceRinkDtoResponse.setClosing_date(formatter.format(iceRinkDtoRequest.getClosing_date()));
        iceRinkDtoResponse.setDescription(iceRinkDtoRequest.getDescription());
        return iceRinkDtoResponse;
    }

    private void setParamsIceRinkDtoResponse(ResultSet resultSet, IceRinkDtoResponse iceRinkDtoResponse) throws SQLException {
        iceRinkDtoResponse.setId(resultSet.getInt("id"));
        String name = resultSet.getString("name");
        iceRinkDtoResponse.setName(resultSet.getString("name"));
        iceRinkDtoResponse.setCity_id(resultSet.getInt("city_id"));
        iceRinkDtoResponse.setArea_id(resultSet.getInt("area_id"));
        iceRinkDtoResponse.setOpening_date(formatter.format(resultSet.getDate("opening_date")));
        if (resultSet.getDate("closing_date") != null)
            iceRinkDtoResponse.setClosing_date(formatter.format(resultSet.getDate("closing_date")));
        iceRinkDtoResponse.setDescription(resultSet.getString("description"));
    }
}
