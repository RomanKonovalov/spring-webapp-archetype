#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;

import ${package}.model.Account;

public interface AccountService {

    Account saveNewAccount(Account account);

    User createUserDetails(final Account account);

    void signin(final Account account);

    Account getCurrentAccount();

    Account findAccountById(Long id);

    Account findAccountByEmail(String email);

    Account updateAccount(Account account);

    void updatePassword(String email, String currentPassword, String password) throws AccessDeniedException,
            BadCredentialsException;

    Page<Account> findUsers(String emailPattern, Pageable pageable);

    int countUsers(final String emailPattern);

    void deleteAccount(String email);

    List<Account> findLoggedinAccounts();

    boolean isAdmin();

}
