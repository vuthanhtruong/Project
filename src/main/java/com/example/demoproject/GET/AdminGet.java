package com.example.demoproject.GET;


import com.example.demoproject.OOP.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@Transactional
public class AdminGet {
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/TrangChuAdmin")
    public String TrangChuAdmin(HttpSession session, ModelMap model) {
        if (session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        Admin admin = entityManager.find(Admin.class, session.getAttribute("AdminID"));
        model.addAttribute("admin", admin);
        return "Admin/TrangChuAdmin";
    }

    @GetMapping("/DangXuatAdmin")
    public String DangXuatAdmin(HttpSession session) {
        session.invalidate(); // Hủy toàn bộ session
        return "redirect:/DangNhapAdmin";
    }
}
