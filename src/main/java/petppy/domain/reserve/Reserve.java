package petppy.domain.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import petppy.domain.BaseTimeEntity;
import petppy.domain.services.Services;
import petppy.domain.services.ServicesType;
import petppy.domain.user.User;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static petppy.domain.reserve.ReserveType.CANCEL;
import static petppy.domain.services.ServicesType.CAT_SITTING;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Reserve extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "service_id")
    private Services services;

    @Column(name = "request")
    private String request;

    @Column(name = "reserve_start_date")
    private LocalDateTime reserveStartDate;

    @Column(name = "reserve_end_date")
    private LocalDateTime reserveEndDate;

    @Enumerated(STRING)
    private ReserveType reserveType;

    public void changeReserveTime(LocalDateTime reserveStartDate) {

        this.reserveStartDate = reserveStartDate;

        if (this.services.getServicesType() == CAT_SITTING) {
            this.reserveEndDate = reserveStartDate.plusHours(2);
        } // else if ~~
    }

    public void cancelReserve() {
        this.reserveType = CANCEL;
    }
}
