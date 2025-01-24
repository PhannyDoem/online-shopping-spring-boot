package com.onlineShopping.util;

import com.onlineShopping.model.ProductOrder;
import com.onlineShopping.model.UserDetails;
import com.onlineShopping.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Component
public class CommonUtil {


    private  JavaMailSender mailSender;

    @Autowired
    private UserService userService;




    public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("onatasashimi@gmail.com", "Shopping Cart");
        helper.setTo(reciepentEmail);

        String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
                + "\">Change my password</a></p>";
        helper.setSubject("Password Reset");
        helper.setText(content, true);
        mailSender.send(message);
        return true;
    }

    public static String generateUrl(HttpServletRequest request) {

        // http://localhost:8080/forgot-password
        String siteUrl = request.getRequestURL().toString();

        return siteUrl.replace(request.getServletPath(), "");
    }

    String msg=null;;

    public void sendMailForProductOrder(ProductOrder order, String status) throws Exception
    {

        msg="<p>Hello [[name]],</p>"
                + "<p>Thank you order <b>[[orderStatus]]</b>.</p>"
                + "<p><b>Product Details:</b></p>"
                + "<p>Name : [[productName]]</p>"
                + "<p>Category : [[category]]</p>"
                + "<p>Quantity : [[quantity]]</p>"
                + "<p>Price : [[price]]</p>"
                + "<p>Payment Type : [[paymentType]]</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("daspabitra55@gmail.com", "Shooping Cart");
        helper.setTo(order.getOrderAddress().getEmail());

        msg=msg.replace("[[name]]",order.getOrderAddress().getFirstName());
        msg=msg.replace("[[orderStatus]]",status);
        msg=msg.replace("[[productName]]", order.getProduct().getTitle());
        msg=msg.replace("[[category]]", order.getProduct().getCategory());
        msg=msg.replace("[[quantity]]", order.getQuantity().toString());
        msg=msg.replace("[[price]]", order.getPrice().toString());
        msg=msg.replace("[[paymentType]]", order.getPaymentType());

        helper.setSubject("Product Order Status");
        helper.setText(msg, true);
        mailSender.send(message);
    }

    public UserDetails getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        return userService.getUserByEmail(email);
    }


}