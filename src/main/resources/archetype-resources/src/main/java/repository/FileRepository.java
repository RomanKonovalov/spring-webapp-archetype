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
import ${package}.model.File;

@Repository("fileRepo")
public interface FileRepository extends JpaRepository<File, Long> {

    Page<File> findByAccount(Account account, Pageable pageable);

    int countByAccount(Account account);

    Optional<File> findByNameAndAccount(String name, Account account);
}
