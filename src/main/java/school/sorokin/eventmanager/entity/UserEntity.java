package school.sorokin.eventmanager.entity;

import jakarta.persistence.*;
import school.sorokin.eventmanager.model.UserRole;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "passwordHash")
    private String passwordHash;

    @Column(name = "age")
    private Integer age;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserEntity(Long id, String login, String passwordHash, Integer age, UserRole role) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.age = age;
        this.role = role;
    }

    public UserEntity() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
