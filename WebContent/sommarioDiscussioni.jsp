<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="%{sommarioDiscussioni.size() == 0}">
	<div class="alert_info">Non ci sono discussioni</div>
</s:if>
<s:else>
	<ul id="listaSommarioDiscussioni">
		<s:iterator value="sommarioDiscussioni" var="d">
			<li>
				<s:property value="value"/>
				<input type="hidden" value="<s:property value="key"/>"/>
			</li>
		</s:iterator>
	</ul>
</s:else>
<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#'>Discussioni</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Discussioni esistenti</a>"
);
</script>