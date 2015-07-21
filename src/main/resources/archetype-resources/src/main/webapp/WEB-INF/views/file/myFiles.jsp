#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<t:template>
    <div>
        <h2>
            <fmt:message key="myfiles.heading" />
        </h2>

        <div id="actions" class="form-group">
            <a class="btn btn-primary" href="${symbol_dollar}{home}"> <i class="icon-ok icon-white"></i> <fmt:message
                    key="button.done" />
            </a> <a class="btn btn-default" href="upload"> <i class="icon-upload"></i> Upload Another
            </a>
        </div>
        
        <form action="delete" id="formDelete" method="post">
        </form>

        <display:table name="files" cellspacing="0" cellpadding="0" requestURI="" sort="external" partialList="true"
            size="filesCount" defaultsort="1" id="file" pagesize="25"
            class="table table-condensed table-striped table-hover" export="true">
            <display:column property="friendlyName" escapeXml="true" sortable="true" titleKey="myfiles.friendlyName"
                sortName="friendlyName" url="/file/download" paramId="name" paramProperty="name" />
            <display:column property="name" escapeXml="true" sortable="true" titleKey="myfiles.fileName" sortName="name" />
            <display:column property="location" escapeXml="true" titleKey="myfiles.location" />
            <display:column property="size" escapeXml="true" sortable="true" titleKey="myfiles.size" sortName="size" />
            <display:column property="contentType" escapeXml="true" sortable="true" titleKey="myfiles.contentType"
                sortName="contentType" />

            <display:column style="padding-left: 15px" media="html">
                <a class="btn btn-danger" href="${symbol_pound}" onclick="onFormSubmit('formDelete', 'name', '${symbol_dollar}{file.name}');"> <fmt:message
                        key="uploadForm.delete.button" />
                </a>
            </display:column>

            <display:setProperty name="paging.banner.item_name">
                <fmt:message key="myfiles.file" />
            </display:setProperty>
            <display:setProperty name="paging.banner.items_name">
                <fmt:message key="myfiles.files" />
            </display:setProperty>

            <display:setProperty name="export.excel.filename" value="File_List.xls" />
            <display:setProperty name="export.csv.filename" value="File_List.csv" />
        </display:table>

    </div>

</t:template>

<script type="text/javascript">
function addHidden(theForm, key, value) {
    // Create a hidden input element, and append it to the form:
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = key;
    input.value = value;
    theForm.appendChild(input);
}

function onFormSubmit(formName, key, value) {
    var theForm = document.getElementById(formName);
    addHidden(theForm, key, value);
    theForm.submit();
}
</script>


