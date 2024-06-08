package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

// @RestController
// public class UserController {

//     private UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @GetMapping("")
//     public String getHomePage() {
//         return this.userService.handleHello();
//     }
// }

@Controller
public class UserController {

    private final UserService userService;

    // private final UserRepository userRepository;
    public UserController(UserService userService) {
        this.userService = userService;
        // this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        // System.out.println("sadasd");
        // String test = this.userService.handleHello();
        List<User> arrUsers = this.userService.getAllUsersByEmail("quan23403@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("eric", "test");
        model.addAttribute("hoidanit", "from TCLC");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        // model.addAttribute("newUser", new User());
        List<User> users = this.userService.getAllUsers();
        // System.out.println(users);
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        // System.out.println("run here " + hoidanit);
        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

}
