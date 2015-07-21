#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

    <div class="col-sm-4">
        <h2>
            <fmt:message key="addRole.heading" />
        </h2>
        <p>
            <fmt:message key="addRole.message" />
        </p>
    </div>
    <div class="col-sm-6">
        <spring:bind path="role.*">
            <c:if test="${symbol_dollar}{not empty status.errorMessages}">
                <div class="alert alert-danger alert-dismissable">
                    <a href="${symbol_pound}" data-dismiss="alert" class="close">&times;</a>
                    <c:forEach var="error" items="${symbol_dollar}{status.errorMessages}">
                        <c:out value="${symbol_dollar}{error}" escapeXml="false" />
                        <br />
                    </c:forEach>
                </div>
            </c:if>
        </spring:bind>

        <form:form commandName="role" method="post" action="role" id="role" autocomplete="off" cssClass="well">

            <form:hidden path="id" />

            <div class="form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                <label for="label" class="control-label"><fmt:message key="addRole.label" />:</label>
                <form:input cssClass="form-control" path="label" id="label" />
                <form:errors path="label" cssClass="help-block" />
            </div>


            <div class="form-group">
                <button type="submit" class="btn btn-large btn-primary">
                    <i class="icon-ok icon-white"></i>
                    <fmt:message key='addRole.submitButton' />
                </button>
                <a class="btn btn-default" href="<c:url value="roles"/>"> 
                    <fmt:message key="button.cancel" />
                </a>
            </div>
        </form:form>

    </div>

</t:template>