
package com.example.demoproject.OOP;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Teachers")
@PrimaryKeyJoinColumn(name = "ID") // Liên kết với khóa chính của Person
@Getter
@Setter
public class Teachers extends Person {

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "MIS_ID", length = 100)
    private String misID;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employees employee; // Liên kết với Employee (có thể là NULL)

    @ManyToOne
    @JoinColumn(name = "AdminID", nullable = true) // Có thể NULL nếu không có Admin trực tiếp quản lý
    private Admin admin;

    // Constructor có tham số
    public Teachers(String id, String password, String firstName, String lastName, String email, String phoneNumber, String misID, Employees employee, Admin admin) {
        super(); // Gọi constructor của Person
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.password = password;
        this.misID = misID;
        this.employee = employee;
        this.admin = admin;
    }

    // Constructor không tham số (cần thiết cho JPA)
    public Teachers() {
    }
}
