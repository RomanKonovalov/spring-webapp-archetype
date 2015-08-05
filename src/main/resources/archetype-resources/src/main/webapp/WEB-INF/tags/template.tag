<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<spring:eval expression="@propertyConfigurer.getProperty('admin.role')" var="adminRole" />
<spring:eval expression="@propertyConfigurer.getProperty('user.role')" var="userRole" />
<spring:eval expression="@propertyConfigurer.getProperty('app.availableLanguages')" var="availableLanguages" />


<!DOCTYPE html>

<html lang="en">
<head>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/core.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sticky-footer.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-formhelpers.min.css"/>" />
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap-formhelpers.min.js"/>"></script>
<link rel="icon" href="<c:url value="/resources/images/favicon.ico"/>" />
<title><fmt:message key="webapp.name" /></title>
<title><fmt:message key="home.title" /></title>
<meta name="menu" content="Home" />
</head>
<body>

    <%@ include file="/WEB-INF/common/menu.jsp"%>

    <div class="container" id="content">
        <%@ include file="/WEB-INF/common/messages.jsp"%>
        <div class="row">
            <jsp:doBody />
        </div>
    </div>

    <div id="footer" class="container navbar-fixed-bottom">
        <span class="col-sm-6 text-left"><fmt:message key="webapp.version" />${pom.version} <c:if
                test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status" /> ${pageContext.request.remoteUser}
            </c:if> </span> <span class="col-sm-6 text-right"> &copy; <%=java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)%>
            <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name" /></a>
        </span>
    </div>

</body>
</html>
