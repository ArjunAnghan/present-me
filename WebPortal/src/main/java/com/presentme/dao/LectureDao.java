package com.presentme.dao;

import com.presentme.entity.Attendance;
import com.presentme.entity.Lecture;
import com.presentme.entity.Student;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LectureDao {
    private SessionFactory sessionFactory;

    @Autowired
    public LectureDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Lecture getAttendence(Integer lectureId) {
        Session session = sessionFactory.getCurrentSession();
        Lecture lecture = session.get(Lecture.class, lectureId);
        Hibernate.initialize(lecture.getAttendances());
        return lecture;

    }

    @Transactional
    public void commitLecture(Integer lectureID) {
        Session session = sessionFactory.getCurrentSession();
        Lecture lecture = session.get(Lecture.class, lectureID);
        lecture.setFlag(0);
        session.save(lecture);
    }

    @Transactional
    public String presentMe(Integer lectureId, String studentUser, String group){
        Session session = sessionFactory.getCurrentSession();

        List<Student> studentList = session.createQuery("From Student s where s.username = :user", Student.class)
                .setParameter("user", studentUser)
                .getResultList();
        if (studentList.size()>0){
            Student student = studentList.get(0);

            Lecture lecture = session.get(Lecture.class, lectureId);
            Hibernate.initialize(lecture.getAttendances());
            for (Attendance attendance : lecture.getAttendances()) {
                if (attendance.getStudent().getUsername().equals(studentUser)) {
                    return "0";
                }
            }
            if (lecture.getFlag() == 1 && lecture.getGroupEntity().contains(group)) {
                Attendance attendance = new Attendance();

                attendance.setLect(lecture);
                attendance.setStudent(student);
                session.save(attendance);
                return "1";
            }
        }

        return "0";
    }

    @Transactional
    public void addStudent(String username, Integer lectId) {
        Session session = sessionFactory.getCurrentSession();

        List<Student> studentList = session.createQuery("From Student s where s.username = :user", Student.class)
                .setParameter("user", username.toUpperCase())
                .getResultList();
        if (studentList.size()>0){
            Lecture lecture = session.get(Lecture.class, lectId);
            Hibernate.initialize(lecture.getAttendances());
            for (Attendance a:lecture.getAttendances()) {
                if (a.getStudent().getUsername().equals(username)){
                    return;
                }
            }
            Attendance attendance = new Attendance();
            attendance.setStudent(studentList.get(0));
            attendance.setLect(lecture);
            session.save(attendance);
        }
    }
}
