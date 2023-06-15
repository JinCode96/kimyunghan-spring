package hello.core.Member;

import hello.core.Member.Member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);

}
