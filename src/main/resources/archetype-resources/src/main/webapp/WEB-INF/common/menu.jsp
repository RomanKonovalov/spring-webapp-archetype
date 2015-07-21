#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<div class="collapse navbar-collapse" id="navbar">
    <ul class="nav navbar-nav">
        <c:if test="${symbol_dollar}{empty pageContext.request.remoteUser}">
            <li class="active"><a href="<c:url value="/account/signin"/>"><fmt:message key="login.title" /></a></li>
        </c:if>

        <security:authorize access="isAuthenticated()">
            <li><a href="<c:url value="/home"/>"><fmt:message key="menu.home" /></a></li>
            <li><a href="<c:url value="/account"/>"><fmt:message key="menu.user" /></a></li>
            <li><a href="<c:url value="/account/myUtilities"/>"><fmt:message key="menu.myutilities" /></a></li>
        </security:authorize>
        
        <security:authorize access="@securityService.hasPermissionForUtility('FileUtility')">
            <li><a href="<c:url value="/file/upload"/>"><fmt:message key="menu.selectFile" /></a></li>
            <li><a href="<c:url value="/file/myFiles"/>"><fmt:message key="menu.myfiles" /></a></li>
        </security:authorize>
        
        <security:authorize access="hasRole('${symbol_dollar}{adminRole}')">
            <li class="dropdown"><a href="${symbol_pound}" class="dropdown-toggle" data-toggle="dropdown" role="button"
                aria-haspopup="true" aria-expanded="false"><fmt:message key="menu.admin" /><span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="<c:url value="/admin/users"/>"><fmt:message key="menu.admin.users" /></a></li>
                    <li><a href="<c:url value="/admin/activeUsers"/>"><fmt:message key="menu.admin.activeUsers" /></a></li>
                    <li><a href="<c:url value="/admin/utilities"/>"><fmt:message key="menu.admin.utilities" /></a></li>
                    <li><a href="<c:url value="/admin/roles"/>"><fmt:message key="menu.admin.roles" /></a></li>
                </ul>
           </li>
        </security:authorize>
        
        <security:authorize access="isAuthenticated()">
            <li><a href="<c:url value="/account/logout"/>"><fmt:message key="user.logout" /></a></li>
        </security:authorize>

    </ul>
</div>

