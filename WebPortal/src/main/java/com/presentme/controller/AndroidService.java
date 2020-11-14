package com.presentme.controller;

import com.presentme.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class AndroidService {

    private RestService restService;

    @Autowired
    public AndroidService(RestService restService) {
        this.restService = restService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getString")
    public String login(@RequestParam(value = "b71cO2") String roll, @RequestParam(value = "Oaos3L") String pass, @RequestParam(value = "kUQNGF") String imei) {
        String status = restService.getStudentStatus(roll.toUpperCase(), pass, imei);
        if (status.equals("7")){
            status = status + "," + restService.getStudentGroup(roll.toUpperCase());
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/loginFaculty")
    public String login(@RequestParam(value = "iTYz2f") String fcode, @RequestParam(value = "KoTetn") String pass) {
        return restService.getTeacherLogin(fcode.toUpperCase(), pass);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/generateLecture")
    public String generateLecture(@RequestParam("iTYz2f") String tuser, @RequestParam("TDLl0w") String slot, @RequestParam("cXYZ06") String subject, @RequestParam("groups") String groups) {
        Integer slotId = restService.generateLecture(tuser.toUpperCase(), slot, subject.toUpperCase(), groups.toUpperCase());

        String slots = String.valueOf(slotId);
        for (int i = 0; i < 4; i++) {
            slots = Base64.getMimeEncoder().encodeToString(slots.getBytes());
        }

        return slots;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/commit")
    public String commitLecture(@RequestParam("LOTS2f") String lectureID) {

        for (int i = 0; i < 4; i++) {
            lectureID = new String(Base64.getMimeDecoder().decode(lectureID.getBytes()) , StandardCharsets.UTF_8);
        }

        restService.commitLecture(Integer.valueOf(lectureID));
        return "1";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/present")
    public String doPresent(@RequestParam("ILIm50") String lectureId, @RequestParam("b71cO2") String student, @RequestParam("group") String group) {

        return restService.presentme(lectureId, student.toUpperCase(), group);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkBlock")
    public String doPresent(@RequestParam("b71cO2") String roll) {

        return restService.checkBlock(roll.toUpperCase());
    }

}
