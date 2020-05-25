package com.taikang.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author itw_gongxy
 * @date 2020/2/27 16:37
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;
   /* //验证码有个文字
    private int charCount = 6;

    private int width = 120;

    private int height =30;

    private int drawY = 20;

    private int space = 15;

    private String[] chars ={"A","B","C","D","E","F",
            "G","H","I","J","K","L","M","N","O","P","T","U","V","W",
            "X","Y","Z","1","2","3","4","5","6","7","8","9","0"};


    @RequestMapping("/code")
    public void makeCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/png");

        //创建一个透明图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取一个画笔
        Graphics graphics = image.getGraphics();
        //画笔颜色设置为蓝色色
        graphics.setColor(Color.CYAN);
        //将画板涂成蓝色
        graphics.fillRect(0, 0, width, height);
        //设置字体
        Font font = new Font("雅黑",Font.BOLD,16);
        graphics.setFont(font);
        graphics.setColor(Color.lightGray);

        String s =creatCharacter(graphics);

        for(int m=0;m<4;m++){
            graphics.setColor(makeColor());
            int dot [] = makeLineDot();
            graphics.drawLine(dot[0],dot[1],dot[2],dot[3]);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"png",outputStream);
        outputStream.flush();
        outputStream.close();

    }

    private String creatCharacter(Graphics graphics) {
        StringBuffer buffer = new StringBuffer();

        int rand = 0;
        for(int i=0;i<charCount;i++){
            int nextInt = new SecureRandom().nextInt(chars.length);
            String data = chars[nextInt];
            buffer.append(data);
            graphics.setColor(makeColor());
            graphics.drawString(data,(i+1)*space,20);
        }

        return buffer.toString();
    }

    private Color makeColor() {
        SecureRandom random = new SecureRandom();
        int a = random.nextInt(255);
        int b = random.nextInt(255);
        int c = random.nextInt(255);

        return new Color(a,b,c);

    }

    private int [] makeLineDot(){
        Random random = new Random();
        int x1 = random.nextInt(width/2);
        int y1 = random.nextInt(height);

        int x2 = random.nextInt(width);
        int y2 = random.nextInt(height);

        return new int[]{x1,y1,x2,y2};

    }*/
   private int w = 70;
    private int h = 24;

    @GetMapping(value = "/code")
    public void getVlidateCode(HttpServletRequest request, HttpServletResponse response, String identifier)
            throws IOException {
        createImage(request, response, identifier);
    }

    private void createImage(HttpServletRequest request, HttpServletResponse response, String identifier)
            throws IOException {

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        /*
         * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
         */
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        if (StringUtils.isNumeric(width) && StringUtils.isNumeric(height)) {
            w = NumberUtils.toInt(width);
            h = NumberUtils.toInt(height);
        }

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        /*
         * 生成背景
         */
        createBackground(g);

        /*
         * 生成字符
         */
        String s = createCharacter(g);

        g.dispose();
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        out.close();
    }

    private Color getRandColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        SecureRandom random = new SecureRandom();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
    }

    private void createBackground(Graphics g) {
        // 填充背景
        g.setColor(getRandColor(220, 250));
        g.fillRect(0, 0, w, h);
        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            g.setColor(getRandColor(40, 150));
            SecureRandom random = new SecureRandom();
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            g.drawLine(x, y, x1, y1);
        }
    }

    private String createCharacter(Graphics g) {
        char[] codeSeq = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
        String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
        SecureRandom random = new SecureRandom();
        StringBuilder s = new StringBuilder();
        StringBuilder sr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]); // random.nextInt(10));
            sr.append(r);
            g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
            g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD, 26));
            g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
            // g.drawString(r, i*w/4, h-5);
            s.append(r);
        }

        return s.toString();
    }
}
