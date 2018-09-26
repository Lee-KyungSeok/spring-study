package com.kyung;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws LifecycleException {
        // 아래처럼 톰캣을 사용할 수 있다. (참고만 하고... 사실 쓸 일은 없다.)
        // - (스프링부트는 서버가 아니라 톰캣을 포함한 컨테이너)

        // 1. 톰캣 객체 생성
        Tomcat tomcat = new Tomcat();
        // 2. 포트 설정
        tomcat.setPort(8080);
        // 3. 톰캣에 컨텍스트 추가
        Context context = tomcat.addContext("/", "/");
        // 4. 서블릿 만들기
        HttpServlet servlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                PrintWriter writer = resp.getWriter();
                writer.println("<html><head><title>");
                writer.println("Hey, Tomcat");
                writer.println("</title></head>");
                writer.println("<body><h1>Hello Tomcat</h1></body>");
                writer.println("</html>");
            }
        };
        // 5. 톰캣에 서블릿 추가
        String servletName = "helloServlet";
        tomcat.addServlet("/", servletName, servlet);
        // 6. 컨텍스트에 서블릿 맵핑
        context.addServletMappingDecoded("/hello", servletName);
        // 7. 톰캣 실행 및 대기
        tomcat.start();
        tomcat.getServer().await();

        /* 하지만 위와 같은 작업을 상세히 또 유연하고 설정하고 실행해주는게 바로 스프링 부트의 자동 설정이다.
            - ServletWebServerFactoryAutoConfiguration (서블릿 웹 서버 생성)
              - TomcatServletWebServerFactoryCustomizer (서버 커스터마이징)
            - DispatcherServletAutoConfiguration (서블릿 만들고 등록)
         */
    }
}

