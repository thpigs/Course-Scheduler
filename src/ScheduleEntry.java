/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Timestamp;
import java.util.Calendar;
/**
 *
 * @author Daniel
 */
public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timestamp;
    
    //java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());    
    
    public ScheduleEntry(String sem, String id, String code, String stat, Timestamp time)
    {
        this.semester = sem;
        this.courseCode = code;
        this.studentID = id;
        this.status = stat;
        this.timestamp = time;
    }
    
    public String getSemester()
    {
        return semester;
    }
    
    public String getCourseCode()
    {
        return courseCode;
    }
    
    public String getStudentID()
    {
        return studentID;
    }
    
    public String getStatus()
    {
        return status;
        
    }
    
    public Timestamp getTimestamp()
    {
        return timestamp;
    }
}
