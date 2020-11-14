package com.presentme.controller;

import com.presentme.dao.LectureDao;
import com.presentme.entity.Faculty;
import com.presentme.entity.Lecture;
import com.presentme.entity.User;
import com.presentme.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    private FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping("/index")
    public String getIndexPage(HttpSession session, Model model) {
        User u = (User) session.getAttribute("user");
        Faculty faculty = facultyService.getFaculty(u.getUsername());
        if (faculty == null || faculty.getLeave() == 1) {
            model.addAttribute("message","No Account");
            return "login";
        }
        session.setAttribute("user", faculty);
        model.addAttribute("theFaculty", faculty);
        return "faculty/lectures";
    }

    @RequestMapping(value = "/attendence")
    public String getLectureAttendence(Model model, @RequestParam("lecture") Integer lectureId){
        Lecture lecture = facultyService.getLecture(lectureId);
        model.addAttribute("theLecture", lecture);
        return "faculty/lecture";
    }

    @RequestMapping(value = "/addStudentAttendence", method = RequestMethod.POST)
    public String addStudentAttendence(Model model,@RequestParam("username") String username, @RequestParam("lectureID") Integer lectId){
        facultyService.addStudent(username, lectId);
        Lecture lecture = facultyService.getLecture(lectId);
        model.addAttribute("theLecture", lecture);
        return "faculty/lecture";
    }

}
