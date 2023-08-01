package uz.anime.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.anime.domains.AuthUser;
import uz.anime.dtos.authuser.UserCreateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-31T12:33:35+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Private Build)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserCreateDTO toCreateDto(AuthUser user) {
        if ( user == null ) {
            return null;
        }

        UserCreateDTO.UserCreateDTOBuilder userCreateDTO = UserCreateDTO.builder();

        userCreateDTO.username( user.getUsername() );
        userCreateDTO.email( user.getEmail() );
        userCreateDTO.password( user.getPassword() );

        return userCreateDTO.build();
    }

    @Override
    public AuthUser toEntity(UserCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AuthUser.AuthUserBuilder authUser = AuthUser.childBuilder();

        authUser.username( dto.getUsername() );
        authUser.password( dto.getPassword() );
        authUser.email( dto.getEmail() );

        return authUser.build();
    }
}
