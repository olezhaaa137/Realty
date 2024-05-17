package com.realty.controller;

import com.realty.model.Comment;
import com.realty.model.User;
import com.realty.repo.UserRepository;
import com.realty.service.EmailSender;
import com.realty.service.RandomCodeGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
public class ForgetPasswordController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/forgetPassword")
    public String showPage(@RequestParam String username, Model model, HttpSession session) {

        try{
            User user = userRepo.findByUsername(username).get();
            if (user != null){
                model.addAttribute("email",user.getEmail());
                session.setAttribute("user", user);
                String code = RandomCodeGenerator.generateRandomCode();
                session.setAttribute("code", code);
                EmailSender.getInstance().sendEmail(user.getEmail(), "Код для восстановления пароля", code);
                return "forgetPassword";
            }
        }catch(NoSuchElementException exception){
            return "forgetPasswordError";
        }
        return  "forgetPasswordError";
    }

    @PostMapping("/checkCode")
    public String checkCode(@RequestParam String code, HttpSession session, Model model) {
        if (code.equals(session.getAttribute("code"))) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                model.addAttribute("user", user);
                return "setNewPassword";
            }
        }
        return "invalidCode";
    }

    @PostMapping("/updatePassword")
    public String checkCode(User user, HttpSession session, Model model) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user = (User)session.getAttribute("user");
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "login";
    }
}
