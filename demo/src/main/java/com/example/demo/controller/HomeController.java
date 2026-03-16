package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:/products";
    }

    @GetMapping("/home")
    @ResponseBody
    public String home(Authentication authentication) {
        String username = authentication.getName();
        return "Hello " + username + " (quyen: " + authentication.getAuthorities() + ")";
    }

    @GetMapping("/admin/dashboard")
    @ResponseBody
    public String adminDash() {
        return "welcome back admin";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("message",
                "Bạn không có quyền thực hiện chức năng này. Chỉ tài khoản ADMIN mới được phép thêm sản phẩm.");
        return "access-denied";
    }
}
