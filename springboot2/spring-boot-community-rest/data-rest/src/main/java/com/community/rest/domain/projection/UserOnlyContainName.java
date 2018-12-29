package com.community.rest.domain.projection;

import com.community.rest.domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "getOnlyName", types = {User.class}) // 노출시키고 싶은 것만 정해줄 수 있다.(별로 추천하지는 않는다... users/1 하면 적용 안됨)
public interface UserOnlyContainName {

    String getName();
}
