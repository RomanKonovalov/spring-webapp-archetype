#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

    <h2>
        <fmt:message key="home.heading" />
    </h2>
    <p>
        <fmt:message key="home.message" />
    </p>

    <ul class="glassList">
        <li><a href="<c:url value='/account'/>"><fmt:message key="menu.user" /></a></li>
        <security:authorize access="@securityService.hasPermissionForUtility('FileUtility')">
            <li><a href="<c:url value="/file/upload"/>"><fmt:message key="menu.selectFile" /></a></li>
            <li><a href="<c:url value="/file/myFiles"/>"><fmt:message key="menu.myfiles" /></a></li>
        </security:authorize>
    </ul>

</t:template>
