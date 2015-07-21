#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<c:if test="${symbol_dollar}{not empty message}">
    <div class="alert alert-${symbol_dollar}{message.type} alert-dismissable">
        <a href="${symbol_pound}" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${symbol_dollar}{message.message}"/><br />
    </div>
    <c:remove var="message" scope="session"/>
</c:if>

