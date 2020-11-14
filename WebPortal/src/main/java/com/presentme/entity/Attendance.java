package com.presentme.entity;

import javax.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {
    private int id;
    private Lecture lect;
    private Student student;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "lect")
    public Lecture getLect() {
        return lect;
    }

    public void setLect(Lecture lect) {
        this.lect = lect;
    }

    @ManyToOne
    @JoinColumn(name = "student")
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attendance that = (Attendance) o;

        if (id != that.id) return false;
        if (lect != that.lect) return false;
        if (student != that.student) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + lect.getId();
        result = 31 * result + student.getId();
        return result;
    }
}
