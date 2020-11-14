package com.presentme.controller;

import com.presentme.entity.User;
import com.presentme.service.LoginService;
import com.presentme.utility.OtpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("password")
public class PasswordController {

    private LoginService loginService;

    @Autowired
    public PasswordController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/sendOtp")
    public String sendOtp(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userForgot");

        if (user == null) {
            model.addAttribute("message", "Invalid Login Method");
            return "login";
        }

        String hashedOtp = OtpSender.sendOtpEmail(user.getUsername(), user.getEmail());
        session.setAttribute("hashedOtp", hashedOtp.getBytes());
        return "password/enterOtp";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String validateOtp(Model model, @RequestParam("otp") String otp, HttpSession session) {
        byte[] orgOtp = (byte[]) session.getAttribute("hashedOtp");

        int status = OtpSender.validate(otp, orgOtp);
        if (status == 1) {
            return "password/resetPassword";
        }
        model.addAttribute("message", "Invalid OTP");
        return "login";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(Model model, @RequestParam("pass") String pass, @RequestParam("cpass") String cpass, HttpSession session) {
        if (pass == null || pass.trim().equals("")) {
            model.addAttribute("message", "Passwords cannot be only white spaces");
            return "password/resetPassword";
        }

        pass = pass.trim();
        cpass = cpass.trim();

        if (!pass.equals(cpass)) {
            model.addAttribute("message", "Passwords do not match");
            return "password/resetPassword";
        }

        User u = (User) session.getAttribute("userForgot");
        loginService.updatePassword(u, pass);
        model.addAttribute("message", "Password Reset");
        return "login";
    }

}
