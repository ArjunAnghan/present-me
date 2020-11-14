package com.presentme.dao;

import com.presentme.entity.GroupsEntity;
import com.presentme.entity.Student;
import com.presentme.entity.User;
import com.presentme.utility.BCrypt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentDao {

    private SessionFactory sessionFactory;

    @Autowired
    public StudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Student> getAllStudents() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From Student", Student.class).getResultList();
    }

    @Transactional
    public void clearImei(String suser) {
        Session session = sessionFactory.getCurrentSession();

        Student student = session.createQuery("From Student s where s.username = :user", Student.class)
                .setParameter("user", suser)
                .getSingleResult();
        student.setImei(null);
        student.setBlock(0);
        session.save(student);
    }

    @Transactional
    public Student clearPassword(String suser) {
        Session session = sessionFactory.getCurrentSession();

        Student student = session.createQuery("From Student s where s.username = :user", Student.class)
                .setParameter("user", suser)
                .getSingleResult();
        student.setPassword(null);
        student.setSalt(null);
        student.setActivated(0);
        session.save(student);
        return student;
    }

    @Transactional
    public String addStudent(Student s) {
        Session session = sessionFactory.getCurrentSession();
        List<Student> student = session.createQuery("From Student s where s.username = :roll", Student.class)
                .setParameter("roll", s.getUsername())
                .getResultList();
        if (student.size() > 0) {
            return "clashs";
        }

        session.save(s);
        return "adds";
    }

    @Transactional
    public List<String> getAllStudentsUsernames() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Student", Student.class).getResultList().stream().map(User::getUsername).collect(Collectors.toList());
    }

    @Transactional
    public void addStudentDirect(Student s) {
        Session session = sessionFactory.getCurrentSession();
        session.save(s);
    }

    @Transactional
    public String getStudent(String user, String pass, String imei) {
        Session session = sessionFactory.getCurrentSession();

        List<Student> studentList = session.createQuery("From Student s where s.username = :user", Student.class)
                .setParameter("user", user)
                .getResultList();

        try {
            if (studentList.size() > 0) {
                Student s = studentList.get(0);
                if (BCrypt.hashpw(pass, s.getSalt()).equals(s.getPassword())) {
                    if (s.getImei() == null) {
                        List<Student> studentListImei = session.createQuery("From Student s where s.imei = :imei", Student.class)
                                .setParameter("imei", imei)
                                .getResultList();

                        if (studentListImei.size() > 0) {
                            for (Student student : studentListImei) {
                                student.setBlock(1);
                                session.save(student);
                            }
                            s.setImei(imei);
                            s.setBlock(1);
                            session.save(s);
                            return "18";
                        } else {
                            s.setImei(imei);
                            session.save(s);
                            return "7";
                        }
                    } else if (s.getImei().equals(imei)) {
                        if (s.getBlock() == 1) {
                            return "18";
                        }
                        return "7";
                    } else if (!s.getImei().equals(imei)) {
                        List<Student> studentListImei = session.createQuery("From Student s where s.imei = :imei", Student.class)
                                .setParameter("imei", imei)
                                .getResultList();

                        for (Student student : studentListImei) {
                            student.setBlock(1);
                            session.save(student);
                        }
                        s.setImei(imei);
                        s.setBlock(1);
                        session.save(s);
                        return "18";
                    }
                } else {
                    return "1";
                }

            } else {
                return "1";
            }
            return "1";
        } catch(Exception e){
            return "-1";
        }
    }

    @Transactional
    public String getStudentGroup(String roll) {
        Session session = sessionFactory.getCurrentSession();
        Student student = session.createQuery("From Student s where s.username = :user", Student.class).setParameter("user", roll).getResultList().get(0);
        return student.getGroupsEntity().getGroupname();
    }

    @Transactional
    public Student getStudent(String roll) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Student s where s.username = :user", Student.class).setParameter("user", roll).getResultList().get(0);
    }

    @Transactional
    public String getAllGroupCommas() {
        Session session = sessionFactory.getCurrentSession();
        List<GroupsEntity> groups = session.createQuery("from GroupsEntity", GroupsEntity.class).getResultList();
        StringBuilder sb = new StringBuilder("");
        for (GroupsEntity g : groups){
            sb.append(g.getGroupname()).append(",");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}



//            if (s.getImei() == null){
//                s.setImei(imei);
//                session.save(s);
//                return "7";
//            }
//
//            if (s.getBlock() == 1 || !s.getImei().equals(imei)) {
//
//                if (!s.getImei().equals(imei)) {
//                    List<Student> studentListImei = session.createQuery("From Student s where s.imei = :imei", Student.class)
//                            .setParameter("imei", imei)
//                            .getResultList();
//
//                    for (Student student : studentListImei) {
//                        student.setBlock(1);
//                        session.save(student);
//                    }
//                    s.setBlock(1);
//                    session.save(s);
//                }
//
//                return "18";
//            }
