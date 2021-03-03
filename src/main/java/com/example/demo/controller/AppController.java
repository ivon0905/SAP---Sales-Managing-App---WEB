package com.example.demo.controller;

import com.example.demo.customUserDetails.CustomUserDetails;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import com.example.demo.service.ProductService;
import com.example.demo.service.RequestsService;
import com.example.demo.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AppController implements WebMvcConfigurer {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RequestRepository requestRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SalesRepresentativeRepository salesRepresentativeRepo;

    @Autowired
    private SoldProductRepository salesRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ProductService service;

    @Autowired
    private RequestsService requestService;

    @Autowired
    private SalesService salesService;

    @GetMapping("/index")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String viewSignUpPage(Model model){
        model.addAttribute("user",new UserRequest());

        return "sign_up_form";
    }

    @PostMapping("/process_register")
    public String processRegister(UserRequest user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        requestRepo.save(user);

        return "index.html";
    }

   @RequestMapping("/default")
    public String afterLogin(@AuthenticationPrincipal CustomUserDetails c){
        String username = c.getUsername();
        User user = userRepo.findByUsername(username);
        String appRole = user.getAppRole();
        if(appRole.equals("admin"))
            return "admin";
        return "manager";
    }
}
