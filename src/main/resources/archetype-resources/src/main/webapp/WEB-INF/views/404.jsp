#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>

    <p>
        <fmt:message key="404.message">
            <fmt:param>
                <c:url value="/home" />
            </fmt:param>
        </fmt:message>
    </p>
    <p style="text-align: center">
        <a href="http://www.flickr.com/photos/mcginityphoto/6716288117/"
            title="Lake McDonald by McGinityPhoto, on Flickr"> <img
            src="http://farm8.staticflickr.com/7035/6716288117_5d84ab851d_z.jpg" width="640" height="426"
            alt="Lake McDonald" style="margin: 20px; border: 1px solid black"></a>
    </p>
    
</t:template>