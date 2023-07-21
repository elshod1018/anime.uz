package uz.anime.services;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendHtmlEmail(String toEmail, String smsCode) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("anime.uz@gmail.com"));
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, toEmail);
        mimeMessage.setSubject("Activation code");

        String htmlContent = """
                <h1>Activation code for <a href="https://anime.uz">anime.uz</a>: <b> %s </b></h1>""".formatted(smsCode);
        mimeMessage.setContent(htmlContent, "text/html; charset=utf-8");
        mailSender.send(mimeMessage);
    }
}
