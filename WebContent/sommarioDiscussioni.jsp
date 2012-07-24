<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idMembro" value="%{idMembro}"/>
<s:if test="%{sommarioDiscussioni.size() == 0}">
	<div class="alert_info">Non ci sono discussioni</div>
</s:if>
<s:else>
	<h3>Clicca sull'utente per continuare la discussione</h3>
	<table id="sommarioDiscussioni">
		<thead>
			<tr><th>Utente</th></tr>
		</thead>
		<tbody>
			<s:iterator value="sommarioDiscussioni">
				<tr>
					<td>
						<a class="userName" href="showChat"><s:property value="value"/></a>
						<input type="hidden" value="<s:property value="key"/>"/>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>
<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#'>Discussioni</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Discussioni esistenti</a>"
);
$("#sommarioDiscussioni").on("click", "a.userName", function(e){
	e.preventDefault();
	var target = $(e.target);
	var params = {
		idDiscussione:target.next().val(),
		idMittente:$("#idMembro").val()
	};
	$.get("showChat", params, function(data){
		$("#content").html(data);
	});
});
</script>