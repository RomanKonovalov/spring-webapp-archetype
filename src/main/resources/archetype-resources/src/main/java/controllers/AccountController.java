#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ${package}.dto.AccountForm;
import ${package}.dto.SignupForm;
import ${package}.dto.UpdatePasswordForm;
import ${package}.model.Account;
import ${package}.model.Role;
import ${package}.populators.Converter;
import ${package}.services.AccountService;
import ${package}.services.RoleService;
import ${package}.services.UtilityService;
import ${package}.services.message.MessageService;
import ${package}.services.security.SecurityService;

@Controller
@RequestMapping("/account*")
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private static final String UPDATE_VIEW_NAME = "account/update";

    private static final String SIGNUP_VIEW_NAME = "account/signup";

    private static final String UPDATE_PASSWORD_VIEW_NAME = "account/updatePassword";

    private static final String MY_UTILITES_VIEW_NAME = "account/myUtilities";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    @Qualifier(value = "signupFormConverter")
    private Converter<SignupForm, Account> accountConverter;

    @Autowired
    @Qualifier(value = "accountFormReverseConverter")
    private Converter<Account, AccountForm> accountFormReverseConverter;

    @Autowired
    @Qualifier(value = "accountFormConverter")
    private Converter<AccountForm, Account> accountFormConverter;

    @InitBinder
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Role.class, null, new PropertyEditorSupport() {
            @Override
            public void setAsText(final String text) {
                final Role role = roleService.findByName(text);
                setValue(role);
            }
        });
    }

    @ModelAttribute(value = "allCountries")
    public List<String> getAllCountries() {
        final String[] locales = Locale.getISOCountries();
        final List<String> countries = new ArrayList<String>();

        for (final String countryCode : locales) {
            countries.add(new Locale("", countryCode).getDisplayCountry());
        }
        return countries;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid @ModelAttribute("form") final AccountForm form, final Errors errors,
            final RedirectAttributes ra, @RequestParam(required = false) final String delete) {

        if (delete != null) {
            LOG.debug("Request to delete account with email: {}", form.getEmail());

            try {
                accountService.deleteAccount(form.getEmail());
                messageService.addSuccessAttribute(ra, "{updateAccount.account.deleted}", form.getEmail());
            } catch (final AccessDeniedException accessDeniedException) {
                messageService.addErrorAttribute(ra, "{updateAccount.account.delete.error}");
            }

            return "redirect:/";
        }

        if (errors.hasErrors()) {
            return UPDATE_VIEW_NAME;
        }

        LOG.debug("Request to update form: {}", form);

        final Account persistedAccount = accountService.findAccountByEmail(form.getEmail());

        final Account account = accountFormConverter.convert(form, persistedAccount);

        accountService.updateAccount(account);
        messageService.addSuccessAttribute(ra, "{updateAccount.account.updated}", form.getEmail());

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUserForm(final Model model, final HttpServletRequest request,
            @RequestParam(required = false) final String id) {

        Account account;

        if (StringUtils.isNotEmpty(id) && StringUtils.isNumeric(id)) {
            account = accountService.findAccountById(Long.parseLong(id));
            model.addAttribute("updateUser", true);
        } else {
            account = accountService.getCurrentAccount();
            model.addAttribute("updateUser", false);
        }

        model.addAttribute("form", accountFormReverseConverter.convert(account, new AccountForm()));

        if (request.isUserInRole(securityService.getAdminRole())) {
            model.addAttribute("availableRoles", roleService.getAvailableRoles());
        }

        return UPDATE_VIEW_NAME;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignupForm(final Model model) {
        model.addAttribute(new SignupForm());
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute final SignupForm signupForm, final Errors errors,
            final HttpServletRequest request, final RedirectAttributes ra) {

        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }

        LOG.debug("Request to signup form: {}", signupForm);

        Account account = accountConverter.convert(signupForm, new Account());

        try {
            account = accountService.saveNewAccount(account);
        } catch (final EntityExistsException e) {
            errors.rejectValue("email", "errors.existing.user", new Object[] { signupForm.getEmail() },
                    "duplicate user");

            return SIGNUP_VIEW_NAME;
        }

        if (request.isUserInRole(securityService.getAdminRole())) {
            messageService.addSuccessAttribute(ra, "{signup.account.added}", signupForm.getEmail());
        } else {
            accountService.signin(account);
            messageService.addSuccessAttribute(ra, "{signup.account.registered}");
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/updatePassword*", method = RequestMethod.GET)
    public ModelAndView getForm(final HttpServletRequest request) {
        final UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
        updatePasswordForm.setEmail(request.getRemoteUser());
        return new ModelAndView(UPDATE_PASSWORD_VIEW_NAME).addObject("updatePasswordForm", updatePasswordForm);
    }

    @RequestMapping(value = "/updatePassword*", method = RequestMethod.POST)
    public String updatePassword(@Valid @ModelAttribute final UpdatePasswordForm updatePasswordForm,
            final Errors errors, final RedirectAttributes ra, final HttpServletRequest request) {
        LOG.debug("Request to update password for account with email: {}", updatePasswordForm.getEmail());

        if (errors.hasErrors()) {
            return UPDATE_PASSWORD_VIEW_NAME;
        }

        if (!request.getRemoteUser().equalsIgnoreCase(updatePasswordForm.getEmail())) {
            messageService.addErrorAttribute(ra, "{updatePassword.invalidAccountEmail}");
            return "redirect:/";
        }

        try {
            accountService.updatePassword(updatePasswordForm.getEmail(), updatePasswordForm.getCurrentPassword(),
                    updatePasswordForm.getPassword());
        } catch (final AccessDeniedException accessDeniedException) {
            messageService.addErrorAttribute(ra, messageService.convertMessage("{updatePassword.invalidAccountEmail}"));
            return "redirect:/";
        } catch (final BadCredentialsException badCredentialsException) {
            errors.rejectValue("currentPassword", "updatePassword.invalidPassword",
                    new Object[] { updatePasswordForm.getCurrentPassword() }, "invalid password");
            return UPDATE_PASSWORD_VIEW_NAME;
        }

        messageService.addSuccessAttribute(ra, "{updatePassword.success}");

        return "redirect:/";
    }

    @RequestMapping(value = "/myUtilities*", method = RequestMethod.GET)
    public String getMyUtilities(final Model model) {
        model.addAttribute("utilities", utilityService.findAccountUtilities());
        return MY_UTILITES_VIEW_NAME;
    }

}
