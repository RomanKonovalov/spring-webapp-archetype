#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="${symbol_pound}bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href=""><fmt:message key="webapp.name" /></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
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
      
      <form class="navbar-form navbar-right">
      	<select class="form-control input-medium bfh-languages" data-language="${symbol_dollar}{pageContext.response.locale}" data-available="${symbol_dollar}{availableLanguages}" onchange="${symbol_dollar}.get('?lang='+this.value);location.reload();"></select>
      </form>

    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>


