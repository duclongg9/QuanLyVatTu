
package DAO;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import Model.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class Email {
    
    final String from = "onggiamdocdan@gmail.com";
    final String passWord = "kano rvkg mrbl ntkp";

    public void sendBookingEmail(String to) {
        new Thread(() -> {
            sendEmail(to);
        }).start();
    }

    private void sendEmail(String to ) {
        Properties pro = new Properties();
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable", "true");

        Authenticator authen = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, passWord);
            }
        };
    
        Session session = Session.getInstance(pro, authen);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject("The Order Has Been Successfully Submitted", "UTF-8");
            msg.setSentDate(new Date());
            msg.setContent(
                    "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;'>"
                            + "<h2 style='color: #007bff; text-align: center;'>Booking Confirmation</h2>"
                            + "<p><strong>Customer:</strong> <span style='color: #333;'>"  + "</span></p>"
                            + "<p><strong>Order Code:</strong> <span style='color: #333; font-weight: bold;'>" + "</span></p>"
                            + "<p><strong>Total Cost:</strong> <span style='color: #28a745; font-size: 18px; font-weight: bold;'>"  + " USD</span></p>"
                            + "<p style='margin-top: 20px;'>"
                            + "Please make the payment at least <strong>10 days</strong> before your flight to secure your booking."
                            + "</p>"
                            + "<div style='text-align: center; margin-top: 30px;'>"
                            + "<a href='https://ve247vn.com/bookingFlightTicketsURL' style='background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;'>Make Payment Now</a>"
                            + "</div>"
                            + "<p style='font-size: 12px; color: #777; text-align: center; margin-top: 20px;'>"
                            + "If you have any questions, please contact our support team."
                            + "</p>"
                            + "</div>",
                    "text/html"
            );
            Transport.send(msg);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

     
    }
    
    public static void main(String[] args) {
        Email test = new Email();
        test.sendBookingEmail("luonghuuminhlc@gmail.com");
    }
    
}
