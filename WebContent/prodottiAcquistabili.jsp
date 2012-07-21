<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!-- <h3 class="pageTitle">Prodotti acquistabili</h3> -->
<s:actionerror/>
<s:form id="listaProdottiForm" action="confermaSchedaAcquisto" method="post">
			<table id="listaProdotti">
				<tbody>
					<thead>
						<tr>
			<!-- 				<th>ID Prodotto</th> -->
							<th>Nome</th>
							<th>Categoria</th>
							<th>Descrizione</th>
							<th>Costo Unitario</th>
							<th>Costo Trasporto</th>
							<th>Qtà minima</th>
							<th>Step</th>
			<!-- 				<th>Inizio disponibilità</th> -->
							<th>Fine disponibilità</th>
							<th>Qtà da ordinare</th>
							<th>Qtà disponibile</th>
						</tr>
					</thead>
					<tbody>
					    <s:iterator value="listaProdotti" var="p">
							<tr>
			<%-- 					<td><s:property value="ID_Prodotto"/></td> --%>
								<td><s:property value="nome"/></td>
								<td><s:property value="categoria"/></td>
								<td><s:property value="descrizione"/></td>
								<td><s:property value="costo_unitario"/></td>
								<td><s:property value="costo_trasporto"/></td>
								<td><s:property value="pezzatura_min_utente"/></td>
								<td><s:property value="step"/></td>
			<%-- 					<td><s:property value="inizio_disponibilita"/></td> --%>
								<td><s:property value="fine_disponibilita"/></td>
								<td><input type="text" name="<s:property value="ID_Prodotto"/>" size="3" value="<s:property value=""/>"/></td>
								<td><s:property value="%{disponibilitaProdotti.get(#p.ID_Prodotto)}"/></td>
							</tr>
						</s:iterator>
					</tbody>
				</tbody>
			</table>
			<s:hidden id="idOrdine" value="%{idOrdine}"></s:hidden>
			<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
			<s:submit method="execute" value="Conferma" align="left" />
</s:form>
<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Ordini aperti</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Prodotti acquistabili</a>"
);
$("#listaProdottiForm").on("submit", function(e){
	e.preventDefault();
	var params = {
		idOrdine:$("#idOrdine").val(),
		idMembro:$("#idMembro").val(),
		quantita:$(this).serializeJSON()
	};	
	console.log(params.quantita);
	for(key in params.quantita){
		if(!/^(0|([1-9]+))$/.test(params.quantita[key])){
			alert("Errore input quantità (" + params.quantita[key] + ")");
			return;
		}
	}
	$.get("confermaSchedaAcquisto", params, function(data){
		$("#content").html(data);
	});
});
</script>