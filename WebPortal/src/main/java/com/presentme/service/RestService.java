package com.presentme.service;

import com.presentme.dao.FacultyDao;
import com.presentme.dao.LectureDao;
import com.presentme.dao.StudentDao;
import com.presentme.entity.Attendance;
import com.presentme.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class RestService {

    private StudentDao studentDao;
    private FacultyDao facultyDao;
    private LectureDao lectureDao;

    @Autowired
    public RestService(StudentDao studentDao, FacultyDao facultyDao, LectureDao lectureDao) {
        this.studentDao = studentDao;
        this.facultyDao = facultyDao;
        this.lectureDao = lectureDao;
    }

    public String getStudentStatus(String user, String pass, String imei){
        return  studentDao.getStudent(user,pass,imei);
    }

    public String getTeacherLogin(String id, String pass){
        String status = facultyDao.getFacultyLoginStatus(id, pass);
        if (status.equals("1")){
            status = status + "," + studentDao.getAllGroupCommas();
        }
        return status;
    }

    public Integer generateLecture(String tuser, String slot, String sub, String groups){
        return facultyDao.generateLecture(tuser, slot, sub, groups);
    }

    public void commitLecture(Integer lectureID) {
        lectureDao.commitLecture(lectureID);
    }

    public String presentme(String lectureId, String studentUser, String group) {

        for (int i=0;i<4;i++) {
            lectureId = new String(Base64.getMimeDecoder().decode(lectureId.getBytes()), StandardCharsets.UTF_8);
        }
        return lectureDao.presentMe(Integer.valueOf(lectureId), studentUser, group);
    }

    public String getStudentGroup(String roll) {

        return studentDao.getStudentGroup(roll);
    }

    public String checkBlock(String roll) {

        Student s = studentDao.getStudent(roll);
        return String.valueOf(s.getBlock());

    }
}
