#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ${package}.model.Role;
import ${package}.services.AccountService;
import ${package}.services.RoleService;
import ${package}.services.message.MessageService;

@Controller
@RequestMapping("/admin")
public class RolesController {

    private static final Logger LOG = LoggerFactory.getLogger(RolesController.class);

    private static final String ROLES_VIEW_NAME = "admin/roles";

    private static final String UPDATE_ROLE_VIEW_NAME = "admin/updateRole";

    private static final String ADD_ROLE_VIEW_NAME = "admin/addRole";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String getAllRoles(final Model model) {

        model.addAttribute("roles", roleService.getAvailableRoles());

        return ROLES_VIEW_NAME;
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public String getRole(final Model model, @RequestParam(required = true) final long id) {

        model.addAttribute("role", roleService.findById(id));

        return UPDATE_ROLE_VIEW_NAME;
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.GET)
    public String getAddRoleForm(final Model model) {

        model.addAttribute("role", new Role());

        return ADD_ROLE_VIEW_NAME;
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public String addRole(@ModelAttribute final Role role, final BindingResult errors, final RedirectAttributes ra) {

        if (StringUtils.isBlank(role.getLabel())) {
            errors.rejectValue("label", "errors.blank", new String[] { "Label" }, "Label required");
            return ADD_ROLE_VIEW_NAME;
        }

        try {
            roleService.addRole(role.getLabel());
        } catch (final EntityExistsException e) {
            errors.rejectValue("label", "addRole.error.exist", new Object[] { role.getLabel() }, "duplicate role");

            return ADD_ROLE_VIEW_NAME;
        }

        messageService.addSuccessAttribute(ra, "{updateRole.create.success}", role.getLabel());

        return "redirect:/" + ROLES_VIEW_NAME;
    }

    @RequestMapping(value = "/role", method = RequestMethod.DELETE)
    public String deleteRole(@RequestParam(required = true) final long id, final RedirectAttributes ra) {

        final Role role = roleService.findById(id);

        roleService.deleteRole(role);

        messageService.addSuccessAttribute(ra, "{updateRole.delete.success}", role.getLabel());

        return "redirect:/" + ROLES_VIEW_NAME;
    }

    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    public String updateRole(@ModelAttribute final Role role, final RedirectAttributes ra,
            @RequestParam(required = false) final String delete) {

        if (delete != null) {
            roleService.deleteRole(role);
            messageService.addSuccessAttribute(ra, "{updateRole.delete.success}", role.getLabel());
            return "redirect:/" + ROLES_VIEW_NAME;
        }

        roleService.updateRole(role);
        messageService.addSuccessAttribute(ra, "{updateRole.update.success}", role.getLabel());

        return "redirect:/" + ROLES_VIEW_NAME;
    }

}
