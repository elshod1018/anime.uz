package uz.anime.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class SendSMSEvent {
    private final String email;
    private final String smsCode;
}
