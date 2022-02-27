package petppy.repository.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import petppy.domain.user.Type;
import petppy.domain.user.User;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    public List<User> findByNameContaining(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"membership"}, type = FETCH)
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"membership"}, type = FETCH)
    Optional<User> findByEmailAndType(String email, Type type);

    void deleteByEmail(String email);



}

