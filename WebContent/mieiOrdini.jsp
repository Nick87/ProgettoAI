<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tipoOrdini" value="%{tipoOrdini}"/>
<s:if test="%{listaOrdini.size() == 0}">
	<div class="alert_info">Non ci sono ordini <s:property value="%{tipoOrdini}"/></div>
</s:if>
<s:else>
	<!-- <h3 class="pageTitle">Ordini <s:property value="%{tipoOrdini}"/></h3> -->
	<table id="listaOrdini">
		<thead>
			<tr>
				<th>Ordine</th>
				<th>Data apertura</th>
				<th>Data chiusura</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listaOrdini">
				<tr>
					<td>
						<s:url action="dettaglioOrdine" var="dettaglioOrdine">
						  	<s:param name="idOrdine"><s:property value="ID_Ordine"/></s:param>
						  	<s:param name="idMembro"><s:property value="%{idMembro}"/></s:param>
						</s:url>
						<a href="${dettaglioOrdine}"><s:property value="ID_Ordine"/></a>
					</td>
					<td><s:property value="data_apertura"/></td>
					<td><s:property value="data_chiusura"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>
<script>
$("#listaOrdini").on("click", "a", function(e){
	e.preventDefault();
	e.stopPropagation();
	var target = $(e.target);
	history.pushState(null, null, target.attr("href"));
	$.get(target.attr("href"), function(data){
		$("#content").html(data);
	});
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>I miei ordini</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Ordini " + $("#tipoOrdini").val() + "</a>"
);
</script>