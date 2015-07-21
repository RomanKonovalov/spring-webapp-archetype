#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

<div class="col-sm-10">
    <h2><fmt:message key="activeUsers.heading"/></h2>

    <p><fmt:message key="activeUsers.message"/></p>

    <div id="actions" class="form-group">
        <a href="${symbol_dollar}{home}" class="btn btn-primary">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.done"/></a>
    </div>

    <display:table name="users" id="user" cellspacing="0" cellpadding="0"
                   defaultsort="1" class="table table-condensed table-striped table-hover" pagesize="50" requestURI="">
        <display:column property="email" escapeXml="true" style="width: 30%" titleKey="activeUsers.email"
                        sortable="true"/>
        <display:column titleKey="activeUsers.fullName" sortable="true">
            <c:out value="${symbol_dollar}{user.firstName} ${symbol_dollar}{user.lastName}" escapeXml="true"/>
            <c:if test="${symbol_dollar}{not empty user.email}">
                <a href="mailto:<c:out value="${symbol_dollar}{user.email}"/>">
                    <img src="<c:url value="/resources/images/iconEmail.gif"/>"
                         alt="<fmt:message key="activeUsers.icon.email"/>" class="icon"/></a>
            </c:if>
        </display:column>

        <display:setProperty name="paging.banner.item_name" value="user"/>
        <display:setProperty name="paging.banner.items_name" value="users"/>
    </display:table>
</div>
</t:template>
