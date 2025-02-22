package com.example.demoproject.POST;


import com.example.demoproject.OOP.Admin;
import com.example.demoproject.OOP.Employees;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/")
@Transactional
public class NhanVienPost {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/DangKyNhanVien")
    public String DangKyNhanVien(@RequestParam String EmployeeID, @RequestParam String FirstName,
                                 @RequestParam String LastName, @RequestParam String Email, @RequestParam String PhoneNumber,
                                 @RequestParam String Password) {
        List<Admin> admins = entityManager.createQuery("from Admin", Admin.class).getResultList();
        Admin admin = admins.get(0);
        Employees employees = new Employees();
        employees.setId(EmployeeID);
        employees.setFirstName(FirstName);
        employees.setLastName(LastName);
        employees.setEmail(Email);
        employees.setPassword(Password);
        employees.setPhoneNumber(PhoneNumber);
        employees.setAdmin(admin);

        entityManager.persist(employees);

        return "redirect:/DangNhapNhanVien";
    }

    @PostMapping("/DangNhapNhanVien")
    public String DangNhapNhanVien(@RequestParam("EmployeeID") String employeeID,
                                   @RequestParam("Password") String password,
                                   HttpSession session,
                                   ModelMap model) {
        try {
            Employees employee = entityManager.createQuery(
                            "SELECT e FROM Employees e WHERE e.id = :employeeID", Employees.class)
                    .setParameter("employeeID", employeeID)
                    .getSingleResult();

            if (employee != null && employee.getPassword().equals(password)) {
                session.setAttribute("EmployeeID", employee.getId());
                return "redirect:/TrangChuNhanVien";
            } else {
                model.addAttribute("error", "Mã nhân viên hoặc mật khẩu không đúng!");
                return "redirect:/DangNhapNhanVien";
            }
        } catch (NoResultException e) {
            model.addAttribute("error", "Mã nhân viên không tồn tại!");
            return "redirect:/DangNhapNhanVien";
        }
    }
}
