#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ${package}.repository.UtilityRepository;
import ${package}.services.AccountService;
import ${package}.services.UtilityService;
import ${package}.services.security.SecurityService;

@Service("securityService")
public class DefaultSecurityService implements SecurityService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UtilityRepository serviceRepository;

    @Autowired
    private UtilityService utilityService;

    @Value(value = "${symbol_dollar}{admin.role}")
    private String adminRole;

    @Value(value = "${symbol_dollar}{admin.role.label}")
    private String adminRoleLbael;

    @Value(value = "${symbol_dollar}{admin.email}")
    private String adminEmail;

    @Value(value = "${symbol_dollar}{admin.password}")
    private String adminPassword;

    @Value(value = "${symbol_dollar}{admin.firstname}")
    private String adminFirstname;

    @Value(value = "${symbol_dollar}{admin.lastname}")
    private String adminLastname;

    @Value(value = "${symbol_dollar}{user.role}")
    private String userRole;

    @Value(value = "${symbol_dollar}{user.role.label}")
    private String userRoleLbael;

    @Override
    public String getAdminRole() {
        return adminRole;
    }

    @Override
    public String getAdminRoleLbael() {
        return adminRoleLbael;
    }

    @Override
    public String getAdminEmail() {
        return adminEmail;
    }

    @Override
    public String getAdminPassword() {
        return adminPassword;
    }

    @Override
    public String getAdminFirstname() {
        return adminFirstname;
    }

    @Override
    public String getAdminLastname() {
        return adminLastname;
    }

    @Override
    public String getUserRole() {
        return userRole;
    }

    @Override
    public String getUserRoleLbael() {
        return userRoleLbael;
    }

    @Override
    public boolean hasPermissionForUtility(final String name) {
        final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
        if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            return false;
        }

        return utilityService.findAccountUtilityByName(name) != null;
    }

}
