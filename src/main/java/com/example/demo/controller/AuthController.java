package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;


@Controller
public class AuthController {

    private UserService userService;
    private BookService bookService;

    public AuthController(UserService userService, BookService bookService) {
		super();
		this.userService = userService;
		this.bookService = bookService;
	}
	@ModelAttribute("currentUser")
    public User currentUser(Authentication auth) {
    	if (auth == null) {
    		return new User();
    	}
    	User currentUser = userService.findUserByEmail(auth.getName());
        return currentUser;
    }
    public Boolean isAdmin(Authentication auth) {
    	if (auth == null) {
    		return false;
    	}
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
   
    
    @GetMapping("/")
    public String home(Model model,Authentication auth){
    	List<BookDto> books = bookService.findBooksByUserId(Long.valueOf(1));
    	model.addAttribute("books", books);
        return "index";
    }

   
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @GetMapping("/pay")
    public String pay(){
        return "developing";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
      
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,BindingResult result,Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,"Email đã được đăng ký");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        model.addAttribute("msg", "Đăng ký tài khoản thành công");
        return "/login";
    }

    
}