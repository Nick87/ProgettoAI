<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
<s:if test="%{lista_dati_deleghe.size() == 0}">
	<div class="alert_info">Non ci sono deleghe attive</div>
</s:if>
<s:else>
	<!-- <h3 class="pageTitle">Ordini aperti</h3> -->
<%-- <s:form action="rimuoviDelega" method="post"> --%>
	<table id="delegheattive">
		<thead>
			<tr>
				<th>Ordine</th>
				<th>Delegato</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="lista_dati_deleghe" var="d">
				<tr id="<s:property value="ID_Ordine"/>">
					<td><s:property value="ID_Ordine"/></td>
					<td><s:property value="%{dettagliDelegato.get(#d.ID_Membro_che_ritira)}"/></td>
					<td>
						<s:hidden id="ID_Ordine_%{#d.ID_Ordine}" value="%{#d.ID_Ordine}"/>
<%-- 						<s:hidden id="ID_membro_che_acquista_%{#d.ID_Ordine}" value="%{#d.ID_Membro_che_acquista}"/> --%>
						<s:hidden id="ID_Membro_che_ritira_%{#d.ID_Ordine}" value="%{#d.ID_Membro_che_ritira}"/>
	<%-- 					<td><s:submit method="execute" value="Rimuovi" align="right"/></td> --%>
						<input value="<s:property value="ID_Ordine"/>" class="input_image" type="image" src="images/remove.jpeg" alt="Rimuovi Delega" title="Rimuovi Delega"/>
					</td>
				</tr>
				
			</s:iterator>
		</tbody>
	</table>
<%-- </s:form> --%>
<script>
$(document).ready(function() {	 
	$('.input_image').click(function(e){
	var id_ordine = $(this).attr("value");  
	e.preventDefault();
	var index = $( this ).index( $("a") );
			var params = {
				idMembro:$("#idMembro").val(),
				idOrdine:$("#ID_Ordine_"+id_ordine).val(),
				/*ID_membro_che_acquista:$("#ID_membro_che_acquista_"+id_ordine).val(),*/
				ID_Membro_che_ritira:$("#ID_Membro_che_ritira_"+id_ordine).val()
			};
		$.get("rimuoviDelega", params, function(data){
			$("#content").html(data);
		});            
	           
	
	});
}); //fine DOM
/*$("#rimuoviDelega").on("submit", function(e){
	e.preventDefault();
	var index = $( this ).index( $("a") );
	var params = {
		idOrdine:$("#ID_Ordine").val(),
		ID_membro_che_acquista:$("#ID_membro_che_acquista").val(),
		ID_Membro_che_ritira:$("#ID_Membro_che_ritira").val()
	};
	$.get("rimuoviDelega", params, function(data){
		$("#content").html(data);
	});
});*/
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Deleghe</a>"
);
</script>
</s:else>
