#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

    <div class="col-sm-4">
        <h2>
            <fmt:message key="signup.heading" />
        </h2>
        <p>
            <fmt:message key="signup.message" />
        </p>
    </div>
    <div class="col-sm-7">
        <spring:bind path="signupForm.*">
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

        <form:form commandName="signupForm" method="post" action="signup" id="signupForm" autocomplete="off"
            cssClass="well">
            <div class="row">
                <spring:bind path="signupForm.firstName">
                    <div class="col-sm-6 form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                        <label for="firstName" class="control-label"><fmt:message key="signup.firstName" />:</label>
                        <form:input cssClass="form-control" path="firstName" id="firstName" maxlength="50" />
                        <form:errors path="firstName" cssClass="help-block" />
                    </div>
                </spring:bind>
                <spring:bind path="signupForm.lastName">
                    <div class="col-sm-6 form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                        <label for="lastName" class="control-label"><fmt:message key="signup.lastName" />:</label>
                        <form:input cssClass="form-control" path="lastName" id="lastName" maxlength="50" />
                        <form:errors path="lastName" cssClass="help-block" />
                    </div>
                </spring:bind>
            </div>
            <div class="row">
                <spring:bind path="signupForm.password">
                    <div class="col-sm-6 form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                        <label for="password" class="control-label"><fmt:message key="signup.password" />:</label>
                        <form:password cssClass="form-control" path="password" id="password" showPassword="true" />
                        <form:errors path="password" cssClass="help-block" />
                    </div>
                </spring:bind>
                <spring:bind path="signupForm.confirmPassword">
                    <div class="col-sm-6 form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                        <label for="confirmPassword" class="control-label"><fmt:message
                                key="signup.confirmPassword" />:</label>
                        <form:password cssClass="form-control" path="confirmPassword" id="confirmPassword" />
                        <form:errors path="confirmPassword" cssClass="help-block" />
                    </div>
                </spring:bind>
            </div>
            <div class="row">
                <spring:bind path="signupForm.email">
                    <div class="col-sm-6 form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                        <label for="email" class="control-label"><fmt:message key="signup.email" />:</label>
                        <form:input cssClass="form-control" path="email" id="email" />
                        <form:errors path="email" cssClass="help-block" />
                    </div>
                </spring:bind>
            </div>
            <div class="form-group">
                <label for="website" class="control-label"><fmt:message key="signup.website" />:</label>
                <form:input cssClass="form-control" path="website" id="website" />
            </div>
            <div>
                <legend class="accordion-heading">
                    <a data-toggle="collapse" href="${symbol_pound}collapse-address"><fmt:message key="signup.address.address" /></a>
                </legend>
                <div id="collapse-address" class="accordion-body collapse">
                    <div class="form-group">
                        <label for="address.country" class="control-label"><fmt:message
                                key="signup.address.country" />:</label>
                        <form:select id="countries" path="address.country" cssClass="form-control input-medium bfh-countries" data-country="${symbol_dollar}{pageContext.request.locale.country}" />
                    </div>
                    <div class="form-group">
                        <label for="address.province" class="control-label"><fmt:message
                                key="signup.address.province" />:</label>
                        <%-- <form:input cssClass="form-control" path="address.province" id="address.province" /> --%>
                        <form:select path="address.province" cssClass="form-control input-medium bfh-states" data-country="countries" />
                    </div>
                    <div class="row">
                        <div class="col-sm-7 form-group">
                            <label for="address.city" class="control-label"><fmt:message
                                    key="signup.address.city" />:</label>
                            <form:input cssClass="form-control" path="address.city" id="address.city" />
                        </div>
                        <div class="col-sm-4 form-group">
                            <label for="address.postalCode" class="control-label"><fmt:message
                                    key="signup.address.postalCode" />:</label>
                            <form:input cssClass="form-control" path="address.postalCode" id="address.postalCode" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address.address" class="control-label"><fmt:message
                                key="signup.address.address" />:</label>
                        <form:input cssClass="form-control" path="address.address" id="address.address" />
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber" class="control-label"><fmt:message key="updateAccount.phoneNumber" />:</label>
                        <form:input path="phoneNumber" type="text" cssClass="form-control input-medium bfh-phone" data-country="countries" id="phoneNumber" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
                    <i class="icon-ok icon-white"></i>
                    <fmt:message key="signup.button.register" />
                </button>
                <a class="btn btn-default" href="${symbol_dollar}{home}"> <i class="icon-ok icon-white"></i> <fmt:message
                        key="button.cancel" />
                </a>
            </div>
        </form:form>
    </div>

</t:template>