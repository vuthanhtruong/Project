
package com.example.demoproject.POST;


import com.example.demoproject.OOP.Admin;
import com.example.demoproject.OOP.Employees;
import com.example.demoproject.OOP.Students;
import com.example.demoproject.OOP.Teachers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@Transactional
public class AdminPost {
    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/DangNhapAdmin")
    public String DangNhapAdmin(@RequestParam("AdminID") String AdminID,
                                @RequestParam("PasswordAdmin") String PasswordAdmin,
                                HttpSession session) {
        Admin admin = entityManager.find(Admin.class, AdminID);

        if (admin == null) {
            return "redirect:/DangNhapAdmin";
        }

        if (!PasswordAdmin.equals(admin.getPassword())) {
            return "redirect:/DangNhapAdmin";
        }

        session.setAttribute("AdminID", AdminID);
        return "redirect:/TrangChuAdmin";
    }
}