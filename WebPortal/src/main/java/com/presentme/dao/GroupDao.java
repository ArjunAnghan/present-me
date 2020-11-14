package com.presentme.dao;

import com.presentme.entity.GroupsEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GroupDao {

    private SessionFactory sessionFactory;

    @Autowired
    public GroupDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<GroupsEntity> getGroupsAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From GroupsEntity ", GroupsEntity.class).getResultList();
    }

    @Transactional
    public GroupsEntity getGroup(Integer groupId) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("From GroupsEntity g where g.id = :gid", GroupsEntity.class).setParameter("gid", groupId).getResultList().get(0);
    }

    @Transactional
    public void deleteGroup(Integer groupId) {
        Session session = sessionFactory.getCurrentSession();

        GroupsEntity groupsEntity = session.get(GroupsEntity.class, groupId);
        Hibernate.initialize(groupsEntity.getStudentList());
        session.delete(groupsEntity);
    }

    @Transactional
    public void addGroup(String group) {
        Session session = sessionFactory.getCurrentSession();
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setGroupname(group);
        session.save(groupsEntity);
    }
}
