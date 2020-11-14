package com.presentme.controller;

import com.presentme.entity.*;
import com.presentme.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("/index")
    public String getIndexPage(HttpSession session, Model model){
        User u = (User)session.getAttribute("user");
        Admin admin = adminService.getAdmin(u.getUsername());
        session.setAttribute("user", admin);
        return "redirect:faculties";
    }

    @RequestMapping("/faculties")
    public String getFaculties(Model model, @ModelAttribute("status") String message){
        List<Faculty> facultyList = adminService.getAllFaculties();
        model.addAttribute("allFaculties", facultyList);
        model.addAttribute("message",message);
        return "admin/faculties";
    }

    @RequestMapping(value = "/faculty")
    public String getFacultyLectures(Model model, @RequestParam("faculty") String facultyUsername){
        Faculty theFaculty = adminService.getFacultyLectures(facultyUsername);
        model.addAttribute("theFaculty", theFaculty);
        return "admin/faculty";

    }

    @RequestMapping(value = "/attendence")
    public String getLectureAttendence(Model model, @RequestParam("lecture") Integer lectureId){
        Lecture lecture = adminService.getLecture(lectureId);
        model.addAttribute("theLecture", lecture);
        return "admin/lecture";
    }

    @RequestMapping("/students")
    public String getStudents(Model model){
        List<Student> studentList = adminService.getAllStudents();
        model.addAttribute("allStudents", studentList);
        return "admin/students";
    }

    @RequestMapping("/clearImei")
    public String clearImei(@RequestParam("student") String suser){
        adminService.clearImei(suser);
        return "redirect:students";
    }

    @RequestMapping("/clearpassword")
    public String clearPassword(@RequestParam("student") String suser){
        adminService.clearPassword(suser);
        return "redirect:students";
    }

    @RequestMapping("/activate")
    public String activateTeacher(@RequestParam("faculty") String facultyUser){
        adminService.activateFaculty(facultyUser);
        return "redirect:faculties";
    }

    @RequestMapping("/deactivate")
    public String deactivateTeacher(@RequestParam("faculty") String facultyUser){
        adminService.deactivateFaculty(facultyUser);
        return "redirect:faculties";
    }

    @RequestMapping("/addAdminPage")
    public String addApage() {
        return "admin/addAdmin";
    }

    @RequestMapping("/addFacultyPage")
    public String addfpage() {
        return "admin/addfaculty";
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public String addAdmin(RedirectAttributes redirectAttributes, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("name") String name) {
        String status = adminService.registerAdmin(username.toUpperCase().trim(), email.toLowerCase().trim(), name);
        redirectAttributes.addAttribute("message", status);
        return "redirect:admins";
    }

    @RequestMapping(value = "/addFaculty", method = RequestMethod.POST)
    public String addGuide(RedirectAttributes redirectAttributes,@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("name") String name) {
        String status = adminService.registerFaculty(username.toUpperCase().trim(), email.toLowerCase().trim(), name);
        redirectAttributes.addAttribute("message",status);
        return "redirect:faculties";
    }

    @RequestMapping(value = "/addFacultyCsv", method = RequestMethod.POST)
    public String addGuideCsv(Model model, RedirectAttributes redirectAttributes, @RequestParam("facultycsv") CommonsMultipartFile facultydata) {
        String status = adminService.registerFaculty(facultydata);
        if (status == null) {
            redirectAttributes.addAttribute("message", "addg");
            return "redirect:faculties";
        } else {
            model.addAttribute("message", status);
            return "admin/addfaculty";
        }
    }

    @RequestMapping("/admins")
    public String getAdmins(Model model, @ModelAttribute("status") String message){
        List<Admin> adminList = adminService.getAllAdmins();
        model.addAttribute("allAdmins", adminList);
        model.addAttribute("message",message);
        return "admin/admins";
    }

    @RequestMapping("/addStudentPage")
    public String addGpage(Model model) {
        List<GroupsEntity> groupsList = adminService.getGroupsList();
        model.addAttribute("groupList", groupsList);
        return "admin/addStudent";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(RedirectAttributes redirectAttributes, @RequestParam("roll") String username, @RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("groupId") Integer groupId) {
        String status = adminService.registerStudent(username.toUpperCase().trim(), email.toLowerCase().trim(), name, groupId);
        redirectAttributes.addAttribute("message", status);
        return "redirect:students";
    }

    @RequestMapping(value = "/addStudentCsv", method = RequestMethod.POST)
    public String addStudentCsv(Model model, RedirectAttributes redirectAttributes, @RequestParam("studentscsv") CommonsMultipartFile studentData) {
        String status = adminService.registerStudent(studentData);
        if (status == null) {
            redirectAttributes.addAttribute("message", "adds");
            return "redirect:students";
        } else {
            model.addAttribute("message", status);
            return "admin/addStudent";
        }
    }

    @RequestMapping("/groups")
    public String getGroups(Model model){
        List<GroupsEntity> groupsList = adminService.getGroupsList();
        model.addAttribute("allGroups", groupsList);
        return "admin/groups";
    }

    @RequestMapping("/addGroupPage")
    public String addGrppage(Model model) {

        return "admin/addGroups";
    }

    @RequestMapping(value = "/delGroup", method = RequestMethod.GET)
    public String deleteGroup(Model model, @RequestParam("groupId") Integer groupId){
        adminService.deleteGroup(groupId);
        List<GroupsEntity> groupsList = adminService.getGroupsList();
        model.addAttribute("allGroups", groupsList);
        return "admin/groups";
    }

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public String addGroups(Model model, @RequestParam("groupname") String groupName){
        adminService.addGroups(groupName);
        List<GroupsEntity> groupsList = adminService.getGroupsList();
        model.addAttribute("allGroups", groupsList);
        return "admin/groups";
    }
}
