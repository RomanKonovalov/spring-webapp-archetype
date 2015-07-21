#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.security;

public interface SecurityService {

    String getAdminRole();

    String getAdminRoleLbael();

    String getAdminEmail();

    String getAdminPassword();

    String getAdminFirstname();

    String getAdminLastname();

    String getUserRole();

    String getUserRoleLbael();

    boolean hasPermissionForUtility(String name);

}
