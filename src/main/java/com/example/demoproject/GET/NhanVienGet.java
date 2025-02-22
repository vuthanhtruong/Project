package com.example.demoproject.GET;

import com.example.demoproject.OOP.Employees;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Transactional
public class NhanVienGet {

    @PersistenceContext
    private EntityManager entityManager;


    @GetMapping("/DangKyNhanVien")
    public String DangKyNhanVien() {
        return "Employees/DangKyNhanVien";
    }

    @GetMapping("/TrangChuNhanVien")
    public String TrangChuNhanVien(ModelMap model, HttpSession session) {
        if (session.getAttribute("EmployeeID") == null) {
            return "redirect:/DangNhapNhanVien";
        }
        Employees employee = entityManager.find(Employees.class, session.getAttribute("EmployeeID"));
        model.addAttribute("employee", employee);
        return "Employees/TrangChuNhanVien";
    }

    @GetMapping("/DangXuatNhanVien")
    public String DangXuatGiaoVien(HttpSession session) {
        session.invalidate();
        return "redirect:/DangNhapNhanVien";
    }
}