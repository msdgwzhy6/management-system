package indi.orange1438.managementsystem.web.system.verCode;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA 14.
 * User: karl.zhao
 * Time: 2016/2/1 0001.
 * Verification code
 */
@Controller
@RequestMapping("/code")
public class VerCodeController {

    @RequestMapping
    public void generate(HttpServletRequest request,HttpServletResponse response){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = drawImg(output);

//        System.out.println("客户端传的Get时间值："+request.getParameter("t"));
//        Subject currentUser = SecurityUtils.getSubject();
//        Session session = currentUser.getSession();
//        session.setAttribute(Const.SESSION_SECURITY_CODE, code);

        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String drawImg(ByteArrayOutputStream output){
        String code = "";
        for(int i=0; i<4; i++){
            code += randomChar();
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman",Font.PLAIN,20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66,2,82);
        g.setColor(color);
        g.setBackground(new Color(226,226,240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int)x, (int)baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    private char randomChar(){
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }
}
