package com.presentme.dao;

import com.presentme.entity.Faculty;
import com.presentme.entity.Lecture;
import com.presentme.entity.User;
import com.presentme.utility.BCrypt;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.enterprise.inject.TransientReference;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FacultyDao {

    private SessionFactory sessionFactory;

    @Autowired
    public FacultyDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Faculty> getFacultiesAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From Faculty", Faculty.class).getResultList();
    }

    @Transactional
    public Faculty getFacultywithLectures(String facultyUsername) {
        Session session = sessionFactory.getCurrentSession();

        Faculty f = session.createQuery("From Faculty f where f.username = :username", Faculty.class)
                .setParameter("username", facultyUsername)
                .getSingleResult();

        Hibernate.initialize(f.getLectureList());
        return f;
    }

    @Transactional
    public void updateFacultyActive(String faculty,int i) {
        Session session = sessionFactory.getCurrentSession();

        Faculty f = session.createQuery("From Faculty f where f.username = :username", Faculty.class)
                .setParameter("username", faculty)
                .getSingleResult();
        f.setLeave(i);
        session.save(f);
    }

    @Transactional
    public String addFaculty(Faculty g) {
        Session session = sessionFactory.getCurrentSession();
        List<Faculty> faculties = session.createQuery("From Faculty f where f.username = :faculty", Faculty.class)
                .setParameter("faculty", g.getUsername())
                .getResultList();
        if (faculties.size() > 0) {
            return "clashg";
        }

        session.save(g);
        return "addg";
    }

    @Transactional
    public List<String> getAllFacultyUsernames() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Faculty ", Faculty.class).getResultList().stream().map(User::getUsername).collect(Collectors.toList());
    }

    @Transactional
    public void addFacultyDirect(Faculty g) {
        Session session = sessionFactory.getCurrentSession();
        session.save(g);
    }

    @Transactional
    public String getFacultyLoginStatus(String username, String pass){
        Session session = sessionFactory.getCurrentSession();

        List<Faculty> faculties = session.createQuery("From Faculty f where f.username = :user", Faculty.class)
        .setParameter("user", username)
        .getResultList();

        if (faculties.size() > 0){
            Faculty f1 = faculties.get(0);
            if (f1.getActivated() == 0 || f1.getLeave() == 1){
                return "2";
            }

            if (BCrypt.hashpw(pass, f1.getSalt()).equals(f1.getPassword())){
                return "1";
            }
            return "0";
        }
        return "0";
    }

    @Transactional
    public Integer generateLecture(String tuser, String slot, String subject, String groups){
        Session session = sessionFactory.getCurrentSession();

        Faculty faculty = session.createQuery("From Faculty f where f.username = :user", Faculty.class)
                .setParameter("user", tuser)
                .getSingleResult();

        Lecture lecture = new Lecture();
        lecture.setSlotno(slot);
        lecture.setSubject(subject);
        lecture.setFaculty(faculty);
        lecture.setTime(Timestamp.valueOf(LocalDateTime.now()));
        lecture.setGroupEntity(groups);
        lecture.setFlag(1);

        session.save(lecture);

        return lecture.getId();
    }

    @Transactional
    public Integer getTidFromTcode(String tcode) {
        Session session = sessionFactory.getCurrentSession();

        List<Faculty> facultyList = session.createQuery("From Faculty f where f.username = :user", Faculty.class)
                .setParameter("user", tcode)
                .getResultList();

        if (facultyList == null || facultyList.size() == 0){
            return -1;
        }
        return facultyList.get(0).getId();
    }
}
