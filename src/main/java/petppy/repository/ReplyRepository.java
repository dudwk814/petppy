package petppy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
