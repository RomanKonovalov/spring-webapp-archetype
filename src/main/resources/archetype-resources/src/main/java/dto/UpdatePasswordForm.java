#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UpdatePasswordForm {

    @NotBlank(message = "{errors.required}")
    @Email(message = "{errors.email}")
    private String email;

    @Size(min = 6, max = 10, message = "{errors.range}")
    private String currentPassword;

    @Size(min = 6, max = 10, message = "{errors.range}")
    private String password;

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
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * @param currentPassword
     *            the currentPassword to set
     */
    public void setCurrentPassword(final String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

}
