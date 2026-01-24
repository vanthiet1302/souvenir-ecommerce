package vn.edu.nlu.fit.backend.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    // ⚠️ DÙNG APP PASSWORD – KHÔNG DÙNG PASSWORD THƯỜNG
    private static final String FROM_EMAIL = "yourgmail@gmail.com";
    private static final String APP_PASSWORD = "xxxx xxxx xxxx xxxx";

    public static boolean sendOTP(String toEmail, String otp) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "INOLA Support"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("Mã xác thực đặt lại mật khẩu");

            message.setContent(
                    "<h3>INOLA - Xác thực tài khoản</h3>" +
                            "<p>Mã OTP của bạn là:</p>" +
                            "<h2 style='color:#e74c3c'>" + otp + "</h2>" +
                            "<p>Mã có hiệu lực trong 5 phút.</p>",
                    "text/html; charset=UTF-8"
            );

            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
