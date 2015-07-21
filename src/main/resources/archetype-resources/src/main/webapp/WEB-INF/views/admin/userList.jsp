#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<t:template>

    <c:if test="${symbol_dollar}{not empty searchError}">
        <div class="alert alert-danger alert-dismissable">
            <a href="${symbol_pound}" data-dismiss="alert" class="close">&times;</a>
            <c:out value="${symbol_dollar}{searchError}" />
        </div>
    </c:if>

    <div class="col-sm-10">
        <h2>
            <fmt:message key="userList.heading" />
        </h2>

        <form method="get" action="${symbol_dollar}{home}/admin/users" id="searchForm" class="form-inline">
            <div id="search" class="text-right">
                <span class="col-sm-9"> <input type="text" size="20" name="searchCriteria" id="query"
                    value="${symbol_dollar}{param.searchCriteria}" placeholder="<fmt:message key="userList.search.enterTerms"/>"
                    class="form-control input-sm">
                </span>
                <button id="button.search" class="btn btn-default btn-sm" type="submit">
                    <i class="icon-search"></i>
                    <fmt:message key="button.search" />
                </button>
            </div>
        </form>

        <div id="actions" class="btn-group">
            <a class="btn btn-primary" href="<c:url value='/signup'/>"> <i
                class="icon-plus icon-white"></i> <fmt:message key="userList.button.add" /></a> <a class="btn btn-default"
                href="<c:url value='/home'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
        </div>

        <display:table name="users" cellspacing="0" cellpadding="0" requestURI="" sort="external" partialList="true"
            size="usersCount" defaultsort="1" id="users" pagesize="25"
            class="table table-condensed table-striped table-hover" export="true">
            <display:column property="email" escapeXml="true" sortable="true" titleKey="userList.email"
                style="width: 25%" url="/account" paramId="id" paramProperty="id" sortName="email" />
            <display:column property="fullName" escapeXml="true" sortable="true" titleKey="userList.fullName"
                style="width: 34%" sortName="firstName lastName" />
            <display:column sortProperty="enabled" sortable="true" titleKey="userList.enabled"
                style="width: 16%; padding-left: 15px" media="html" sortName="enabled">
                <input type="checkbox" disabled="disabled" <c:if test="${symbol_dollar}{users.enabled}">checked="checked"</c:if> />
            </display:column>
            <display:column property="enabled" titleKey="userList.enabled" media="csv xml excel pdf" />

            <display:setProperty name="paging.banner.item_name">
                <fmt:message key="userList.user" />
            </display:setProperty>
            <display:setProperty name="paging.banner.items_name">
                <fmt:message key="userList.users" />
            </display:setProperty>

            <display:setProperty name="export.excel.filename" value="User_List.xls" />
            <display:setProperty name="export.csv.filename" value="User_List.csv" />
        </display:table>
    </div>

</t:template>
