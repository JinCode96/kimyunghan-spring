package hello.core.discount;

import hello.core.Member.Grade;
import hello.core.Member.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private final int discountFixAmount = 1000; // 1000원 할인
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
