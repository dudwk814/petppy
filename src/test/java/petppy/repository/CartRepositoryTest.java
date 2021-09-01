/*
package petppy.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;

import petppy.domain.member.Member;
import petppy.domain.Item;
import petppy.domain.Cart;

import javax.persistence.EntityManager;

import java.util.Optional;

@SpringBootTest
@Transactional
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;
    
    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;
    
    @Test
    public void 상품_등록() throws Exception {
        //given
        Member member = addMember();

        Item item1 = addItem1();

        Cart cart = addCart(member, item1);

        //when
        Cart findCart = cartRepository.getById(cart.getId());

        //then
        System.out.println("findCart.getId() = " + findCart.getId());
        System.out.println("findCart.getMember().toString() = " + findCart.getMember().toString());
        System.out.println("findCart.getItem().toString() = " + findCart.getItem().toString());
        Assertions.assertThat(findCart.getCount()).isEqualTo(2);
    }

    @Test
    public void 카트_상품_수량_변경() throws Exception {
        //given
        Member member = addMember();

        Item item1 = addItem1();

        Cart cart = addCart(member, item1);

        em.flush();
        em.clear();

        Cart findCart = cartRepository.findById(cart.getId()).get();

        //when
        findCart.changeCount(6);

        //then
        Assertions.assertThat(findCart.getCount()).isEqualTo(6);
    }

    @Test
    public void 주문_취소() throws Exception {
        //given
        Member member = addMember();

        Item item1 = addItem1();

        Cart cart = addCart(member, item1);
        cartRepository.delete(cart);

        //when
        Optional<Cart> result = cartRepository.findById(cart.getId());

        //then
        org.junit.jupiter.api.Assertions.assertTrue(!result.isPresent());

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

    public Cart addCart(Member member, Item item) {
        Cart cart = Cart
                .builder()
                .member(member)
                .item(item)
                .count(2)
                .build();

        Cart savedCart = cartRepository.save(cart);

        return savedCart;
    }

}*/
