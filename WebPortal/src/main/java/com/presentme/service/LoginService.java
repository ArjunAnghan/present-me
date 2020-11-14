package com.presentme.service;

import com.presentme.dao.LoginDao;
import com.presentme.entity.User;
import com.presentme.utility.BCrypt;
import com.presentme.utility.LinkGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private LoginDao loginDao;

    @Autowired
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }


    public User getUser(String username, String password) {
        return loginDao.getUser(username, password);
    }

    public void updatePassword(User u, String pass) {
        String salt = BCrypt.gensalt();
        loginDao.changePassword(u.getId(), BCrypt.hashpw(pass, salt), salt);
    }

    public String activateAccount(String user, String encUser, String encEmail, String userSalt, String emailSalt) {
        if (user == null || user.trim().equals("") || encUser == null || encUser.trim().equals("") || encEmail == null || encEmail.trim().equals("") || userSalt == null || userSalt.trim().equals("") || emailSalt == null || emailSalt.trim().equals("")) {
            return "Invalid Link";
        }

        User u = loginDao.getUser(user);
        if (u != null) {

            List<String> ans = LinkGen.encUserandEmail(u.getUsername(), u.getEmail(), userSalt, emailSalt);

            if (u.getActivated() == 0) {
                if (ans.get(0).equals(encUser) && ans.get(1).equals(encEmail)) {
                    return null;
                } else {
                    return "Invalid Link";
                }
            } else {
                return "Already Activated";
            }
        }
        return "Invalid Link";
    }

    public User getUserResetPassword(String username) {
        return loginDao.getUserResetPassword(username);
    }

    public void addPassword(String user, String pass) {
        String Salt = BCrypt.gensalt();
        loginDao.activateAccount(user, BCrypt.hashpw(pass, Salt), Salt);
    }
}
