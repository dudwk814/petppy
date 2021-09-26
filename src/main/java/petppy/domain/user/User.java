package petppy.domain.user;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import petppy.domain.Address;
import petppy.domain.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"membership"})
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String picture;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Enumerated(EnumType.STRING)
    @Column(name = "sign_up_Type")
    private Type type;

    @OneToOne(mappedBy = "user", cascade = ALL)
    private Membership membership;

    @Builder
    public User(Long id, String email, String name, String password, Address address, Role role, String picture, Type type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
        this.picture = picture;
        this.type = type;
    }

    public void changeAddress(Address address) {
        this.address = address;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public void addMembership(Membership membership) {
        this.membership = membership;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
