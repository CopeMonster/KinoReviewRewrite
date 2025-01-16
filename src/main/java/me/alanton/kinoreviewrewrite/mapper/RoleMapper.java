package me.alanton.kinoreviewrewrite.mapper;

import me.alanton.kinoreviewrewrite.dto.response.RoleResponse;
import me.alanton.kinoreviewrewrite.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);
}
