<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
<s:hidden id="tipoMembro" value="%{tipoMembro}"></s:hidden>
<s:if test="%{lista_utenti.size() == 0}">
	<div class="alert_info">Non ci sono utenti iscritti</div>
</s:if>
<s:else>
<%-- <s:form action="abilita_disabilita_utente" method="post"> --%>
	<table id="listaUtenti">
		<thead>
			<tr>
				<th>Id</th>
				<th>Username</th>
				<th>Cognome</th>
				<th>Nome</th>
				<th>Tipo</th>
				<th>Abilita/Disabilita</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="lista_utenti" var="u">
				<tr>
					<td><s:property value="ID_Membro"/></td>
					<td><s:property value="username"/></td>
					<td><s:property value="cognome"/></td>
					<td><s:property value="nome"/></td>
					<td><s:property value="tipo_membro"/></td>
					<s:if test="%{#u.abilitato==true}">
<%-- 						<td><s:property value="abilitato"/></td> --%>
						<td><input value="<s:property value="ID_Membro"/>" class="input_image" type="image" src="images/disable_user.png" alt="Disabilita Utente" title="Disabilita Utente"/></td>
						<s:hidden id="operazione_%{#u.ID_Membro}" value="disabilita"/>
						<s:hidden id="id_membro_selezionato_%{#u.ID_Membro}" value="%{#u.ID_Membro}"/>
					</s:if>
					<s:else>
						<td><input value="<s:property value="ID_Membro"/>" class="input_image" type="image" src="images/add_user.png" alt="Aggiungi Utente" title="Aggiungi Utente"/></td>
						<s:hidden id="operazione_%{#u.ID_Membro}" value="abilita"/>
						<s:hidden id="id_membro_selezionato_%{#u.ID_Membro}" value="%{#u.ID_Membro}"/>
					</s:else>
				</tr>
			</s:iterator>
		</tbody>
	</table>
<%-- </s:form> --%>
</s:else>
<script>
/*$("#abilita_disabilita_utente").on("submit", function(e){
	e.preventDefault();
	var params = {
			idMembro:$("#idMembro").val(),
			operazione:$("#operazione").val(),
			tipoMembro:$("#tipoMembro").val(),
			id_membro_selezionato:$("#id_membro_selezionato").val()
	};
	$.get("abilita_disabilita_utente", params, function(data){
		$("#content").html(data);
	});
});
*/
$(document).ready(function() {	 
	$('.input_image').click(function(e){
		var id_membro = $(this).attr("value");  
		e.preventDefault();
		var index = $( this ).index( $("a") );
				var params = {
					idMembro:$("#idMembro").val(),
					tipoMembro:$("#tipoMembro").val(),
					operazione:$("#operazione_"+id_membro).val(),					
					id_membro_selezionato:$("#id_membro_selezionato_"+id_membro).val()
				};
			$.get("abilita_disabilita_utente", params, function(data){
				$("#content").html(data);
			});            		           
		});
	});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Lista Utenti</a>"
);
</script>
