package com.shenmou.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenmou.springboot.entity.User;
import com.shenmou.springboot.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    public static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    /**
     * 生成token
     */
    public static String getToken(String userName, String sign) {
        return JWT.create().withAudience(userName) // 将 userName 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userName = JWT.decode(token).getAudience().get(0);
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_name", userName);
                return staticUserService.getOne(queryWrapper);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
