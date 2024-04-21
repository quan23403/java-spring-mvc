package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {
    
    @GetMapping("/")
    public String index() {
        return "Hello World HMQ";
    }

    @GetMapping("/user")
    public String userPage() {
        return "Hello User";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "Hello Admin";
    }
    
}
