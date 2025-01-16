package me.alanton.kinoreviewrewrite.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.kinoreviewrewrite.dto.response.RoleResponse;
import me.alanton.kinoreviewrewrite.entity.Role;
import me.alanton.kinoreviewrewrite.exception.BusinessException;
import me.alanton.kinoreviewrewrite.exception.BusinessExceptionReason;
import me.alanton.kinoreviewrewrite.mapper.RoleMapper;
import me.alanton.kinoreviewrewrite.repository.RoleRepository;
import me.alanton.kinoreviewrewrite.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse getRoleById(Long id) {
        log.info("Fetching role with id: {}", id);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Role with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND);
                });
        log.debug("Fetched role: {}", role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse getRoleByName(String name) {
        log.info("Fetching role with name: {}", name);
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> {
                    log.error("Role with name: {} not found", name);
                    return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND);
                });
        log.debug("Fetched role: {}", role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        log.info("Fetching all roles");
        List<Role> roles = roleRepository.findAll();
        log.debug("Fetched roles: {}", roles);
        return roles.stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse saveRole(RoleResponse roleResponse) {
        log.info("Saving new role: {}", roleResponse.name());
        Role role = Role.builder()
                .name(roleResponse.name())
                .build();

        roleRepository.save(role);
        log.info("Saved new role with id: {}", role.getId());

        return roleResponse;
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id, RoleResponse roleResponse) {
        log.info("Updating role with id: {}", id);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Role with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND);
                });

        role.setName(roleResponse.name());
        roleRepository.save(role);
        log.info("Updated role with id: {}", role.getId());

        return roleResponse;
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        log.info("Deleting role with id: {}", id);
        roleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Role with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND);
                });

        roleRepository.deleteById(id);
        log.info("Deleted role with id: {}", id);
    }

    @Override
    public boolean existByName(String name) {
        log.info("Checking existence of role by name: {}", name);
        return roleRepository.existsByName(name);
    }
}
