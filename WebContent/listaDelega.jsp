<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
<s:if test="%{lista_dati_deleghe.size() == 0}">
	<div class="alert_info">Non ci sono deleghe attive</div>
</s:if>
<s:else>
	<!-- <h3 class="pageTitle">Ordini aperti</h3> -->
<s:form action="rimuoviDelega" method="post">
	<table id="delegheattive">
		<thead>
			<tr>
				<th>Ordine</th>
				<th>Delegato</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="lista_dati_deleghe" var="d">
				<tr>
					<td><s:property value="ID_Ordine"/></td>
					<td><s:property value="%{dettagliDelegato.get(#d.ID_Membro_che_ritira)}"/></td>
					<s:hidden id="ID_Ordine" value="%{#d.ID_Ordine}"/>
					<s:hidden id="ID_membro_che_acquista" value="%{#d.ID_Membro_che_acquista}"/>
					<td><s:submit method="execute" value="Rimuovi" align="right"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:form>
<script>
$("#rimuoviDelega").on("submit", function(e){
	e.preventDefault();
	var params = {
		idOrdine:$("#ID_Ordine").val(),
		ID_membro_che_acquista:$("#ID_membro_che_acquista").val()
	};
	$.get("rimuoviDelega", params, function(data){
		$("#content").html(data);
	});
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Deleghe</a>"
);
</script>
</s:else>
