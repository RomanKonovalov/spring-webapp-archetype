#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<t:template>

    <div class="col-sm-4">
        <h2>
            <fmt:message key="updateUtility.heading" />
        </h2>
        <p>
            <fmt:message key="updateUtility.message" />
        </p>
    </div>
    <div class="col-sm-6">

        <form:form commandName="utility" method="post" action="utility" id="utility" autocomplete="off"
            cssClass="well">
            
            <form:hidden path="id"/>

            <div class="form-group">
                <label for="name" class="control-label"><fmt:message key="updateUtility.name" />:</label>
                <form:input cssClass="form-control" path="name" id="name" readonly="true" />
            </div>


            <div class="form-group">
                <label for="description" class="control-label"><fmt:message key="updateUtility.description" />:</label>
                <form:input cssClass="form-control" path="description" id="description" />
            </div>

            <div class="form-group">
                <label for="roles" class="control-label"><fmt:message key="updateUtility.assignRoles" /></label>
                <form:select path="roles" cssClass="form-control">
                    <c:forEach items="${symbol_dollar}{availableRoles}" var="role">
                        <option value="${symbol_dollar}{role.name}" ${symbol_dollar}{fn:contains(utility.roles, role) ? 'selected' : ''}>${symbol_dollar}{role.label}</option>
                    </c:forEach>
                </form:select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-large btn-primary">
                    <i class="icon-ok icon-white"></i>
                    <fmt:message key='updateUtility.submitButton' />
                </button>
                <a class="btn btn-default" href="${symbol_dollar}{home}"> 
                    <i class="icon-ok icon-white"></i> 
                    <fmt:message key="button.cancel" />
                </a>
            </div>
        </form:form>

    </div>

</t:template>