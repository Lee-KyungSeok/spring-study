package member.service;

import member.Member;
import member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//@Service("memService") // <beans:bean id="memService" class="com.bs.lec17.member.service.MemberService"></beans:bean> 와 동일한 방
//@Component("memService") // Component 를 사용해도 service 와 동일한 방식
@Repository("memService") // Repository 로 사용해도 된다. (세가지 중에 원하는 것을 사용하면 된다)
public class MemberService implements IMemberService {

    @Autowired
    MemberDao dao;

    @Override
    public void memberRegister(String memId, String memPw, String memMail,
                               String memPhone1, String memPhone2, String memPhone3) {
        System.out.println("memberRegister()");
        System.out.println("memId : " + memId);
        System.out.println("memPw : " + memPw);
        System.out.println("memMail : " + memMail);
        System.out.println("memPhone : " + memPhone1 + " - " + memPhone2 + " - " + memPhone3);

        dao.memberInsert(memId, memPw, memMail, memPhone1, memPhone2, memPhone3);
    }

    @Override
    public Member memberSearch(String memId, String memPw) {
        System.out.println("memberSearch()");
        System.out.println("memId : " + memId);
        System.out.println("memPw : " + memPw);

        Member member = dao.memberSelect(memId, memPw);

        return member;
    }

    @Override
    public Member[] memberModify(Member member) {

        Member memBef = dao.memberSelect(member.getMemId(), member.getMemPw());
        Member memAft = dao.memberUpdate(member);
        printMember(memAft);

        return new Member[]{memBef, memAft};
    }

    @Override
    public void memberRemove(Member member) {
        printMembers(dao.memberDelete(member));
    }

    private void printMembers(Map<String, Member> map) {

        Set<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            Member mem = map.get(key);
            printMember(mem);
        }

    }

    private void printMember(Member mem) {

        System.out.print("ID:" + mem.getMemId() + "\t");
        System.out.print("|PW:" + mem.getMemPw() + "\t");
        System.out.print("|MAIL:" + mem.getMemMail() + "\t");
        System.out.print("|PHONE:" + mem.getMemPhone1() + " - " +
                mem.getMemPhone2() + " - " +
                mem.getMemPhone3() + "\t");

    }

}
