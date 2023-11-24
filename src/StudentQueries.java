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
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<StudentEntry> students;
    private static String foundStudent;
    private static ArrayList<String> studentName;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement findStudentId;
    private static PreparedStatement findStudentNameById;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        students = new ArrayList<StudentEntry>();
        connection = DBConnection.getConnection();
        try
        {
            getAllStudents = connection.prepareStatement("select * from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry current = new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
                students.add(current);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static String findStudentId(String name)
    {
        foundStudent = "";
        String[] fullName = name.split(", ");
        connection = DBConnection.getConnection();
        try
        {
            findStudentId = connection.prepareStatement("select studentid from app.student where lastname = ? AND firstname = ?");
            findStudentId.setString(1, fullName[0]);
            findStudentId.setString(2, fullName[1]);
            resultSet = findStudentId.executeQuery();
            
            while(resultSet.next())
            {
                foundStudent = resultSet.getString(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return foundStudent;
    }

    public static ArrayList<String> findStudentNameById(String id)
    {
        studentName = new ArrayList<String>();
        
        connection = DBConnection.getConnection();
        try
        {
            findStudentNameById = connection.prepareStatement("select lastname, firstname from app.student where studentid = ?");
            findStudentNameById.setString(1, id);
            resultSet = findStudentNameById.executeQuery();
            
            resultSet.next();
            studentName.add(resultSet.getString(1));
            studentName.add(resultSet.getString(2));
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentName;
    }
    
    public static void dropStudent(String id)
    {
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            dropStudent.setString(1, id);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
