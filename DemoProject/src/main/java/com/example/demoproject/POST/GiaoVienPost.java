
package com.example.demoproject.POST;

import com.example.demoproject.OOP.Admin;
import com.example.demoproject.OOP.Employees;
import com.example.demoproject.OOP.Teachers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
@Transactional
public class GiaoVienPost {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/DangKyGiaoVien")
    public String dangKyGiaoVien(@RequestParam("EmployeeID") String employeeID,
                                 @RequestParam("TeacherID") String teacherID,
                                 @RequestParam("FirstName") String firstName,
                                 @RequestParam("LastName") String lastName,
                                 @RequestParam("Email") String email,
                                 @RequestParam("PhoneNumber") String phoneNumber,
                                 @RequestParam(value = "MisID", required = false) String misID,
                                 @RequestParam("Password") String password,
                                 @RequestParam("ConfirmPassword") String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            return "redirect:/DangKyGiaoVien?error=passwordsNotMatch";
        }

        List<Admin> admins = entityManager.createQuery("from Admin", Admin.class).getResultList();
        if (admins.isEmpty()) {
            return "redirect:/DangKyGiaoVien?error=noAdminFound";
        }

        Admin admin = admins.get(0);
        Employees employee = entityManager.find(Employees.class, employeeID);

        if (entityManager.find(Teachers.class, teacherID) != null) {
            return "redirect:/DangKyGiaoVien?error=teacherIDExists";
        }

        Teachers giaoVien = new Teachers();
        giaoVien.setEmployee(employee);
        giaoVien.setAdmin(admin);
        giaoVien.setId(teacherID);
        giaoVien.setFirstName(firstName);
        giaoVien.setLastName(lastName);
        giaoVien.setEmail(email);
        giaoVien.setPhoneNumber(phoneNumber);
        giaoVien.setMisID(misID);
        giaoVien.setPassword(password);
        entityManager.persist(giaoVien);

        return "redirect:/DangNhapGiaoVien";
    }

    @PostMapping("/DangNhapGiaoVien")
    public String DangNhapGiaoVien(@RequestParam("TeacherID") String teacherID,
                                   @RequestParam("Password") String password,
                                   ModelMap model,
                                   HttpSession session) {
        try {
            Teachers teacher = entityManager.createQuery(
                            "SELECT t FROM Teachers t WHERE t.id = :teacherID", Teachers.class)
                    .setParameter("teacherID", teacherID)
                    .getSingleResult();

            if (teacher != null && teacher.getPassword().equals(password)) {
                session.setAttribute("TeacherID", teacher.getId());
                return "redirect:/TrangChuGiaoVien";
            } else {
                model.addAttribute("error", "Mã giáo viên hoặc mật khẩu không đúng!");
                return "DangNhapGiaoVien";
            }
        } catch (NoResultException e) {
            model.addAttribute("error", "Mã giáo viên không tồn tại!");
            return "DangNhapGiaoVien";
        }
    }
}
