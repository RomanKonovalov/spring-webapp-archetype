#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>
    <div>
        <h2>
            <fmt:message key="roles.heading" />
        </h2>

        <div id="actions" class="form-group">
            <a class="btn btn-primary" href="${symbol_dollar}{home}"> <i class="icon-ok icon-white"></i> <fmt:message
                    key="button.done" />
            </a> <a class="btn btn-default" href="addRole"> <i class="icon-upload"></i> <fmt:message
                    key="roles.button.add" />
            </a>
        </div>

        <form:form commandName="role" method="delete" action="role" id="formDelete"></form:form>

        <display:table name="roles" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1" id="role"
            pagesize="25" class="table table-condensed table-striped table-hover" export="true">
            <display:column property="name" escapeXml="true" sortable="true" titleKey="roles.name"
                sortName="friendlyName" url="/admin/role" paramId="id" paramProperty="id" />
            <display:column property="label" escapeXml="true" sortable="true" titleKey="roles.label" sortName="name" />

            <display:column style="padding-left: 15px" media="html">
                <c:if test="${symbol_dollar}{(role.name ne adminRole) and (role.name ne userRole)}">
                    <a class="btn btn-danger" href="${symbol_pound}" onclick="onFormSubmit('formDelete', 'id', '${symbol_dollar}{role.id}');">
                        <fmt:message key="roles.button.delete" />
                    </a>
                </c:if>
            </display:column>

            <display:setProperty name="paging.banner.item_name">
                <fmt:message key="roles.role" />
            </display:setProperty>
            <display:setProperty name="paging.banner.items_name">
                <fmt:message key="roles.roles" />
            </display:setProperty>

            <display:setProperty name="export.excel.filename" value="Role_List.xls" />
            <display:setProperty name="export.csv.filename" value="Role_List.csv" />
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
        var theForm = document.getElementById(formName);
        addHidden(theForm, key, value);
        theForm.submit();
    }
</script>


