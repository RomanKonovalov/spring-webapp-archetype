#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.model.Role;
import ${package}.model.Utility;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Long> {

    Optional<Utility> findUtilityByName(String name);

    Optional<Utility> findUtilityByid(long id);

    Optional<Utility> findUtilityByNameAndRoles(String name, Set<Role> roles);

    Set<Utility> findUtilityByRoles(Set<Role> roles);

}
