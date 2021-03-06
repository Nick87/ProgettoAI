<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
<s:if test="%{ordiniAperti.size() == 0}">
	<div class="alert_info">Non ci sono nuovi ordini aperti</div>
</s:if>
<s:else>
	<table id="tableOrdiniAperti">
		<thead>
			<tr>
				<th>Ordine</th>
				<th>Data apertura</th>
				<th>Data chiusura</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="ordiniAperti">
				<tr>
					<td>
				    	<s:url action="listaProdottiAcquistabili" var="orderId">
						  	<s:param name="idOrdine"><s:property value="ID_Ordine"/></s:param>
						  	<s:param name="idMembro"><s:property value="%{idMembro}"/></s:param>
						</s:url>
						<a href="${orderId}"><s:property value="ID_Ordine"/></a>
					</td>
					<td><s:date name="data_apertura" format="dd/MM/yyyy"/></td>
					<td><s:date name="data_chiusura" format="dd/MM/yyyy"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>
<script>
$("#tableOrdiniAperti").on("click", "a", function(e){
	e.preventDefault();
	e.stopPropagation();
	var target = $(e.target);
	history.pushState(null, null, target.attr("href"));
	$.get(target.attr("href"), /*params,*/ function(data){
		$("#content").html(data);
	});
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Ordini</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Ordini aperti</a>"
);
</script>
