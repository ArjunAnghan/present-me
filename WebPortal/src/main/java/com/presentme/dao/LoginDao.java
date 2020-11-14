package com.presentme.dao;

import com.presentme.entity.User;
import com.presentme.utility.BCrypt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LoginDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public LoginDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public User getUser(String username, String password) {
        Session session = sessionFactory.getCurrentSession();

        List<User> userList = session.createQuery("From User u where u.username = :username", User.class).setParameter("username", username).getResultList();
        if (userList.size() > 0){
            User user = userList.get(0);
            String enteredHashPass = BCrypt.hashpw(password, user.getSalt());

            if (enteredHashPass.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    @Transactional
    public User getUser(String username) {
        Session session = sessionFactory.getCurrentSession();

        List<User> users = session.createQuery("From User u where u.username = :user", User.class).setParameter("user", username).getResultList();

        if (users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    @Transactional
    public void changePassword(int id, String hashedPass, String salt) {
        Session session = sessionFactory.getCurrentSession();
        User u = session.get(User.class,id);
        u.setPassword(hashedPass);
        u.setSalt(salt);
        session.save(u);
    }

    @Transactional
    public User getUserResetPassword(String username) {
        Session session = sessionFactory.getCurrentSession();

        List<User> users = session.createQuery("From User u where u.username = :user and u.level != 2", User.class).setParameter("user", username).getResultList();

        if (users.size() > 0){
            return users.get(0);
        }
        return null;

    }

    @Transactional
    public void activateAccount(String user, String pass, String salt) {
        Session session = sessionFactory.getCurrentSession();

        List<User> users = session.createQuery("From User u where u.username = :user", User.class).setParameter("user", user).getResultList();

        if (users.size() > 0){
            User u =  users.get(0);
            u.setPassword(pass);
            u.setSalt(salt);
            u.setActivated(1);
            session.save(u);
        }
    }
}
