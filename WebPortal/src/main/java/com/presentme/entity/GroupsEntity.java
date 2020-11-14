package com.presentme.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groupsentity")
public class GroupsEntity {
    private int id;
    private String groupname;
    private List<Student> studentList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "groupname")
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @OneToMany(mappedBy = "groupsEntity", fetch = FetchType.LAZY)
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupsEntity that = (GroupsEntity) o;

        if (id != that.id) return false;
        if (groupname != null ? !groupname.equals(that.groupname) : that.groupname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupname != null ? groupname.hashCode() : 0);
        return result;
    }
}
