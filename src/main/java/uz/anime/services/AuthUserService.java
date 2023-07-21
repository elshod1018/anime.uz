package uz.anime.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.anime.config.security.JwtService;
import uz.anime.domains.AuthUser;
import uz.anime.domains.UserSMS;
import uz.anime.dtos.authuser.*;
import uz.anime.enums.SMSCodeType;
import uz.anime.enums.Status;
import uz.anime.enums.TokenType;
import uz.anime.repositories.AuthUserRepository;

import java.util.Objects;

import static uz.anime.mapper.UserMapper.USER_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final JwtService jwtService;
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserSMSService userSMSService;

    public AuthUser create(@NotNull UserCreateDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Password and ConfirmPassword must be the same");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        AuthUser authUser = USER_MAPPER.toEntity(dto);
        authUser = authUserRepository.save(authUser);
        userSMSService.createSMSCode(authUser, SMSCodeType.ACTIVATION);
        return authUser;
    }

    public AuthUser findByUsername(String username) {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username '%s' not found".formatted(username)));
    }

    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) {
        String username = tokenRequest.username();
        String password = tokenRequest.password();
        findByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtService.generateToken(username);
    }

    public TokenResponse refreshAccessToken(@NonNull RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        if (!jwtService.isValidToken(refreshToken, TokenType.REFRESH))
            throw new CredentialsExpiredException("Token is invalid");

        String username = jwtService.getUsername(refreshToken, TokenType.REFRESH);
        authUserRepository.findByUsername(username);
        TokenResponse tokenResponse = TokenResponse.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiry(jwtService.getExpiry(refreshToken, TokenType.REFRESH))
                .build();
        return jwtService.generateAccessToken(username, tokenResponse);
    }

    public void activate(UserActivationDTO dto) {
        AuthUser authUser = findByEmail(dto.email());
        UserSMS userSMS = userSMSService.findByUserId(authUser.getId(), SMSCodeType.ACTIVATION);
        if (!Objects.isNull(userSMS) && userSMS.getCode().equals(dto.code())) {
            authUser.setStatus(Status.ACTIVE);
            authUserRepository.save(authUser);
            userSMS.setExpired(true);
            userSMSService.update(userSMS);
        }
        throw new RuntimeException("Code is invalid");
    }

    public AuthUser findByEmail(String email) {
        return authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email '%s' not found".formatted(email)));
    }

    public boolean exist(String username) {
        return authUserRepository.existsByUsername(username);
    }

    public void resendCode(String email, SMSCodeType smsCodeType) {
        userSMSService.createSMSCode(findByEmail(email), smsCodeType);
    }
}
