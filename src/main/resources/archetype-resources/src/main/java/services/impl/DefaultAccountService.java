#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ${package}.model.Account;
import ${package}.model.Role;
import ${package}.repository.AccountRepository;
import ${package}.services.AccountService;
import ${package}.services.RoleService;
import ${package}.services.security.SecurityService;
import com.google.common.base.Stopwatch;

@Service
@Transactional
public class DefaultAccountService implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAccountService.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public Account saveNewAccount(Account account) {

        LOG.debug("Saving new account: {}.", account);

        final String encodedPassword = new BCryptPasswordEncoder().encode(account.getPassword());

        account.setPassword(encodedPassword);

        account.setRoles(Collections.singleton(roleService.getUser()));

        account.setEnabled(true);

        try {
            account = accountRepository.save(account);
        } catch (final DataIntegrityViolationException exception) {
            throw new EntityExistsException(exception.getLocalizedMessage());
        }
        return account;
    }

    public void signin(final Account account) {
        LOG.debug("Signing in account: {}.", account);
        SecurityContextHolder.getContext().setAuthentication(authenticate(account));
    }

    private Authentication authenticate(final Account account) {
        return new UsernamePasswordAuthenticationToken(createUserDetails(account), null, buildAuthorities(account));
    }

    public User createUserDetails(final Account account) {
        final User userDetails = new User(account.getEmail(), account.getPassword(), account.isEnabled(),
                account.isAccountNonExpired(), account.isCredentialsNonExpired(), account.isAccountNonLocked(),
                buildAuthorities(account));
        return userDetails;
    }

    private Set<GrantedAuthority> buildAuthorities(final Account account) {
        final Set<GrantedAuthority> authorities = new HashSet<>();

        Assert.notNull(account.getRoles(), "Roles list must not be null");

        for (final Role role : account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public Account getCurrentAccount() {
        final String currentAccountName = SecurityContextHolder.getContext().getAuthentication().getName();

        return findAccountByEmail(currentAccountName);
    }

    @Override
    public Account updateAccount(final Account account) {
        LOG.debug("Updating account: {}.", account);

        return accountRepository.save(account);
    }

    @Override
    public Account findAccountByEmail(final String email) {
        return accountRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Can't find account by email: '" + email + "'"));
    }

    @Override
    public void updatePassword(final String email, final String currentPassword, final String password)
            throws AccessDeniedException, BadCredentialsException {
        LOG.debug("Updating password for account with email: {}", email);

        final Account account = getCurrentAccount();

        if (!account.getEmail().equals(email)) {
            throw new AccessDeniedException("Access is denied");
        }

        if (!new BCryptPasswordEncoder().matches(currentPassword, account.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        final String encodedPassword = new BCryptPasswordEncoder().encode(password);

        account.setPassword(encodedPassword);

        accountRepository.save(account);
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public Page<Account> findUsers(final String emailPattern, final Pageable pageable) {
        LOG.debug("Finding all users.");
        final Stopwatch stopwatch = Stopwatch.createStarted();

        final Page<Account> users = accountRepository.findByRolesAndEmailLike(roleService.getUser(), "%" + emailPattern
                + "%", pageable);

        LOG.debug("findAllUsers done in {}.", stopwatch);

        return users;
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public int countUsers(final String emailPattern) {
        return accountRepository.countByRolesAndEmailLike(roleService.getUser(), "%" + emailPattern + "%");
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public Account findAccountById(final Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find account by id: '" + id + "'"));
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public void deleteAccount(final String email) {
        LOG.debug("Deleting account with email: {}", email);

        final Account currentAccount = getCurrentAccount();

        if (currentAccount.getEmail().equals(email)) {
            throw new AccessDeniedException("Access is denied");
        }

        final Account account = findAccountByEmail(email);

        accountRepository.delete(account);
    }

    @Override
    @PreAuthorize("hasRole(@securityService.getAdminRole())")
    public List<Account> findLoggedinAccounts() {
        LOG.debug("Finding all loggedIn users.");
        final List<Object> principals = sessionRegistry.getAllPrincipals();

        final List<Account> accounts = new ArrayList<>();

        for (final Object principal : principals) {
            if (principal instanceof User) {
                accounts.add(findAccountByEmail(((User) principal).getUsername()));
            }
        }
        return accounts;
    }

    @Override
    public boolean isAdmin() {
        return getCurrentAccount().getRoles().contains(roleService.getAdmin());
    }

}
