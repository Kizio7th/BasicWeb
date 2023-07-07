package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Paid;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.PaidService;
import com.example.demo.service.UserService;

@Controller
public class AdminController {
	private UserService userService;
	private BookService bookService;
	private PaidService paidService;
	
    
    public AdminController(UserService userService, BookService bookService, PaidService paidService) {
		super();
		this.userService = userService;
		this.bookService = bookService;
		this.paidService = paidService;
	}
	@ModelAttribute("currentUser")
    public User currentUser(Authentication auth) {
    	if (auth == null) {
    		return new User();
    	}
    	User currentUser = userService.findUserByEmail(auth.getName());
        return currentUser;
    }
    @ModelAttribute("isAdmin")
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
    @GetMapping("/admin/books")
    public String books(Model model){
        List<BookDto> books = bookService.findBooksByUserId(Long.valueOf(1));
        model.addAttribute("books", books);
        return "books";
    }
    @GetMapping("/admin/book/{id}")
    public String book(Model model, @PathVariable("id") Long id,Authentication auth){
    	BookDto book = new BookDto();
    	if(id != 0) {
    		book = bookService.mapToBookDto(bookService.findBookById(id));
    	}
        model.addAttribute("book",book);
        return "book";
    }
    @PostMapping("/admin/book/0/save")
    public String addBook(@ModelAttribute("book") BookDto bookDto,Model model, @RequestParam("photo") MultipartFile bookImage,Authentication auth, BindingResult result){
    	User currentUser = currentUser(auth);
    	List<BookDto> existingBook = bookService.findBooksByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
    	if(existingBook != null && !existingBook.isEmpty()){
    		result.rejectValue("title", null, "Trùng tiêu đề và tác giả");
        }
        if(result.hasErrors()){
        	model.addAttribute("error", "Trùng tiêu đề và tác giả");
        	model.addAttribute("book", new BookDto());
        	return "/book";
        }
        bookService.saveBook(bookDto, bookImage, currentUser);
        List<BookDto> books = bookService.findBooksByUserId(Long.valueOf(1));
        model.addAttribute("books", books);
        model.addAttribute("msg", "Thêm thành công");
        return "books";
    }
    @PostMapping("/admin/book/{id}/update")
    public String updateBook(Model model,@ModelAttribute("book") BookDto bookDto, @PathVariable("id") Long id,Authentication auth, @RequestParam("photo") MultipartFile bookImage, BindingResult result) {
    	List<BookDto> existingBook = bookService.findBooksByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
    	if(existingBook != null && !existingBook.isEmpty()){
            if(existingBook.get(0).getId() != id) {
            	result.rejectValue("title", null, "Trùng tiêu đề và tác giả");
            }
        }
    	if(result.hasErrors()){
    		if(bookImage.isEmpty()) {
    			byte[] image = bookService.findBookById(id).getCover();
    			bookDto.setCover(bookService.handleByte(image));
    		}
    		else bookDto.setCover(bookService.handleMultipartFile(bookImage));
    		model.addAttribute("book", bookDto);
    		return "/book";
        }
    	bookService.updateBook(bookDto, bookImage, id);
    	BookDto book = bookService.mapToBookDto(bookService.findBookById(id));
    	model.addAttribute("book",book);
    	model.addAttribute("msg", "Sửa thành công");
    	return "book" ;
    }
    @PostMapping("/admin/book/{id}/delete")
    public String deleteBook(Model model, @PathVariable("id") Long id, Authentication auth){
    	bookService.deleteBook(id);
    	List<BookDto> books = bookService.findBooksByUserId(Long.valueOf(1));
        model.addAttribute("books", books);
        model.addAttribute("msg", "Xoá thành công");
        return "books";
    }
    @GetMapping("/admin/paid")
    public String paid(Model model){
        List<Paid> paids = paidService.findAllPaids();
        model.addAttribute("paids", paids);
        return "paid";
    }
}
