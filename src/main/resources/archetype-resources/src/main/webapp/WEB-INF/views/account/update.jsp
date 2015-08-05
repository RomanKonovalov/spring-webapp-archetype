#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

    <div class="col-sm-4">
        <h2>
            <fmt:message key="updateAccount.heading" />
        </h2>
        <c:choose>
            <c:when test="${symbol_dollar}{updateUser}">
                <p>
                    <fmt:message key="updateAccount.admin.message" />
                </p>
            </c:when>
            <c:otherwise>
                <p>
                    <fmt:message key="updateAccount.message" />
                </p>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col-sm-7">
        <spring:bind path="form.*">
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


        <form:form commandName="form" method="put" action="" id="form" autocomplete="off" cssClass="well">

            <div class="row">
                <spring:bind path="form.email">
                    <div class="col-sm-6 form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                        <label for="email" class="control-label"><fmt:message key="updateAccount.email" />:</label>
                        <form:input cssClass="form-control" path="email" id="email" readonly="true" />
                        <form:errors path="email" cssClass="help-block" />
                        <c:if test="${symbol_dollar}{pageContext.request.remoteUser == form.email}">
                            <span class="help-block"> <a href="<c:url value="/account/updatePassword" />"><fmt:message
                                        key='updatePassword.changePasswordLink' /></a>
                            </span>
                        </c:if>
                    </div>
                </spring:bind>
            </div>

            <div class="row">
                <div class="col-sm-6 form-group">
                    <label for="firstName" class="control-label"><fmt:message key="updateAccount.firstName" />:</label>
                    <form:input cssClass="form-control" path="firstName" id="firstName" maxlength="50" />
                </div>
                <div class="col-sm-6 form-group">
                    <label for="lastName" class="control-label"><fmt:message key="updateAccount.lastName" />:</label>
                    <form:input cssClass="form-control" path="lastName" id="lastName" maxlength="50" />
                </div>
            </div>
            <div class="form-group">
                <label for="website" class="control-label"><fmt:message key="updateAccount.website" />:</label>
                <form:input cssClass="form-control" path="website" id="website" />
            </div>

            <div>
                <legend class="accordion-heading">
                    <a data-toggle="collapse" href="${symbol_pound}collapse-address"><fmt:message key="updateAccount.address.address" /></a>
                </legend>
                <div id="collapse-address" class="accordion-body collapse">
                    
                    <div class="form-group">
                        <label for="address.country" class="control-label"><fmt:message
                                key="signup.address.country" />:</label>
                        <c:set value="${symbol_dollar}{empty form.address.country ? pageContext.request.locale.country : form.address.country}" var="country"/>
                        <form:select id="countries" path="address.country" cssClass="form-control input-medium bfh-countries" data-country="${symbol_dollar}{country}"/>
                    </div>
                    <div class="form-group">
                        <label for="address.province" class="control-label"><fmt:message
                                key="signup.address.province" />:</label>
                        <form:select path="address.province" cssClass="form-control input-medium bfh-states" data-country="countries" data-state="${symbol_dollar}{form.address.province}" />
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

            <security:authorize access="hasRole('${symbol_dollar}{adminRole}')">
                <div class="form-group">
                    <label class="control-label" for="accountSettings"><fmt:message
                            key="updateAccount.accountSettings" /></label>

                    <div class="form-group" id="accountSettings">
                        <label class="checkbox-inline"> <form:checkbox path="enabled" id="enabled" /> <fmt:message
                                key="updateAccount.enabled" />
                        </label> <label class="checkbox-inline"> <form:checkbox path="accountExpired"
                                id="accountExpired" /> <fmt:message key="updateAccount.accountExpired" />
                        </label> <label class="checkbox-inline"> <form:checkbox path="accountLocked" id="accountLocked" />
                            <fmt:message key="updateAccount.accountLocked" />
                        </label> <label class="checkbox-inline"> <form:checkbox path="credentialsExpired"
                                id="credentialsExpired" /> <fmt:message key="updateAccount.credentialsExpired" />
                        </label>
                    </div>

                </div>
                <div class="form-group">
                    <label for="userRoles" class="control-label"><fmt:message key="updateAccount.assignRoles" /></label>
                    <form:select path="roles" cssClass="form-control">
                        <c:forEach items="${symbol_dollar}{availableRoles}" var="role">
                            <option value="${symbol_dollar}{role.name}" ${symbol_dollar}{fn:contains(form.roles, role) ? 'selected' : ''}>${symbol_dollar}{role.label}</option>
                        </c:forEach>
                    </form:select>
                </div>
            </security:authorize>

            <security:authorize access="hasRole('${symbol_dollar}{userRole}')">
                <div class="form-group">
                    <label class="control-label"><fmt:message key="updateAccount.assignRoles" />:</label>
                    <div class="readonly">
                        <c:forEach var="role" items="${symbol_dollar}{form.roles}" varStatus="status">
                            <c:out value="${symbol_dollar}{role.label}" />
                            <c:if test="${symbol_dollar}{!status.last}">,</c:if>
                        </c:forEach>
                    </div>
                </div>
            </security:authorize>

            <div class="form-group">
                <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
                    <i class="icon-ok icon-white"></i>
                    <fmt:message key="button.save" />
                </button>

                <c:if test="${symbol_dollar}{updateUser and param.method != 'Add'}">
                    <button type="submit" class="btn btn-default" name="delete"
                        onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
                        <i class="icon-trash"></i>
                        <fmt:message key="button.delete" />
                    </button>
                </c:if>

                <a class="btn btn-default" href="${symbol_dollar}{home}"> <i class="icon-ok icon-white"></i> <fmt:message
                        key="button.cancel" />
                </a>

            </div>
        </form:form>
    </div>

</t:template>