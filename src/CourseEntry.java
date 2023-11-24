/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel
 */
public class CourseEntry {
    private String semester;
    private String courseCode;
    private String description;
    private int classSeats;
    
    public CourseEntry(String sem, String code, String desc, int seats)
    {
        this.semester = sem;
        this.courseCode = code;
        this.description = desc;
        this.classSeats = seats;
    }
    
    public String getSemester()
    {
        return semester;
    }
    
    public String getCourseCode()
    {
        return courseCode;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public int getSeats()
    {
        return classSeats;
    }
}
