package petppy.repository.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.answer.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
