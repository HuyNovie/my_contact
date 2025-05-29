package com.example.mycontact.controller;

import com.example.mycontact.dto.RegisterDTO;
import com.example.mycontact.entities.Contact;
import com.example.mycontact.service.ContactService;
import com.example.mycontact.service.UserService;
import com.example.mycontact.utils.WebUtils;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Authentication authentication) {
        User springUser = (User) authentication.getPrincipal();

        String username = springUser.getUsername();
        String userInfo = WebUtils.toString(springUser);

        List<Contact> contactList = (List<Contact>) contactService.findAll();

        model.addAttribute("username", username);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("contact", contactList);

        return "contactPage";
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Authentication authentication) {
        User springUser = (User) authentication.getPrincipal();

        String username = springUser.getUsername();
        String userInfo = WebUtils.toString(springUser);

        List<Contact> contactList = (List<Contact>) contactService.findAll();

        model.addAttribute("username", username);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("contact", contactList);

        return "contactPage";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "registerPage"; // Thymeleaf template
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDTO registerDTO) {
        userService.save(registerDTO);
        return "redirect:/registration?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @GetMapping("/403")
    public String accessDenied(Model model, Authentication authentication) {
        if (authentication != null) {
            User springUser = (User) authentication.getPrincipal();
            String username = springUser.getUsername();
            String userInfo = WebUtils.toString(springUser);

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("message", "Hi " + username + "<br> You do not have permission to access this page!");
        }
        return "403Page";
    }
}
