#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import java.util.List;
import java.util.Set;

import ${package}.model.Utility;

public interface UtilityService {

    Set<Utility> findAccountUtilities();

    Utility findAccountUtilityByName(String name);

    List<Utility> findAllUtilities();

    void removeRole(long utilityId, long roleId);

    void addRole(long utilityId, long roleId);

    Utility findUtilityById(long id);

    void saveUtility(Utility utility);

}
