#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import java.util.List;

import ${package}.model.Role;

public interface RoleService {

    List<Role> getAvailableRoles();

    Role getAdmin();

    Role getUser();

    Role findByName(String name);

    Role findById(long id);

    void addRole(String label);

    void deleteRole(Role role);

    void updateRole(Role role);

}
