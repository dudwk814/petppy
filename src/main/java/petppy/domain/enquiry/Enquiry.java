package petppy.domain.enquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import petppy.domain.BaseTimeEntity;
import petppy.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Enquiry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enquiry_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private EnquiryType enquiryType;

    @Enumerated(EnumType.STRING)
    private EnquiryStatus enquiryStatus;

    @Column(name = "enquiry_title")
    private String title;

    @Column(name = "enquiry_content")
    private String content;

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeEnquiryStatusToComplete() {
        this.enquiryStatus = EnquiryStatus.COMPLETE;
    }

}
