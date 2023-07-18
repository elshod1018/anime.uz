package uz.anime.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import uz.anime.evenet_listeners.events.SendSMSEvent;
import uz.anime.services.MailService;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final MailService mailService;

    @Async
    @EventListener(value = SendSMSEvent.class)
    public void sendSMSEventListener(SendSMSEvent event) {
        String email = event.getEmail();
        String smsCode = event.getSmsCode();
        if (!Objects.isNull(email) && !Objects.isNull(smsCode)) {
            log.info("Sms send to {}", email);
            mailService.sendHtmlEmail(email, smsCode);
        }
    }
}
