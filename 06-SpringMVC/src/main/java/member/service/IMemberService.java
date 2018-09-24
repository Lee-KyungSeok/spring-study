package member.service;

import member.Member;

public interface IMemberService {
    void memberRegister(String memId, String memPw, String memMail, String memPhone1, String memPhone2, String memPhone3);
    Member memberSearch(String memId, String memPw);
    Member[] memberModify(Member member);
    void memberRemove(Member member);
}
