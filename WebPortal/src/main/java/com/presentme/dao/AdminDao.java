package com.presentme.dao;

import com.presentme.entity.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AdminDao {

    private SessionFactory sessionFactory;

    @Autowired
    public AdminDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Admin getAdmin(String username) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from Admin a where a.username = :user", Admin.class)
                .setParameter("user", username)
                .getSingleResult();
    }

    @Transactional
    public String addAdmin(Admin a) {
        Session session = sessionFactory.getCurrentSession();
        List<Admin> admins = session.createQuery("From Admin a where a.username = :admin", Admin.class)
                .setParameter("admin", a.getUsername())
                .getResultList();
        if (admins.size() > 0) {
            return "clasha";
        }

        session.save(a);
        return "adda";
    }

    @Transactional
    public List<Admin> getAllAdmine() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from Admin ", Admin.class).getResultList();
    }
}
