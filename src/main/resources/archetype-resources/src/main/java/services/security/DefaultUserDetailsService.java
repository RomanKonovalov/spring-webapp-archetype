#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ${package}.model.Account;
import ${package}.repository.AccountRepository;
import ${package}.services.AccountService;
import ${package}.services.message.MessageService;

@Service("defaultUserDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService userService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(messageService.convertMessage("login.passwordHint.error", email)));

        return userService.createUserDetails(account);
    }

}
