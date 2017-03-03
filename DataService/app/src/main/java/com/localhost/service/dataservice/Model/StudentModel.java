package com.localhost.service.dataservice.Model;

/**
 * Created by user on 1/30/2017.
 */

public class StudentModel {
    int Student_ID;
    String Student_FirstName;
    String Student_LastName;
    Boolean Student_ProfilePicture;
    String Student_Apikey;

    public int getStudent_ID() {
        return Student_ID;
    }

    public void setStudent_ID(int student_ID) {
        Student_ID = student_ID;
    }

    public String getStudent_FirstName() {
        return Student_FirstName;
    }

    public void setStudent_FirstName(String student_FirstName) {
        Student_FirstName = student_FirstName;
    }

    public String getStudent_LastName() {
        return Student_LastName;
    }

    public void setStudent_LastName(String student_LastName) {
        Student_LastName = student_LastName;
    }

    public Boolean getStudent_ProfilePicture() {
        return Student_ProfilePicture;
    }

    public void setStudent_ProfilePicture(Boolean student_ProfilePicture) {
        Student_ProfilePicture = student_ProfilePicture;
    }

    public String getStudent_Apikey() {
        return Student_Apikey;
    }

    public void setStudent_Apikey(String student_Apikey) {
        Student_Apikey = student_Apikey;
    }
}
