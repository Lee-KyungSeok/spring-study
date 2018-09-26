package com.kyung;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@ConfigurationProperties("kyung")
@Validated
public class KyungProperties {

    // empty 를 방지할 수 있다.
    @NotEmpty
    private String name;

    // Size 도 지정 가능
    @Size(min = 0, max = 100)
    private int age;

    private String fullName;

    // duration 을 지정할 수 있다.
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration sessionTimeout = Duration.ofSeconds(30);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Duration getSesstionTimeout() {
        return sessionTimeout;
    }

    public void setSesstionTimeout(Duration sesstionTimeout) {
        this.sessionTimeout = sesstionTimeout;
    }
}
