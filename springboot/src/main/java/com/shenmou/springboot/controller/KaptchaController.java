package com.shenmou.springboot.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class KaptchaController {
    @Resource
    private DefaultKaptcha defaultKaptcha;

    //生成图片验证码
    @GetMapping("/createImageCode")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成文字验证码
        String text = defaultKaptcha.createText();
        // 生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        //保存到session域
        HttpSession session = request.getSession();
        session.setAttribute("imageCode",text);
        ServletOutputStream out = null;
        try {
            //响应输出图片流
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //匹对图片验证码
    @GetMapping("/verifyImageCode")
    public Result verifyImageCode(String verificationCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("imageCode").equals(verificationCode)) {
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600, "验证码错误");
        }
    }
}
