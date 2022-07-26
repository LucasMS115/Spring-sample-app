package io.github.LucasMS115.spring_sales.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Column
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @Column
    private boolean admin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return Id != null && Objects.equals(Id, appUser.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
