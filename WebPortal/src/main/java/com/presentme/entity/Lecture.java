package com.presentme.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "lecture")
public class Lecture {
    private int id;
    private String slotno;
    //1-ongoing 0-not ongoing
    private int flag;
    private List<Attendance> attendances;
    private Timestamp time;
    private Faculty faculty;
    private String subject;
    private String groupEntity;
    


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
    @Column(name = "slotno")
    public String getSlotno() {
        return slotno;
    }

    public void setSlotno(String slotno) {
        this.slotno = slotno;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp date) {
        this.time = date;
    }

    @Basic
    @Column(name = "flag")
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Basic
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @OneToMany(mappedBy = "lect", fetch = FetchType.LAZY)
    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    @ManyToOne
    @JoinColumn(name = "teacher")
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }


    @Basic
    @Column(name = "groupEntity")
    public String getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(String groupEntity) {
        this.groupEntity = groupEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lecture lecture = (Lecture) o;

        if (id != lecture.id) return false;
        if (!slotno.equals(lecture.slotno)) return false;
        if (flag != lecture.flag) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + flag;
        return result;
    }
}
