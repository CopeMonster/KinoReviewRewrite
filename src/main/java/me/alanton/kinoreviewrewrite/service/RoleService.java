package me.alanton.kinoreviewrewrite.service;

import me.alanton.kinoreviewrewrite.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse getRoleById(Long id);
    RoleResponse getRoleByName(String name);
    List<RoleResponse> getAllRoles();
    RoleResponse saveRole(RoleResponse roleResponse);
    RoleResponse updateRole(Long id, RoleResponse roleResponse);
    void deleteRole(Long id);
    boolean existByName(String name);
}
