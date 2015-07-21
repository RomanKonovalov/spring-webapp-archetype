#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.model.Role;
import ${package}.repository.RoleRepository;
import ${package}.services.RoleService;
import ${package}.services.security.SecurityService;
import com.google.common.base.CaseFormat;

@Service
@Transactional
public class DefaultRoleService implements RoleService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRoleService.class);

    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public List<Role> getAvailableRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getAdmin() {
        return findByName(securityService.getAdminRole());
    }

    @Override
    public Role getUser() {
        return findByName(securityService.getUserRole());
    }

    @Override
    public Role findByName(final String name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Can't find role: " + name));
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public Role findById(final long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find role by id: " + id));
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public void addRole(final String label) {

        final String name = ROLE_PREFIX
                + CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, WordUtils.capitalize(label).trim());

        final Role role = new Role();

        role.setName(name);
        role.setLabel(label);

        try {
            roleRepository.save(role);
        } catch (final DataIntegrityViolationException exception) {
            throw new EntityExistsException(exception.getLocalizedMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public void deleteRole(final Role role) {

        if (role.equals(getAdmin()) || role.equals(getUser())) {
            throw new IllegalArgumentException("You can't delete embedded roles");
        }

        roleRepository.delete(role);
    }

    @Override
    public void updateRole(final Role role) {
        roleRepository.save(role);
    }

}
