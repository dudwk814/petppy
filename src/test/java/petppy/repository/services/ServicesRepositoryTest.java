package petppy.repository.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.services.Services;
import petppy.domain.services.ServicesType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static petppy.domain.services.ServicesType.*;

@SpringBootTest
@Transactional
class ServicesRepositoryTest {

    @Autowired
    ServicesRepository servicesRepository;

    @Test
    @Commit
    public void 서비스_등록() throws Exception {
        addService();

    }

    @Test
    @Commit
    public void 전체_서비스_등록() throws Exception {
        addServices();
    }

    @Test
    public void 서비스_전체_조회() throws Exception {
        //given
        Services savedService = addService();

        //when
        List<Services> result = servicesRepository.findAll();

        //then
        assertEquals(1, result.size()); // addServices() -> 1개
    }

    private Services addService() {

        Services services = Services
                .builder()
                .servicesType(CAT_SITTING)
                .build();

        return servicesRepository.save(services);
    }

    private void addServices() {

        servicesRepository.save(Services
                .builder()
                .servicesType(CAT_SITTING)
                .build());

        servicesRepository.save(Services
                .builder()
                .servicesType(DOG_WALK)
                .build());

        servicesRepository.save(Services
                .builder()
                .servicesType(PET_SPA)
                .build());

        servicesRepository.save(Services
                .builder()
                .servicesType(PET_GROOMING)
                .build());

        servicesRepository.save(Services
                .builder()
                .servicesType(PET_DAYCARE)
                .build());
    }
}