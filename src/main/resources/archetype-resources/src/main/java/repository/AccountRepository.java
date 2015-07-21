#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.model.Account;
import ${package}.model.Role;

@Repository("accountRepo")
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findById(Long id);

    Page<Account> findByRolesAndEmailLike(Role role, String email, Pageable pageable);

    int countByRolesAndEmailLike(Role role, String email);
}
