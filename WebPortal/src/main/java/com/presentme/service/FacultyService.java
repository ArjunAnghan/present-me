package com.presentme.service;

import com.presentme.dao.FacultyDao;
import com.presentme.dao.LectureDao;
import com.presentme.entity.Faculty;
import com.presentme.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {

    private FacultyDao facultyDao;
    private LectureDao lectureDao;

    @Autowired
    public FacultyService(FacultyDao facultyDao, LectureDao lectureDao) {
        this.facultyDao = facultyDao;
        this.lectureDao = lectureDao;
    }

    public Faculty getFaculty(String username) {
        return facultyDao.getFacultywithLectures(username);

    }

    public Lecture getLecture(Integer lectureId) {
        return lectureDao.getAttendence(lectureId);
    }

    public void addStudent(String username, Integer lectId) {
        String[] usernames = username.split(",");
        for (String s : usernames) {
            lectureDao.addStudent(s.trim().toUpperCase(), lectId);
        }
    }
}
