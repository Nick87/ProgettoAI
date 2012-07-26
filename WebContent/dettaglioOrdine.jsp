<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- <h3 class="pageTitle">Dettaglio Ordine <s:property value="%{idOrdine}"/></h3> -->
<s:if test="%{listaProdotti.size() == 0}">
	<div class="alert_info">Non ci sono prodotti in quest'ordine</div>
</s:if>
<s:else>
	<div id="resultNotificationArea">
		<s:actionerror/>
		<s:actionmessage/>
	</div>
	<s:form id="listaProdottiForm" action="aggiornaQuantita" method="post">
		<table id="listaProdotti">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Descrizione</th>
					<th>Categoria</th>
					<th>Costo unitario</th>
					<th>Costo trasporto</th>
					<th>Fine disponibilita'</th>
					<th>Pezzatura minima</th>
					<th>Quantita' prenotata</th>
					<th>Disponibilita' totale attuale</th>
				</tr>
			</thead>
			<tbody>
			    <s:iterator value="listaProdotti" var="p">
					<tr>
						<td><s:property value="nome"/></td>
						<td><s:property value="descrizione"/></td>
						<td><s:property value="categoria"/></td>
						<td><s:property value="costo_unitario"/></td>
						<td><s:property value="costo_trasporto"/></td>
						<td><s:date name="fine_disponibilita" format="dd/MM/yyyy"/></td>
						<td><s:property value="pezzatura_min_utente"/></td>
						<s:if test="%{editable == true}">
							<td><input type="text" name="<s:property value="ID_Prodotto"/>" size="3" value="<s:property value="quantita"/>"/></td>
						</s:if>
						<s:else>
							<td><s:property value="quantita"/></td>
						</s:else>
						<td><s:property value="%{disponibilitaProdotti.get(#p.ID_Prodotto)}"/></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<s:hidden id="idOrdine" value="%{idOrdine}"></s:hidden>
		<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
		<s:if test="%{editable == true}">
			<s:submit method="execute" value="Aggiorna" align="left" />
		</s:if>
	</s:form>
</s:else>
<script>
$("#listaProdottiForm").on("submit", function(e){
	e.preventDefault();
	var params = {
		idOrdine:$("#idOrdine").val(),
		idMembro:$("#idMembro").val(),
		quantita:$(this).serializeJSON()
	};
	for(key in params.quantita){
		if(!/^(0|([1-9][0-9]*))$/.test(params.quantita[key])){
			var ul = $("<ul>").addClass("errorMessage");
			var li = $("<li>");
			var span = $("<span>").html("Errore input quantità (" + params.quantita[key] + ")");
			li.append(span);
			ul.append(li);
			$("#resultNotificationArea").empty().append(ul);
			return;
		}
	}
	$.get("aggiornaQuantita", params, function(data){
		$("#content").html(data);
	});
});
</script>