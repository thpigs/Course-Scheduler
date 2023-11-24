
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel
 */
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<ScheduleEntry> scheduleStudent;
    private static ArrayList<ScheduleEntry> scheduleByCourse;
    private static ArrayList<ScheduleEntry> droppedCourseStudents;
    private static int count;
    private static String status;
    private static String bumpedStudent;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getCourseStatus;
    private static PreparedStatement getSchedulesByCourse;
    private static PreparedStatement dropStudent;
    private static PreparedStatement updateWaitlist;
    private static PreparedStatement getWaitlist;
    private static PreparedStatement hasWaitlist;
    private static PreparedStatement adminDropCourse;
    private static PreparedStatement adminDropCourseStudentList;
    private static PreparedStatement studentDropCourse;
    private static ResultSet resultSet;
    
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getStudentID());
            addScheduleEntry.setString(3, entry.getCourseCode());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        scheduleStudent = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? AND studentid = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry current = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                scheduleStudent.add(current);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleStudent;
    }
    
    public static int getScheduledbyStudentCount(String currentSemester, String courseCode)
    {
        count = 0;
        connection = DBConnection.getConnection();
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? AND coursecode = ?");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            
            while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
        
    }
    
    public static String getCourseStatus(String id, String sem, String code)
    {
        status = "";
        connection = DBConnection.getConnection();
        try
        {
            getCourseStatus = connection.prepareStatement("select status from app.schedule where semester = ? AND coursecode = ? AND studentid = ?");
            getCourseStatus.setString(1, sem);
            getCourseStatus.setString(2, code);
            getCourseStatus.setString(3, id);
            resultSet = getCourseStatus.executeQuery();
            
            while(resultSet.next())
            {
                status = resultSet.getString(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return status;
        
    }
    
    public static ArrayList<ScheduleEntry> getSchedulesByCourse(String sem, String code)
    {
        scheduleByCourse = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try
        {
            getSchedulesByCourse = connection.prepareStatement("select * from app.schedule where semester = ? AND coursecode = ? order by timestamp asc");
            getSchedulesByCourse.setString(1, sem);
            getSchedulesByCourse.setString(2, code);
            resultSet = getSchedulesByCourse.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry current = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                scheduleByCourse.add(current);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleByCourse;
    }
    
    public static void dropStudent(String id)
    {
        try
        {
            dropStudent = connection.prepareStatement("delete from app.schedule where studentid = ?");
            dropStudent.setString(1, id);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static String updateWaitlist(String sem, String code)
    {
        bumpedStudent = "";
        
        try
        {
            getWaitlist = connection.prepareStatement("select studentid from app.schedule where status = ? AND semester = ? AND coursecode = ?");
            getWaitlist.setString(1, "w");
            getWaitlist.setString(2, sem);
            getWaitlist.setString(3, code);
            resultSet = getWaitlist.executeQuery();
            resultSet.next();
            bumpedStudent = resultSet.getString(1);
            
            updateWaitlist = connection.prepareStatement("update app.schedule set status = ? where semester = ? AND coursecode = ? AND studentid = ?");
            updateWaitlist.setString(1, "s");
            updateWaitlist.setString(2, sem);
            updateWaitlist.setString(3, code);
            updateWaitlist.setString(4, bumpedStudent);
            updateWaitlist.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return bumpedStudent;
    }
    
    public static boolean hasWaitlist(String sem, String code)
    {
        connection = DBConnection.getConnection();
        try
        {
            hasWaitlist = connection.prepareStatement("select count(status) from app.schedule where status = ? AND coursecode = ? AND semester = ?");
            hasWaitlist.setString(1, "w");
            hasWaitlist.setString(2,code);
            hasWaitlist.setString(3,sem);
            resultSet = hasWaitlist.executeQuery();
            resultSet.next();
            
            if (resultSet.getInt(1) > 0)
                return true;
            else
                return false;
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return false;
    }

    public static ArrayList<ScheduleEntry> adminDropCourse(String sem, String code)
    {
        droppedCourseStudents = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try
        {
            adminDropCourseStudentList = connection.prepareStatement("select * from app.schedule where semester = ? AND coursecode = ?");
            adminDropCourseStudentList.setString(1, sem);
            adminDropCourseStudentList.setString(2, code);
            resultSet = adminDropCourseStudentList.executeQuery();
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                droppedCourseStudents.add(entry);
            }
            
            adminDropCourse = connection.prepareStatement("delete from app.schedule where semester = ? AND coursecode = ?");
            adminDropCourse.setString(1, sem);
            adminDropCourse.setString(2, code);
            adminDropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return droppedCourseStudents;
    }
    
    public static void studentDropCourse(String sem, String code, String id)
    {
         try
        {
            studentDropCourse = connection.prepareStatement("delete from app.schedule where semester = ? AND coursecode = ? AND studentid = ?");
            studentDropCourse.setString(1, sem);
            studentDropCourse.setString(2, code);
            studentDropCourse.setString(3, id);
            studentDropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
