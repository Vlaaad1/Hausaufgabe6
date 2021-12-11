package repository;

import model.Teacher;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Date: 5.12.2021
 * Repository class that allows the user to modify the table 'teacher' from the database
 */
public class TeacherJDBC_Repository implements ICrudRepository<Teacher>{

    private String DB_URL;
    private String USER;
    private String PASSWORD;

    /**
     * This function reads the necessary dates from the 'config.properties' file
     */
    public void openConnection() throws IOException {
        FileInputStream file = new FileInputStream("C:\\Users\\Vlad\\IdeaProjects\\Hausaufgabe6\\src\\main\\resources\\config.properties");
        Properties properties = new Properties();
        properties.load(file);
        DB_URL = properties.getProperty("DB_URL");
        USER = properties.getProperty("USER");
        PASSWORD = properties.getProperty("PASSWORD");
    }



    @Override
    public Teacher findOne(Teacher obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String QUERY = "SELECT teacherID, firstName, lastName FROM teacher";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (obj.getTeacherID() == resultSet.getInt("teacherID")) {
                    return new Teacher(resultSet.getString("firstName"), resultSet.getString("lastName"),
                            resultSet.getInt("teacherID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Teacher> findAll() throws IOException {
        List<Teacher> teacherList = new ArrayList<>();

        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String QUERY = "SELECT teacherID, firstName, lastName FROM teacher";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                Teacher teacher = new Teacher(resultSet.getString("firstName"), resultSet.getString("lastName"),
                        resultSet.getInt("teacherID"));
                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherList;
    }

    @Override
    public Teacher save(Teacher obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO teacher VALUES (?,?,?)")) {
            if (findOne(obj) == null) {
                preparedStatement.setInt(1, obj.getTeacherID());
                preparedStatement.setString(2, obj.getFirstName());
                preparedStatement.setString(3, obj.getLastName());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Teacher update(Teacher obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE teacher SET firstName = ?, lastName = ? WHERE teacherID = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setString(1, obj.getFirstName());
                preparedStatement.setString(2, obj.getLastName());
                preparedStatement.setInt(3, obj.getTeacherID());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Teacher delete(Teacher obj) throws IOException {

        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM teacher WHERE teacherID = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setInt(1, obj.getTeacherID());
                preparedStatement.executeUpdate();
                return obj;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}