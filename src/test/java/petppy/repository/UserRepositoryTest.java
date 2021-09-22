package petppy.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;
import petppy.domain.user.*;
import petppy.exception.UserNotFoundException;
import petppy.repository.user.MembershipRepository;
import petppy.repository.user.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static petppy.domain.user.Role.MEMBER;

@SpringBootTest
@Transactional
@Commit
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    @Test
    @Commit
    public void 일반_회원가입() throws Exception {
        User user = User
                .builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("1234"))
                .name("testUser")
                .type(Type.NORMAL)
                .role(MEMBER)
                .build();

        Membership membership = Membership.builder().user(user).build();

        user.addMembership(membership);



        userRepository.save(user);
    }

    @Test
    @Commit
    public void 멤버십_변경() throws Exception {
        User user = userRepository.findByEmail("kj99658103@gmail.com").orElseThrow(UserNotFoundException::new);

        user.getMembership().changeRating(Rating.BUSINESS);

        em.flush();
        em.clear();

        User findUser = userRepository.findByEmail("kj99658103@gmail.com").orElseThrow(UserNotFoundException::new);

        System.out.println("findUser.rating = " + findUser.getMembership().getRating());
    }




}
