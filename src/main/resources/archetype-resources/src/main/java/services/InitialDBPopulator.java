#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ${package}.model.Account;
import ${package}.model.Role;
import ${package}.model.Utility;
import ${package}.repository.AccountRepository;
import ${package}.repository.RoleRepository;
import ${package}.repository.UtilityRepository;
import ${package}.services.security.SecurityService;

@Component
@Profile("develop")
public class InitialDBPopulator {

    private static final Logger LOG = LoggerFactory.getLogger(InitialDBPopulator.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UtilityRepository utilityRepository;

    @PostConstruct
    public void saveEmbeddedData() {
        saveRoles();

        saveAdmin();
        
        //saveUsers();

        saveUtilities();
    }

    private void saveUtilities() {
        LOG.debug("PostConstruct, saving embedded FileUtility.");

        final Utility utility = utilityRepository.findUtilityByName("FileUtility").orElse(new Utility());

        utility.setName("FileUtility");
        utility.setRoles(Collections.singleton(roleService.getAdmin()));

        utilityRepository.save(utility);
    }

    private void saveAdmin() {
        LOG.debug("PostConstruct, saving embedded admin.");

        final Account admin = accountRepository.findByEmail(securityService.getAdminEmail()).orElse(new Account());
        admin.setEmail(securityService.getAdminEmail());
        admin.setPassword(securityService.getAdminPassword());
        admin.setFirstName(securityService.getAdminFirstname());
        admin.setLastName(securityService.getAdminLastname());
        admin.setEnabled(true);
        admin.setRoles(Collections.singleton(roleService.getAdmin()));
        accountRepository.save(admin);
    }
    
    private void saveUsers() {
        LOG.debug("PostConstruct, saving embedded users.");
        for (int i = 0; i < 100000; i++) {
            final Account user = accountRepository.findByEmail("user@user" + i).orElse(new Account());
            user.setEmail("user@user" + i);
            user.setPassword(securityService.getAdminPassword());
            user.setFirstName(securityService.getAdminFirstname());
            user.setLastName(securityService.getAdminLastname());
            user.setEnabled(true);
            user.setRoles(Collections.singleton(roleService.getUser()));
            accountRepository.save(user);
        }
    }

    private void saveRoles() {
        LOG.debug("PostConstruct, saving embedded roles");

        final Role adminRole = roleRepository.findByName(securityService.getAdminRole()).orElse(new Role());
        adminRole.setName(securityService.getAdminRole());
        adminRole.setLabel(securityService.getAdminRoleLbael());
        roleRepository.save(adminRole);

        final Role user = roleRepository.findByName(securityService.getUserRole()).orElse(new Role());
        user.setName(securityService.getUserRole());
        user.setLabel(securityService.getUserRoleLbael());
        roleRepository.save(user);
    }

}
