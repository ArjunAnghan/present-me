package com.presentme.service;

import com.presentme.dao.*;
import com.presentme.entity.*;
import com.presentme.utility.LinkGen;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private AdminDao adminDao;
    private FacultyDao facultyDao;
    private LectureDao lectureDao;
    private StudentDao studentDao;
    private GroupDao groupDao;

    @Autowired
    public AdminService(AdminDao adminDao, FacultyDao facultyDao, LectureDao lectureDao, StudentDao studentDao, GroupDao groupDao) {
        this.adminDao = adminDao;
        this.facultyDao = facultyDao;
        this.lectureDao = lectureDao;
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    public Admin getAdmin(String username){
        return adminDao.getAdmin(username);
    }

    public List<Faculty> getAllFaculties() {
        return facultyDao.getFacultiesAll();
    }

    public Faculty getFacultyLectures(String facultyUsername) {
        return facultyDao.getFacultywithLectures(facultyUsername);
    }

    public Lecture getLecture(Integer lectureId) {
        return lectureDao.getAttendence(lectureId);
    }

    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public void clearImei(String suser) {
        studentDao.clearImei(suser);
    }

    public void clearPassword(String suser) {
        Student student = studentDao.clearPassword(suser);
        LinkGen.sendActivationLink(student.getName(), student.getUsername(), student.getEmail());
    }

    public void activateFaculty(String faculty) {
        facultyDao.updateFacultyActive(faculty,1);
    }

    public void deactivateFaculty(String faculty) {
        facultyDao.updateFacultyActive(faculty,0);
    }

    public String registerAdmin(String user, String email, String name) {
        Admin a = new Admin();
        a.setName(name);
        a.setLevel(0);
        a.setUsername(user);
        a.setEmail(email);
        a.setActivated(0);

        String statusAq = adminDao.addAdmin(a);
        if (statusAq.equals("adda")) {
            LinkGen.sendActivationLink(a.getName(), a.getUsername(), a.getEmail());
        }
        return statusAq;
    }

    public String registerFaculty(String user, String email, String name) {
        Faculty g = new Faculty();
        g.setName(name);
        g.setLevel(1);
        g.setUsername(user);
        g.setEmail(email);
        g.setActivated(0);
        g.setLeave(0);

        String statusAq = facultyDao.addFaculty(g);
        if (statusAq.equals("addg")) {
            LinkGen.sendActivationLink(g.getName(), g.getUsername(), g.getEmail());
        }
        return statusAq;
    }

    public String registerFaculty(CommonsMultipartFile facultydata) {
        if (!FilenameUtils.getExtension(facultydata.getOriginalFilename()).equalsIgnoreCase("csv")) {
            return "Invalid Extension";
        }
        try {
            CSVParser parser = new CSVParser(new InputStreamReader(facultydata.getInputStream()), CSVFormat.DEFAULT);

            Set<String> facultyUserSet = new HashSet<>(facultyDao.getAllFacultyUsernames());

            Faculty g = new Faculty();
            g.setLevel(1);
            g.setActivated(0);
            g.setLeave(0);

            for (CSVRecord record : parser) {
                if (facultyUserSet.contains(record.get(0))) {
                    continue;
                }
                if (record.size() != 3) {
                    return "Invalid Contents";
                }
                g.setName(record.get(2));
                g.setUsername(record.get(0).toUpperCase());
                g.setEmail(record.get(1).toLowerCase());
                facultyDao.addFacultyDirect(g);
                LinkGen.sendActivationLink(g.getName(), g.getUsername(), g.getEmail());
                facultyUserSet.add(g.getUsername());
            }
            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmine();
    }

    public String registerStudent(String username, String email, String name, Integer groupId) {
        GroupsEntity groups = groupDao.getGroup(groupId);

        Student s = new Student();
        s.setName(name);
        s.setEmail(email);
        s.setGroupsEntity(groups);
        s.setUsername(username);
        s.setActivated(0);
        s.setLevel(2);
        s.setBlock(0);
        String statusAq = studentDao.addStudent(s);
        if (statusAq.equals("adds")) {
            LinkGen.sendActivationLink(s.getName(), s.getUsername(), s.getEmail());
        }
        return statusAq;
    }

    public String registerStudent(CommonsMultipartFile studentData) {
        if (!FilenameUtils.getExtension(studentData.getOriginalFilename()).equalsIgnoreCase("csv")) {
            return "Invalid Extension";
        }
        try {
            CSVParser parser = new CSVParser(new InputStreamReader(studentData.getInputStream()), CSVFormat.DEFAULT);

            Set<String> studentUserSet = new HashSet<>(studentDao.getAllStudentsUsernames());

            Student s = new Student();
            s.setActivated(0);
            s.setLevel(2);
            s.setBlock(0);
            List<GroupsEntity> groupsList = groupDao.getGroupsAll();

            for (CSVRecord record : parser) {
                if (studentUserSet.contains(record.get(0).toUpperCase())) {
                    continue;
                }
                if (record.size() != 3) {
                    return "Invalid Contents";
                }
                s.setName(record.get(2));
                s.setEmail(record.get(1).toLowerCase());
                s.setUsername(record.get(0).toUpperCase());
                s.setGroupsEntity(groupsList.stream().filter(groups -> groups.getGroupname().equalsIgnoreCase(record.get(3))).findFirst().get());
                studentDao.addStudentDirect(s);
                LinkGen.sendActivationLink(s.getName(), s.getUsername(), s.getEmail());
                studentUserSet.add(s.getUsername());
            }
            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GroupsEntity> getGroupsList() {
        return groupDao.getGroupsAll();
    }

    public void deleteGroup(Integer groupId) {
        groupDao.deleteGroup(groupId);
    }

    public void addGroups(String groupName) {
        String[] groups = groupName.trim().split(",");
        List<String> groupsEntities = groupDao.getGroupsAll().stream().map(GroupsEntity::getGroupname).collect(Collectors.toList());
        for (String group:groups) {
            group = group.trim().toUpperCase();
            if(!groupsEntities.contains(group)){
                groupDao.addGroup(group);
                groupsEntities.add(group);
            }
        }
    }
}
