package petppy;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing  // jpa 시간 관련 기능 활성화
@EnableBatchProcessing  // 배치 활성회
@EnableScheduling   // 스케쥴러 활성회
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 스프링 시큐리 어노테이션 활성화
public class PetppyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetppyApplication.class, args);
    }

}
