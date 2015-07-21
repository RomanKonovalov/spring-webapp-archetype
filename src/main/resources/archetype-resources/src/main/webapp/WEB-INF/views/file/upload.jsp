#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>>

<t:template>

    <div class="col-sm-4">
        <h2>
            <fmt:message key="upload.heading" />
        </h2>
        <p>
            <spring:message code="upload.message" arguments="${symbol_dollar}{maxFileSize}"
       />
        </p>
    </div>
    <div class="col-sm-7">
        <spring:bind path="fileUploadForm.*">
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

        <form:form commandName="fileUploadForm" method="post" action="upload" enctype="multipart/form-data"
            id="uploadForm" cssClass="well" acceptcharset="UTF-8">
            <spring:bind path="fileUploadForm.name">
                <div class="form-group">
                    <label for="name" class="control-label"><fmt:message key="uploadForm.name" />:</label>
                    <form:input cssClass="form-control" path="name" id="name" />
                </div>
            </spring:bind>
            <spring:bind path="fileUploadForm.file">
                <div class="form-group${symbol_dollar}{(not empty status.errorMessage) ? ' has-error' : ''}">
                    <label for="file" class="control-label"><fmt:message key="uploadForm.file" />:</label> 
                    <form:input type="file" path="file" id="file" />
                    <form:errors path="file" cssClass="help-block" />
                </div>
            </spring:bind>
            <div class="form-group">
                <button type="submit" name="upload" class="btn btn-primary" onclick="bCancel=false">
                    <i class="icon-upload icon-white"></i>
                    <fmt:message key="button.upload" />
                </button>
                <a class="btn btn-default" href="${symbol_dollar}{home}"> <i class="icon-ok icon-white"></i> <fmt:message
                        key="button.cancel" />
                </a>
            </div>
        </form:form>
    </div>

</t:template>