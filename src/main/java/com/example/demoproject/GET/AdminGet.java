package com.example.demoproject.GET;

import com.example.demoproject.OOP.Admin;
import com.example.demoproject.OOP.Teachers;
import com.example.demoproject.OOP.Students;
import com.example.demoproject.OOP.Employees;
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
    @GetMapping("/DanhSachGiaoVien")
    public String DanhSachGiaoVien(ModelMap model, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        List<Teachers> teachers = entityManager.createQuery("from Teachers", Teachers.class).getResultList();
        model.addAttribute("teachers",teachers);
        return "Admin/DanhSachGiaoVien";
    }
    @GetMapping("/ThemGiaoVien")
    public String ThemGiaoVien(ModelMap model, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        List<Employees> employees = entityManager.createQuery("from Employees", Employees.class).getResultList();
        model.addAttribute("employees",employees);
        return "Admin/ThemGiaoVien";
    }
    @GetMapping("/DanhSachHocSinh")
    public String DanhSachHocSinh(ModelMap model, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        List<Students> students = entityManager.createQuery("from Students", Students.class).getResultList();
        model.addAttribute("students",students);
        return "Admin/DanhSachHocSinh";
    }
    @GetMapping("/ThemHocSinh")
    public String ThemHocSinh(ModelMap model, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        List<Employees> employees = entityManager.createQuery("from Employees", Employees.class).getResultList();
        model.addAttribute("employees",employees);
        return "/ThemHocSinh";
    }
    @GetMapping("DanhSachNhanVien")
    public String DanhSachNhanVien(ModelMap model, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        List<Employees> employees = entityManager.createQuery("from Employees", Employees.class).getResultList();
        model.addAttribute("employees",employees);
        return "Admin/DanhSachNhanVien";
    }

    @GetMapping("/ThemNhanVien")
    public String ThemNhanVien(HttpSession session, ModelMap model) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        return "/ThemNhanVien";
    }
    @GetMapping("/XoaGiaoVien/{id}")
    public String XoaGiaoVien(@PathVariable("id") String id, HttpSession session, ModelMap model) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        Teachers teacher = entityManager.find(Teachers.class, id);
        if (teacher != null) {
            entityManager.remove(teacher);
        }
        return "redirect:Admin/DanhSachGiaoVien";
    }
    @GetMapping("/XoaHocSinh/{id}")
    public String XoaHocSinh(@PathVariable("id") String id, HttpSession session, ModelMap model) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        Students student = entityManager.find(Students.class, id);
        entityManager.remove(student);
        return "redirect:Admin/DanhSachHocSinh";
    }
    @Transactional
    @GetMapping("/XoaNhanVien/{id}")
    public String XoaNhanVien(@PathVariable("id") String id, HttpSession session, ModelMap model) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        Employees employee = entityManager.find(Employees.class, id);
        if (employee != null) {
            // Cập nhật tất cả Students có EmployeeID = id thành null
            entityManager.createQuery("UPDATE Students s SET s.employee = NULL WHERE s.employee.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            entityManager.createQuery("UPDATE Teachers s SET s.employee = NULL WHERE s.employee.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            // Sau đó xóa nhân viên
            entityManager.remove(employee);
        }
        return "redirect:Admin/DanhSachNhanVien";
    }
    @GetMapping("/SuaHocSinh/{id}")
    public String SuaHocSinh(ModelMap model, @PathVariable("id") String id, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        Students student = entityManager.find(Students.class, id);
        List<Employees> employees=entityManager.createQuery("from Employees", Employees.class).getResultList();
        model.addAttribute("student",student);
        model.addAttribute("employees",employees);
        return "/SuaHocSinh";
    }
    @GetMapping("/SuaGiaoVien/{id}")
    public String SuaGiaoVien(ModelMap model, @PathVariable("id") String id, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        List<Employees> employees=entityManager.createQuery("from Employees", Employees.class).getResultList();
        Teachers teachers = entityManager.find(Teachers.class, id);
        model.addAttribute("teachers",teachers);
        model.addAttribute("employees",employees);
        return "/SuaGiaoVien";
    }
    @GetMapping("/SuaNhanVien/{id}")
    public String SuaAdmin(ModelMap model, @PathVariable("id") String id, HttpSession session) {
        if(session.getAttribute("AdminID") == null) {
            return "redirect:/DangNhapAdmin";
        }
        Employees employee = entityManager.find(Employees.class, id);
        model.addAttribute("employees",employee);
        return "/SuaNhanVien";
    }
}
