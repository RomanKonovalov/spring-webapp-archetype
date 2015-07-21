#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

    <div class="col-sm-10">
        <h2>
            <fmt:message key="utilities.heading" />
        </h2>

        <p>
            <fmt:message key="utilities.message" />
        </p>

        <div id="actions" class="form-group">
            <a href="${symbol_dollar}{home}" class="btn btn-primary"> <i class="icon-ok icon-white"></i> 
            <fmt:message key="button.done" /></a>
        </div>

        <display:table name="utilities" id="utility" cellspacing="0" cellpadding="0" defaultsort="1"
            class="table table-condensed table-striped table-hover" pagesize="50" requestURI="">
            <display:column property="name" escapeXml="true" style="width: 30%" titleKey="utilities.name" sortable="true" url="/admin/utility" paramId="id" paramProperty="id"/>
            <display:column property="description" escapeXml="true" style="width: 30%" titleKey="utilities.description"
                sortable="true" />
            <display:column titleKey="utilities.roles" media="html" sortable="true">
                <form action="utilities/deleteRole" id="formDelete" method="post">
                    <input type="hidden" name="utilityId" value="${symbol_dollar}{utility.id}" />
                    <c:forEach items="${symbol_dollar}{utility.roles}" var="role">
                        <span class="badge alert-info"> <span>${symbol_dollar}{role.label}</span> 
                            <a href='${symbol_pound}' onclick="onFormSubmit('formDelete', 'roleId', ${symbol_dollar}{role.id});">
                                <i class="glyphicon glyphicon-remove-sign glyphicon-white"></i>
                            </a>
                        </span>
                    </c:forEach>
                </form>
            </display:column>

            <display:column style="padding-left: 15px" media="html">
                <form action="utilities/addRole" id="formAdd" method="post">
                    <input type="hidden" name="utilityId" value="${symbol_dollar}{utility.id}" /> 
                    <select cssClass="form-control" onchange="onFormSubmit('formAdd', 'roleId', value);">
                        <option disabled="disabled" selected="selected"><fmt:message key="utilities.addRole" /></option>
                        <c:forEach items="${symbol_dollar}{availableRoles}" var="role">
                            <c:if test="${symbol_dollar}{!fn:contains( utility.roles, role )}">
                                <option value="${symbol_dollar}{role.id}">${symbol_dollar}{role.label}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </form>
            </display:column>

            <display:setProperty name="paging.banner.item_name"><fmt:message key="utilities.utility" /></display:setProperty>
            <display:setProperty name="paging.banner.items_name"><fmt:message key="utilities.utilities" /></display:setProperty>
        </display:table>

    </div>
</t:template>

<script type="text/javascript">
function addHidden(theForm, key, value) {
    // Create a hidden input element, and append it to the form:
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = key;
    input.value = value;
    theForm.appendChild(input);
}

function onFormSubmit(formName, key, value) {
    console.log('onFormSubmit:', formName, key, value);
    var theForm = document.getElementById(formName);
    addHidden(theForm, key, value);
    theForm.submit();
}
</script>


