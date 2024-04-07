package dao;

import entity.Specific;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import util.ConnectionManager;

public class SpecificDao {

    public static final String CREATE_SQL = """
            INSERT INTO specifics(event_id, description, ticket_count, price, address, date, day_of_week, time, is_private, code, rating, uid) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, event_id, description, ticket_count, price, address, date, day_of_week, time, is_private, code, rating, uid
            FROM specifics
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, event_id, description, ticket_count, price, address, date, day_of_week, time, is_private, code, rating, uid
            FROM specifics
            WHERE uid = ?
            """;

    public static final String UPDATE_SQL = """
            UPDATE specifics 
            SET event_id = ?, 
                description = ?, 
                ticket_count = ?, 
                price = ?, 
                address = ?, 
                date = ?, 
                day_of_week = ?, 
                time = ?, 
                is_private = ?, 
                code = ?, 
                rating = ? 
            WHERE uid = ?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM specifics
            WHERE uid = ?
            """;

    public Specific create(Specific specific) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.setInt(1, specific.getEventId());
            preparedStatement.setString(2, specific.getDescription());
            preparedStatement.setInt(3, specific.getTicketCount());
            preparedStatement.setFloat(4, specific.getPrice());
            preparedStatement.setString(5, specific.getAddress());
            preparedStatement.setDate(6, specific.getDate());
            preparedStatement.setObject(7, specific.getDayOfWeek());
            preparedStatement.setTime(8, specific.getTime());
            preparedStatement.setBoolean(9, specific.isPrivate());
            preparedStatement.setString(10, specific.getCode());
            preparedStatement.setFloat(11, specific.getRating());
            preparedStatement.setObject(12, specific.getUid());
            preparedStatement.executeUpdate();
            return specific;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Specific getById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildSpecificEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Specific> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Specific> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildSpecificEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Specific specific) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, specific.getEventId());
            preparedStatement.setString(2, specific.getDescription());
            preparedStatement.setInt(3, specific.getTicketCount());
            preparedStatement.setFloat(4, specific.getPrice());
            preparedStatement.setString(5, specific.getAddress());
            preparedStatement.setDate(6, specific.getDate());
            preparedStatement.setObject(7, specific.getDayOfWeek());
            preparedStatement.setTime(8, specific.getTime());
            preparedStatement.setBoolean(9, specific.isPrivate());
            preparedStatement.setString(10, specific.getCode());
            preparedStatement.setFloat(11, specific.getRating());
            preparedStatement.setObject(12, specific.getUid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Specific buildSpecificEntity(ResultSet resultSet) throws SQLException {
        Specific specific = new Specific();
        specific.setId(resultSet.getInt("id"));
        specific.setEventId(resultSet.getInt("event_id"));
        specific.setDescription(resultSet.getString("description"));
        specific.setTicketCount(resultSet.getInt("ticket_count"));
        specific.setPrice(resultSet.getFloat("price"));
        specific.setAddress(resultSet.getString("address"));
        specific.setDate(resultSet.getDate("date"));
        specific.setDayOfWeek(resultSet.getObject("day_of_week", Integer.class));
        specific.setTime(resultSet.getTime("time"));
        specific.setPrivate(resultSet.getBoolean("is_private"));
        specific.setCode(resultSet.getString("code"));
        specific.setRating(resultSet.getFloat("rating"));
        specific.setUid(resultSet.getObject("uid", UUID.class));
        return specific;
    }
}