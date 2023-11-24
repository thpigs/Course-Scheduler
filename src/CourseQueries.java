/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Daniel
 */
public class CourseQueries {
    private static Connection connection;
    private static ArrayList<CourseEntry> courses;
    private static ArrayList<String> courseCodes;
    private static int seats;
    private static int seatsFilled;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement getFilledSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        courses = new ArrayList<CourseEntry>();
        connection = DBConnection.getConnection();
        try
        {
            getAllCourses = connection.prepareStatement("select * from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                CourseEntry current = new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                courses.add(current);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester, courseCode, description, seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        courseCodes = new ArrayList<String>();
        connection = DBConnection.getConnection();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select courseCode from app.course where semester = ?");
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode)
    {
        seats = 0;
        connection = DBConnection.getConnection();
        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester = ? AND courseCode = ?");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            
            while(resultSet.next())
            {
                seats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
    }   
    
    //Useless because of ScheduleQueries.getScheduledbyStudentCount(..)
    public static int getCourseSeatsFilled(String semester, String courseCode)
    {
        seatsFilled = 0;
        connection = DBConnection.getConnection();
        try
        {
            getFilledSeats = connection.prepareStatement("select studentid from app.schedule where semester = ? AND courseCode = ?");
            getFilledSeats.setString(1, semester);
            getFilledSeats.setString(2, courseCode);
            resultSet = getFilledSeats.executeQuery();
            
            resultSet.last();
            seatsFilled = resultSet.getRow();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seatsFilled;
    }   
    
    public static void dropCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("delete from app.course where semester = ? AND coursecode = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
