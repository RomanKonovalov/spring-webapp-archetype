#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>
    <div class="col-sm-6">
        <form method="post" id="loginForm" action="<c:url value='/j_security_check'/>" class="well" autocomplete="off">
            <h2 class="form-signin-heading">
                <fmt:message key="signin.heading" />
            </h2>
            <c:if test="${symbol_dollar}{param.error != null}">
                <div class="alert alert-danger alert-dismissable">
                    <fmt:message key="signin.password.mismatch" />
                </div>
            </c:if>
            <input type="text" name="j_username" id="j_username" class="form-control"
                placeholder='<fmt:message key="signin.label.username"/>' required tabindex="1" /> 
            <input type="password"  class="form-control" name="j_password" id="j_password" tabindex="2"
                placeholder="<fmt:message key="signin.label.password"/>" required /> 
            <div class="checkbox">
            	<label>
               	<input type="checkbox" name="_spring_security_remember_me" id="rememberMe" tabindex="3" /> 
               	<fmt:message key="signin.rememberMe" /> 
            	</label>
            </div>

            <button type="submit" class="btn btn-lg btn-primary btn-block" name="login" tabindex="4">
                <fmt:message key='signin.button.login' />
            </button>
        </form>

        <p>
            <fmt:message key="signin.signup">
                <fmt:param>
                    <c:url value="/account/signup" />
                </fmt:param>
            </fmt:message>
        </p>

    </div>
</t:template>