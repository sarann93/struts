package org.superbiz.struts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UsersController {
   // private UserService userService;

    private final UserRepository userRepository;

    /*public UsersController(UserService userService) {
        this.userService = userService;
    }*/

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/addUser")
    public String addUserForm() {
        return "addUserForm";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam long id, @RequestParam String firstName,
                          @RequestParam String lastName, Model model) {
        try {
            userRepository.save(new User(id, firstName, lastName));
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "addUserForm";
        }
        return "addedUser";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "displayUsers";
    }

    @GetMapping("/findUser")
    public String findUserForm() {
        return "findUserForm";
    }

    @PostMapping("/findUser")
    public String findUser(@RequestParam long id, Model model) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("errorMessage", "User not found");
            return "findUserForm";
        }

        return "displayUser";
    }

}
