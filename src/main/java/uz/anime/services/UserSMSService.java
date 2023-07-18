package uz.anime.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import uz.anime.domains.AuthUser;
import uz.anime.domains.UserSMS;
import uz.anime.enums.SMSCodeType;
import uz.anime.evenet_listeners.events.SendSMSEvent;
import uz.anime.repositories.UserSMSRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserSMSService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserSMSRepository userSMSRepository;

    public UserSMS createSMSCode(AuthUser user, SMSCodeType type) {
        Integer userId = user.getId();
        UserSMS userSMS = findByUserId(userId, type);
        if (!Objects.isNull(userSMS)) {
            return userSMS;
        }
        String smsCode = "666666"; /*baseUtils.generateCode()*/
        userSMS = save(userId, smsCode, type);
        applicationEventPublisher.publishEvent(new SendSMSEvent(user.getEmail(), smsCode));
        return userSMS;
    }

    private UserSMS save(Integer userId, String smsCode, SMSCodeType type) {
        UserSMS userSMS = UserSMS.builder()
                .userId(userId)
                .code(smsCode)
                .type(type)
                .build();
        return userSMSRepository.save(userSMS);
    }

    public UserSMS findByUserId(Integer userId, SMSCodeType type) {
        return userSMSRepository.findByUserId(userId, type);
    }

    public UserSMS update(UserSMS userSMS) {
        return userSMSRepository.save(userSMS);
    }
}
