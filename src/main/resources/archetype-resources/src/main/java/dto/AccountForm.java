#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import ${package}.model.Role;

public class AccountForm {

    private String firstName;
    private String lastName;

    @NotBlank(message = "{errors.required}")
    @Email(message = "{errors.email}")
    private String email;

    private String phoneNumber;
    private String website;
    private AddressForm address;

    private boolean enabled;

    private Set<Role> roles;

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialsExpired;

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     *            the website to set
     */
    public void setWebsite(final String website) {
        this.website = website;
    }

    /**
     * @return the address
     */
    public AddressForm getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(final AddressForm address) {
        this.address = address;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return the accountExpired
     */
    public boolean isAccountExpired() {
        return accountExpired;
    }

    /**
     * @param accountExpired
     *            the accountExpired to set
     */
    public void setAccountExpired(final boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    /**
     * @return the accountLocked
     */
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @param accountLocked
     *            the accountLocked to set
     */
    public void setAccountLocked(final boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    /**
     * @return the credentialsExpired
     */
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @param credentialsExpired
     *            the credentialsExpired to set
     */
    public void setCredentialsExpired(final boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object${symbol_pound}toString()
     */
    @Override
    public String toString() {
        return "AccountForm [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber="
                + phoneNumber + ", website=" + website + ", address=" + address + ", enabled=" + enabled + ", roles="
                + roles + ", accountExpired=" + accountExpired + ", accountLocked=" + accountLocked
                + ", credentialsExpired=" + credentialsExpired + "]";
    }

}
