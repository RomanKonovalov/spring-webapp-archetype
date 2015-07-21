#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import java.beans.PropertyEditorSupport;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ${package}.model.Role;
import ${package}.model.Utility;
import ${package}.services.RoleService;
import ${package}.services.UtilityService;
import ${package}.services.message.MessageService;

@Controller
@RequestMapping("/admin")
public class UtilitiesController {

    private static final String UTILITIES_VIEW_NAME = "admin/utilities";

    private static final String UTILITY_VIEW_NAME = "admin/updateUtility";

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MessageService messageService;

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

    @RequestMapping(value = "/utilities*", method = RequestMethod.GET)
    public String getAllUtilities(final Model model) {

        model.addAttribute("utilities", utilityService.findAllUtilities());
        model.addAttribute("availableRoles", roleService.getAvailableRoles());

        return UTILITIES_VIEW_NAME;
    }

    @RequestMapping(value = "/utilities/deleteRole", method = RequestMethod.POST)
    public String removeRole(@RequestParam(required = true) final long utilityId,
            @RequestParam(required = true) final long roleId) {

        utilityService.removeRole(utilityId, roleId);

        return "redirect:/" + UTILITIES_VIEW_NAME;
    }

    @RequestMapping(value = "/utilities/addRole", method = RequestMethod.POST)
    public String addRole(@RequestParam(required = true) final long utilityId,
            @RequestParam(required = true) final long roleId) {

        utilityService.addRole(utilityId, roleId);

        return "redirect:/" + UTILITIES_VIEW_NAME;
    }

    @RequestMapping(value = "/utility*", method = RequestMethod.GET)
    public String getUtility(final Model model, @RequestParam(required = true) final long id) {

        model.addAttribute("utility", utilityService.findUtilityById(id));
        model.addAttribute("availableRoles", roleService.getAvailableRoles());

        return UTILITY_VIEW_NAME;
    }

    @RequestMapping(value = "/utility*", method = RequestMethod.POST)
    public String updateUtility(@ModelAttribute final Utility utility, final RedirectAttributes ra) {
        utilityService.saveUtility(utility);

        messageService.addSuccessAttribute(ra, "{updateUtility.success}", utility.getName());

        return "redirect:/";
    }

}
