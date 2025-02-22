package com.example.demoproject.GET;

import com.example.demoproject.OOP.Employees;
import com.example.demoproject.OOP.Teachers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
@Transactional
public class GiaoVienGet {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/DangKyGiaoVien")
    public String DangKyGiaoVien(ModelMap model) {
        // Sử dụng EntityManager để thực thi truy vấn
        List<Employees> employees = entityManager.createQuery("from Employees", Employees.class).getResultList();
        model.addAttribute("employees", employees);
        return "DangKyGiaoVien.html";
    }

    @GetMapping("/TrangChuGiaoVien")
    public String DangNhapGiaoVien(ModelMap model, HttpSession session) {
        if (session.getAttribute("TeacherID") == null) {
            return "DangNhapGiaoVien";
        }
        Teachers teacher = entityManager.find(Teachers.class, session.getAttribute("TeacherID"));
        model.addAttribute("teacher", teacher);
        return "TrangChuGiaoVien";
    }

    @GetMapping("/DangXuatGiaoVien")
    public String DangXuatGiaoVien(HttpSession session) {
        session.invalidate();
        return "redirect:/DangNhapGiaoVien";
    }
}