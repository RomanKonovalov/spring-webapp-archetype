#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<t:template>
    <div>
        <h2>
            <fmt:message key="myutilities.heading" />
        </h2>

        <div id="actions" class="form-group">
            <a class="btn btn-primary" href="${symbol_dollar}{home}"> <i class="icon-ok icon-white"></i> <fmt:message
                    key="button.done" />
            </a>
        </div>

        <display:table name="utilities" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1" id="utility"
            pagesize="25" class="table table-condensed table-striped table-hover" export="true">
            <display:column property="name" escapeXml="true" sortable="true" titleKey="myutilities.name"
                sortName="name"  />
            <display:column property="description" escapeXml="true" sortable="true" titleKey="myutilities.description" sortName="description" />

            <display:setProperty name="paging.banner.item_name">
                <fmt:message key="myutilities.utility" />
            </display:setProperty>
            <display:setProperty name="paging.banner.items_name">
                <fmt:message key="myutilities.utilities" />
            </display:setProperty>

            <display:setProperty name="export.excel.filename" value="Role_List.xls" />
            <display:setProperty name="export.csv.filename" value="Role_List.csv" />
        </display:table>

    </div>

</t:template>
