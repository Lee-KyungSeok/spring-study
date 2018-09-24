import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
    public static void main(String args[]) {

        // 아래는 보통 자바 형식
//        TransportationWalk transportationWalk = new TransportationWalk();
//        transportationWalk.move();

        // spring 에서 사용할 때 container 에 존재하는 bean(객체) 를 불러와서 사용한다.
        // 1. xml 의 context(리소스) 를 불러온다.
        // 2. bean 을 가져와서 클래스를 선언한다. (bean 의 id 와 class 를 이용하여)
        // 3. 클래스의 객체를 사용할 수 있다.
        // 4. 마지막에 리소스를 닫아준다.
        GenericXmlApplicationContext xmlCtx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        TransportationWalk transportationWalk = xmlCtx.getBean("tWalk", TransportationWalk.class);
        transportationWalk.move();
        xmlCtx.close();
    }
}
