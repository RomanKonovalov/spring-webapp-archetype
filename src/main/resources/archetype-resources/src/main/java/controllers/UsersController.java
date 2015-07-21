#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ${package}.services.AccountService;

@Controller
@RequestMapping("/admin")
public class UsersController {

    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

    private static final String USERS_VIEW_NAME = "admin/userList";

    private static final String LOGGEDIN_USERS_VIEW_NAME = "admin/activeUsers";

    private static final int PAGE_SIZE = 25;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/users*", method = RequestMethod.GET)
    public String getAllUsers(final Model model, final HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "") final String searchCriteria) {

        final Pageable pageable = new PageRequest(getPage(request), PAGE_SIZE, getSort(request));

        model.addAttribute("users", accountService.findUsers(searchCriteria, pageable));
        model.addAttribute("usersCount", accountService.countUsers(searchCriteria));

        return USERS_VIEW_NAME;
    }

    @RequestMapping(value = "/activeUsers*", method = RequestMethod.GET)
    public String getLoggedinUsers(final Model model, final HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "") final String searchCriteria) {

        model.addAttribute("users", accountService.findLoggedinAccounts());

        return LOGGEDIN_USERS_VIEW_NAME;
    }

    private int getPage(final HttpServletRequest request) {
        final String pageNumber = request.getParameter(new ParamEncoder("users")
                .encodeParameterName(TableTagParameters.PARAMETER_PAGE));

        return pageNumber != null ? Integer.parseInt(pageNumber) - 1 : 0;
    }

    private Sort getSort(final HttpServletRequest request) {
        final String property = request.getParameter(new ParamEncoder("users")
                .encodeParameterName(TableTagParameters.PARAMETER_SORT));

        final String order = request.getParameter(new ParamEncoder("users")
                .encodeParameterName(TableTagParameters.PARAMETER_ORDER));

        final Direction direction = order == "2" ? Direction.DESC : Direction.ASC;

        return StringUtils.isNotEmpty(property) ? new Sort(direction, property.split(" ")) : null;
    }
}
