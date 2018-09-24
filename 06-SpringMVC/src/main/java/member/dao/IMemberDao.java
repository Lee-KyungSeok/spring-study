package member.dao;

import member.Member;

import java.util.Map;

public interface IMemberDao {
    void memberInsert(String memId, String memPw, String memMail, String memPhone1, String memPhone2, String memPhone3);
    Member memberSelect(String memId, String memPw);
    Member memberUpdate(Member member);
    Map<String, Member> memberDelete(Member member);
}
