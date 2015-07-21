#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.model.Address;

@Repository("addressRepo")
public interface AddressRepository extends JpaRepository<Address, Long> {

}
