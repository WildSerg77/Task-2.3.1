package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class UserController {

	private UserService userService;

	@Autowired()
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String listUsers(Model model) {
	//	userService.add(new User(1, "Bob", 25, "Bob@yager.com"));
		model.addAttribute("user", new User());
		model.addAttribute("users", userService.listUsers());
		return "index";
	}

	@PostMapping(value = "/add")
	public String addUser(@ModelAttribute ("user") User user) {
        if (user.getId() == 0) {
			userService.add(user);
		} else {
			userService.update(user);
		}
        return "redirect:/";
    }

	@PostMapping(value = "/remove")
	public String removeUser(@RequestParam(value = "id") int id) {
		User user = userService.getUserById(id);
		userService.delete(user);
		return "redirect:/";
	}

	@PostMapping(value ="/update")
	public String updateUser(@RequestParam(value = "id") int id) {
	//	model.addAttribute("user", userService.getUserById(id));
	//	model.addAttribute("users", userService.listUsers());
		User user = userService.getUserById(id);
		userService.update(user);
		return "redirect:/";
	}
}