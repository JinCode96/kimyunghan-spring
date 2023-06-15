package hello.core.Member;

public class MemberServiceImpl implements MemberService {

    // 구현체에 의존
    private final MemberRepository memberRepository;

    // 생성자를 통해서 구현체 정함
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
