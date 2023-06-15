package hello.core.Member;

import hello.core.Member.Member;

public interface MemberService {
    void join(Member member);

    Member findMember(Long memberId);

}
