#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>>

<t:template>

    <div class="col-sm-4">
        <h2>
            <fmt:message key="updatePassword.heading" />
        </h2>
        <p>
            <fmt:message key="updatePassword.changePassword.message" />
        </p>
    </div>
    <div class="col-sm-6">
        <spring:bind path="updatePasswordForm.*">
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


        <form:form commandName="updatePasswordForm" method="post" action="updatePassword" id="updatePasswordForm"
            autocomplete="off" cssClass="well">

            <spring:bind path="updatePasswordForm.email">
                <div class="form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                    <label for="email" class="control-label"><fmt:message key="updatePassword.email" />:</label>
                    <form:input cssClass="form-control" path="email" id="email" readonly="true" />
                    <form:errors path="email" cssClass="help-block" />
                </div>
            </spring:bind>

            <spring:bind path="updatePasswordForm.currentPassword">
                <div class="form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                    <label for="currentPassword" class="control-label"><fmt:message
                            key="updatePassword.currentPassword.label" />:</label>
                    <form:password cssClass="form-control" path="currentPassword" id="currentPassword"
                        showPassword="true" />
                    <form:errors path="currentPassword" cssClass="help-block" />
                </div>
            </spring:bind>

            <spring:bind path="updatePasswordForm.password">
                <div class="form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                    <label for="currentPassword" class="control-label"><fmt:message
                            key="updatePassword.newPassword.label" />:</label>
                    <form:password cssClass="form-control" path="password" id="password" />
                    <form:errors path="password" cssClass="help-block" />
                </div>
            </spring:bind>

            <div class="form-group">
                <button type="submit" class="btn btn-large btn-primary">
                    <i class="icon-ok icon-white"></i>
                    <fmt:message key='updatePassword.changePasswordButton' />
                </button>
                <a class="btn btn-default" href="${symbol_dollar}{home}"> 
                    <i class="icon-ok icon-white"></i> 
                    <fmt:message key="button.cancel" />
                </a>
            </div>
        </form:form>

    </div>

</t:template>