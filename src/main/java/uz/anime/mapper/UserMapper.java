package uz.anime.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.anime.domains.AuthUser;
import uz.anime.dtos.authuser.UserCreateDTO;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    UserCreateDTO toCreateDto(AuthUser user);

    AuthUser toEntity(UserCreateDTO dto);

//    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
//    void updateUsersProfileFromDTO(UserProfileUpdateDTO dto, @MappingTarget AuthUser user);
}
