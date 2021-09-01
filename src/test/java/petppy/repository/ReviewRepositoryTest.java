/*
package petppy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.*;
import petppy.domain.member.Member;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static petppy.domain.PaymentMethod.CASH;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    EntityManager em;


    @Test
    public void 리뷰_등록() throws Exception {
        //given
        Member member = addMember();

        Item item = addItem1();


        Order order = addOrder(member);

        Review review = addReview(member, order, item);

        //when
        Review findReview = reviewRepository.getById(review.getId());

        //then
        System.out.println("findReview = " + findReview.getTitle());
        System.out.println("findReview.getContent() = " + findReview.getContent());

        assertThat(findReview.getRating()).isEqualTo(5.0);
    }

    @Test
    public void 리뷰_변경() throws Exception {
        //given
        Member member = addMember();

        Item item = addItem1();


        Order order = addOrder(member);

        Review review = addReview(member, order, item);

        Review findReview = reviewRepository.getById(review.getId());

        //when
        findReview.changeReview("modifiedTitle", "modifiedContent", findReview.getRating(), findReview.getImage1(), findReview.getImage2(), findReview.getImage3());

        em.flush();
        em.clear();

        //then
        System.out.println("findReview = " + findReview);
    }

    private Member addMember() {
        //given
        Member member = Member.builder()
                .id("aaa")
                .password("bbb")
                .name("hhh")
                .address(Address.builder().city("111").street("222").zipcode("333").build())
                .build();

        memberRepository.save(member);
        return member;
    }

    public Item addItem1() {
        Item item1 = Item
                .builder()
                .name("상품1")
                .price(10000)
                .stockQuantity(10)
                .discountRate(10.0)
                .build();

        itemRepository.save(item1);

        return item1;
    }

    public Item addItem2() {
        Item item2 = Item
                .builder()
                .name("상품2")
                .price(30000)
                .stockQuantity(10)
                .discountRate(10.0)
                .build();
        itemRepository.save(item2);

        return item2;
    }

    public Order addOrder(Member member) {
        Order order = Order
                .builder()
                .member(member)
                .address(member.getAddress())
                .name(member.getName())
                .paymentMethod(CASH)
                .build();

        orderRepository.save(order);

        return order;
    }

    public Review addReview(Member member, Order order, Item item) {
        Review review = Review.builder()
                .item(item)
                .order(order)
                .member(member)
                .content("testReviewContent")
                .rating(5.0)
                .title("testReviewTitle")
                .build();

        reviewRepository.save(review);

        return review;
    }

}
*/
