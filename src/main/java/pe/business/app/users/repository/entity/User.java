package pe.business.app.users.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_users")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class User extends AuditingEntity implements Serializable,BaseEntity{

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    @Id
    @NotEmpty(message = "uuid must not be empty")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotNull(message = "email not be null")
    @Column(name = "email", nullable=false)
    private String email;

    @NotNull(message = "email not be null")
    @Column(name = "password", nullable=false)
    private String password;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    private Date lastLogin;

    private String token;

    @NotNull(message = "isactive must not be null")
    private boolean active;

    @PrePersist
    public void prePersist() {
        this.setCreatedDate(new Date());
        this.setUpdatedDate(new Date());
        this.setLastLogin(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedDate(new Date());
        this.setLastLogin(new Date());
    }

}
