package com.presentme.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculty")
@PrimaryKeyJoinColumn(name = "id")
public class Faculty extends User {
    private String name;
    private List<Lecture> lectureList;
    private int leave;

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "clgleft")
    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;
        if (name != null ? !name.equals(faculty.name) : faculty.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result=0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
