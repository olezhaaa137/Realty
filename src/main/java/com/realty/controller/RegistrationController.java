package com.realty.controller;

import com.realty.service.EmailSender;
import com.realty.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.realty.model.User;
import com.realty.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public User user() {
		return new User();
	}

	@GetMapping
	public String getRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registration(Model model, User user) {
		if(!userService.checkExistEmail(user.getEmail())) {
			model.addAttribute("err", "Пользователь с данным email уже существует!");
			return "registration";
		}
		if(!userService.checkExistUsername(user.getUsername())) {
			model.addAttribute("err", "Логин уже существует!");
			return "registration";
		}
		userService.addClient(user);
		EmailSender.getInstance().sendEmail(user.getEmail(), "Добро пожаловать!",
				"Уважаемый " + user.getName() + " " + user.getLastname() +
					", Мы рады вас приветствовать!!");
		return "redirect:/login";
	}
}
