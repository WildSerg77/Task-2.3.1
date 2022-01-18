package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String listUsers(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("users", userService.listUsers());
		return "index";
	}

	@PostMapping(value = "/add")
	public String addUser(@ModelAttribute ("user") User userIn) {
		if (userIn.getId() == 0) {
			userService.add(userIn);
		} else {
			User userOut = userService.getUserById(userIn.getId());
			if (userIn.getName().equals("")) {
				userIn.setName(userOut.getName());
			}
			if (userIn.getAge() == 0) {
				userIn.setAge(userOut.getAge());
			}
			if (userIn.getEmail().equals("")) {
                userIn.setEmail(userOut.getEmail());
            }
			userService.update(userIn);
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
		User user = userService.getUserById(id);
		userService.update(user);
		return "redirect:/";
	}
}