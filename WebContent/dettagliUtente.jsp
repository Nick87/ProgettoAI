<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
<s:hidden id="tipoMembro" value="%{tipoMembro}"></s:hidden>
	<table id="dettagliUtente" align="left">
		<thead>
			<tr>
				<th colspan="2">Informazioni Utente</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="info_membro" var="m">
				<tr>
					<td>Id:</td>
					<td><s:property value="ID_Membro"/></td>
				</tr>
				<tr>
					<td>Username:</td>
					<td><s:property value="username"/></td>
				</tr>
				<tr>
					<td>Cognome:</td>
					<td><s:property value="cognome"/></td>
				</tr>	
				<tr>
					<td>Nome:</td>
					<td><s:property value="nome"/></td>
				</tr>
				<tr>
					<td>Eta':</td>
					<td><s:property value="eta"/></td>
				</tr>
				<tr>
					<td>Sesso:</td>
					<td><s:property value="sesso"/></td>
				</tr>
				<tr>
					<td>Data di nascita:</td>
					<td><s:property value="data_nascita"/></td>
				</tr>
				<tr>
					<td>Luogo di nascita:</td>
					<td><s:property value="luogo_nascita"/></td>
				</tr>
				<tr>
					<td>Indirizzo: </td>
					<td><s:property value="indirizzo"/></td>
				</tr>
				<tr>
					<td>Email: </td>
					<td><s:property value="email"/></td>
				</tr>
				<tr>
					<td>Telefono: </td>
					<td><s:property value="telefono"/></td>
				</tr>
				<tr>
					<td>Tipo Membro:</td>
					<td><s:property value="tipo_membro"/></td>
				</tr>
				<tr>
					<td>Stato Utente:</td>
					<s:if test="%{#m.abilitato==true}">
 						<td>Abilitato</td>
 						<td><input value="<s:property value="ID_Membro"/>" class="input_image" type="image" src="images/disable_user.png" alt="Disabilita Utente" title="Disabilita Utente"/></td>
						<s:hidden id="operazione_%{#m.ID_Membro}" value="disabilita"/>
						<s:hidden id="id_membro_selezionato_%{#m.ID_Membro}" value="%{#m.ID_Membro}"/>
					</s:if>
					<s:else>
						<td>Disabilitato</td>
						<td><input value="<s:property value="ID_Membro"/>" class="input_image" type="image" src="images/add_user.png" alt="Abilita Utente" title="Abilita Utente"/></td>
						<s:hidden id="operazione_%{#m.ID_Membro}" value="abilita"/>
						<s:hidden id="id_membro_selezionato_%{#m.ID_Membro}" value="%{#m.ID_Membro}"/>
					</s:else>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td>
						<input value="<s:property value="ID_Membro"/>" class="indietro_image" type="image" src="images/indietro1.png" alt="Torna indietro" title="Torna indietro"/>
					</td>
				</tr>	
			</s:iterator>
		</tbody>
	</table>
<script>
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
	$('.indietro_image').click(function(e){
		//var id_membro = $(this).attr("value");  
		e.preventDefault();
		var index = $( this ).index( $("a") );
				var params = {
					idMembro:$("#idMembro").val(),
					tipoMembro:$("#tipoMembro").val()
				};
			$.get("listaUtenti", params, function(data){
				$("#content").html(data);
			});            		           
		});
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Lista Utenti</a><div class='breadcrumb_divider'></div><a href='#'>Dettaglio Membro</a>"
);
</script>
