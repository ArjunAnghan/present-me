package com.presentme.controller;

import com.presentme.entity.User;
import com.presentme.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/")
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        User user = loginService.getUser(username, password.trim());
        if (user != null) {
            if (user.getActivated() == 0) {
                model.addAttribute("message", "Account Activation");
                return "login";
            }
            if (user.getLevel() == 0) {
                session.setAttribute("user", user);
                return "redirect:admin/index";
            } else if (user.getLevel() == 1) {
                session.setAttribute("user", user);
                return "redirect:faculty/index";
            }
        }

        model.addAttribute("message", "Invalid Password");
        return "login";
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activateAccount(HttpSession session, Model model, @RequestParam("u") String user, @RequestParam("i") String encUser, @RequestParam("k") String encEmail, @RequestParam("m") String userSalt, @RequestParam("n") String emailSalt) {
        String answer = loginService.activateAccount(user, encUser, encEmail, userSalt, emailSalt);
        if (answer != null) {
            model.addAttribute("message", answer);
            return "login";
        }
        session.setAttribute("addPassUser", user);
        return "password/addPassword";
    }

    @RequestMapping(value = "/addPassword", method = RequestMethod.POST)
    public String changePassword(Model model, @RequestParam("pass") String pass, @RequestParam("cpass") String cpass, HttpSession session) {
        String user = (String) session.getAttribute("addPassUser");
        if (pass == null || cpass == null || pass.trim().equals("") || cpass.trim().equals("")) {
            model.addAttribute("message", "Passwords cannot be only white spaces");
            return "password/addPassword";
        }
        pass = pass.trim();
        cpass = cpass.trim();
        if (user != null) {
            if (pass.equals(cpass)) {
                loginService.addPassword(user, pass);
                model.addAttribute("message", "Activation Done");
                return "login";
            } else {
                model.addAttribute("message", "Passwords do not match");
                return "password/resetPassword";
            }
        } else {
            model.addAttribute("message", "Invalid Login Method");
            return "login";
        }
    }

    @RequestMapping(value = "/forgot")
    public String forgotPasswordPage(Model model, @ModelAttribute("message") String message, HttpSession session) {
        model.addAttribute("message", message);
        return "password/forgot";
    }

    @RequestMapping(value = "/fpUser", method = RequestMethod.POST)
    public String getUser(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("username") String username) {
        User user = loginService.getUserResetPassword(username);
        if (user == null) {
            redirectAttributes.addAttribute("message", "Username Does not Exists");
            return "redirect:forgot";
        }
        session.setAttribute("userForgot", user);
        return "password/sendOtpPage";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }
}
