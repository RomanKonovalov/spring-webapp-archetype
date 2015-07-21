#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

    <div class="col-sm-4">
        <h2>
            <fmt:message key="updateRole.heading" />
        </h2>
        <p>
            <fmt:message key="updateRole.message" />
        </p>
    </div>
    <div class="col-sm-6">

        <form:form commandName="role" method="put" action="role" id="role" autocomplete="off"
            cssClass="well">
            
            <form:hidden path="id"/>

            <div class="form-group">
                <label for="name" class="control-label"><fmt:message key="updateRole.name" />:</label>
                <form:input cssClass="form-control" path="name" id="name" readonly="true" />
            </div>


            <div class="form-group">
                <label for="label" class="control-label"><fmt:message key="updateRole.label" />:</label>
                <form:input cssClass="form-control" path="label" id="label" />
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-large btn-primary">
                    <fmt:message key='updateRole.submitButton' />
                </button>
                <c:if test="${symbol_dollar}{(role.name ne adminRole) and (role.name ne userRole)}">
                <button type="submit" class="btn btn-large btn-danger" name="delete">
                    <fmt:message key='updateRole.deleteButton' />
                </button>
                </c:if>
                <a class="btn btn-default" href="<c:url value="roles"/>"> 
                    <fmt:message key="button.cancel" />
                </a>
            </div>
        </form:form>

    </div>

</t:template>