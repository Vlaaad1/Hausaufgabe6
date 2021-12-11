package repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Date: 5.12.2021
 * Repository class that allows the user to modify the table 'enrollment' from the database
 */
public class EnrollmentJDBC_Repository {

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

    /**
     * @param studentID is the ID of a student
     * @return the list of courses to which the student is enrolled
     */
    public ArrayList<Integer> findCoursesByStudentID(int studentID) throws IOException {
        openConnection();
        ArrayList<Integer> coursesIDs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentID, courseID FROM enrollment";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (resultSet.getInt("studentID") == studentID) {
                    coursesIDs.add(resultSet.getInt("courseID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesIDs;
    }

    /**
     * @param courseID is the ID of a course
     * @return the list of students enrolled to the course
     */
    public ArrayList<Integer> findStudentsByCourseID(int courseID) throws IOException {
        openConnection();
        ArrayList<Integer> studentsIDs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentID, courseID FROM enrollment";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (resultSet.getInt("courseID") == courseID) {
                    studentsIDs.add(resultSet.getInt("studentID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsIDs;
    }

    /**
     * Method used to complete the rows of the 'enrollment' table from database
     * @param studentID is the ID of a student
     * @param courseID is the ID of a course
     * @return true if we successfully added the values in the 'enrollment' table from database
     */
    public boolean save(int studentID, int courseID) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO enrollment VALUES (?,?)")) {
            preparedStatement.setInt(1, studentID);
            preparedStatement.setInt(2, courseID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete a specific row in the 'enrollment' table from database
     * @param studentID is the ID of a student
     * @param courseID is the ID of a course
     * @return true if we successfully delete the row from the 'enrollment' table from database
     */
    public boolean delete(int studentID, int courseID) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrollment WHERE studentID = ? AND courseID = ?")) {
            preparedStatement.setInt(1, studentID);
            preparedStatement.setInt(2, courseID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete the rows containing the specified courseID in the 'enrollment' table from database
     * @param courseID is the ID of a course
     * @return true if we successfully delete the rows from the 'enrollment' table from database
     */
    public boolean deleteStudentsByCourseID(int courseID) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrollment WHERE courseID = ?")) {
            preparedStatement.setInt(1, courseID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete the rows containing the specified studentID in the 'enrollment' table from database
     * @param studentID is the ID of a student
     * @return true if we successfully delete the rows from the 'enrollment' table from database
     */
    public boolean deleteCoursesByStudentID(int studentID) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrollment WHERE studentID = ?")) {
            preparedStatement.setInt(1, studentID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
