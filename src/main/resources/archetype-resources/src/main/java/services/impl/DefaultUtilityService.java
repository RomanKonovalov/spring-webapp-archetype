#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ${package}.model.Role;
import ${package}.model.Utility;
import ${package}.repository.RoleRepository;
import ${package}.repository.UtilityRepository;
import ${package}.services.AccountService;
import ${package}.services.UtilityService;

@Service
public class DefaultUtilityService implements UtilityService {

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Set<Utility> findAccountUtilities() {
        final Set<Role> accountRoles = accountService.getCurrentAccount().getRoles();

        return utilityRepository.findUtilityByRoles(accountRoles);
    }

    @Override
    public Utility findAccountUtilityByName(final String name) {
        final Set<Role> accountRoles = accountService.getCurrentAccount().getRoles();

        return utilityRepository.findUtilityByNameAndRoles(name, accountRoles).orElse(null);
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public List<Utility> findAllUtilities() {
        return utilityRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public void removeRole(final long utilityId, final long roleId) {
        final Utility utility = utilityRepository.findUtilityByid(utilityId).get();

        final Role role = roleRepository.findOne(roleId);

        final Set<Role> roles = utility.getRoles();
        roles.remove(role);

        utility.setRoles(roles);

        utilityRepository.save(utility);
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public void addRole(final long utilityId, final long roleId) {
        final Utility utility = findUtilityById(utilityId);

        final Role role = roleRepository.findOne(roleId);

        final Set<Role> roles = utility.getRoles();
        roles.add(role);

        utility.setRoles(roles);

        utilityRepository.save(utility);
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public Utility findUtilityById(final long id) {
        return utilityRepository.findUtilityByid(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find utility by id: " + id));
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public void saveUtility(final Utility utility) {
        utilityRepository.save(utility);
    }

}
