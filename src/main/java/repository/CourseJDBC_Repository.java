package repository;

import model.Course;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Date: 5.12.2021
 * Repository class that allows the user to modify the table 'course' from the database
 */
public class CourseJDBC_Repository implements ICrudRepository<Course>{

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
    public Course findOne(Course obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT courseID, name, teacherID, credits, maxEnrollment FROM course";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (obj.getCourseID() == resultSet.getInt("courseID")) {
                    return new Course(resultSet.getInt("courseID"), resultSet.getString("name"), resultSet.getInt("teacherID"), resultSet.getInt("credits"), resultSet.getInt("maxEnrollment"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Course> findAll() throws IOException {
        List<Course> courseList = new ArrayList<>();

        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String QUERY = "SELECT courseID, name, teacherID, credits, maxEnrollment FROM course";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                Course course = new Course(resultSet.getInt("courseID"), resultSet.getString("name"), resultSet.getInt("teacherID"), resultSet.getInt("credits"), resultSet.getInt("maxEnrollment"));
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    @Override
    public Course save(Course obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course VALUES (?,?,?,?,?)")) {
            if (findOne(obj) == null) {
                preparedStatement.setInt(1, obj.getCourseID());
                preparedStatement.setString(2, obj.getName());
                preparedStatement.setInt(3, obj.getTeacherID());
                preparedStatement.setInt(4, obj.getCredits());
                preparedStatement.setInt(5, obj.getMaxEnrollment());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Course update(Course obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE course SET name = ?, teacherID = ?, credits = ?, maxEnrollment = ? WHERE courseID = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setString(1, obj.getName());
                preparedStatement.setInt(2, obj.getTeacherID());
                preparedStatement.setInt(3, obj.getCredits());
                preparedStatement.setInt(4, obj.getMaxEnrollment());
                preparedStatement.setInt(5, obj.getCourseID());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Course delete(Course obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM course WHERE courseID = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setInt(1, obj.getCourseID());
                preparedStatement.executeUpdate();
                return obj;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}