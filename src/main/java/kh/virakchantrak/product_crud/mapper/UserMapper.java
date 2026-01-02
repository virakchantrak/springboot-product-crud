package kh.virakchantrak.product_crud.mapper;

import kh.virakchantrak.product_crud.dto.response.UserResponseDTO;
import kh.virakchantrak.product_crud.entity.RoleEntity;
import kh.virakchantrak.product_crud.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDTO toResponse(UserEntity user);
    default String map(RoleEntity role) {
        return role.getName();
    }
}
