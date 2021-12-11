package repository;

import model.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Date: 5.12.2021
 * Repository class that allows the user to modify the table 'student' from the database
 */
public class StudentJDBC_Repository implements ICrudRepository<Student> {

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
    public Student findOne(Student obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentID, firstName, lastName, totalCredits FROM student";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (obj.getStudentID() == resultSet.getInt("studentID")) {
                    return new Student(resultSet.getString("firstName"),
                            resultSet.getString("lastName"), resultSet.getInt("studentID"),
                            resultSet.getInt("totalCredits"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Student> findAll() throws IOException {
        ArrayList<Student> studentList = new ArrayList<>();

        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentID, firstName, lastName, totalCredits FROM student";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("firstName"),
                        resultSet.getString("lastName"), resultSet.getInt("studentID"),
                        resultSet.getInt("totalCredits"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public Student save(Student obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?)")) {
            if (findOne(obj) == null) {
                preparedStatement.setInt(1, obj.getStudentID());
                preparedStatement.setString(2, obj.getFirstName());
                preparedStatement.setString(3, obj.getLastName());
                preparedStatement.setInt(4, obj.getTotalCredits());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Student update(Student obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE student SET firstName = ?, lastName = ?, totalCredits = ? WHERE studentID = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setString(1, obj.getFirstName());
                preparedStatement.setString(2, obj.getLastName());
                preparedStatement.setInt(3, obj.getTotalCredits());
                preparedStatement.setInt(4, obj.getStudentID());
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Student delete(Student obj) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM student WHERE studentID = ?")) {
            if (findOne(obj) != null) {
                preparedStatement.setInt(1, obj.getStudentID());
                preparedStatement.executeUpdate();
                return obj;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}