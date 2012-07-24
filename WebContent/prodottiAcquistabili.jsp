<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:actionerror/>
<s:form id="listaProdottiForm" action="confermaSchedaAcquisto" method="post">
			<table id="listaProdotti">
				<tbody>
					<thead>
						<tr>
							<th>Nome</th>
							<th>Categoria</th>
							<th>Descrizione</th>
							<th>Costo Unitario</th>
							<th>Costo Trasporto</th>
							<th>Qta' minima</th>
							<th>Step</th>
							<th>Fine disponibilita'</th>
							<th>Qta' da ordinare</th>
							<th>Qta' disponibile</th>
						</tr>
					</thead>
					<tbody>
					    <s:iterator value="listaProdotti" var="p">
							<s:if test="%{disponibilitaProdotti.get(#p.ID_Prodotto) == 0}">
								<tr style="color:red;">
							</s:if>
							<s:else>
								<tr>
							</s:else>
									<td><s:property value="nome"/></td>
									<td><s:property value="categoria"/></td>
									<td><s:property value="descrizione"/></td>
									<td><s:property value="costo_unitario"/></td>
									<td><s:property value="costo_trasporto"/></td>
									<td><s:property value="pezzatura_min_utente"/></td>
									<td><s:property value="step"/></td>
									<td><s:property value="fine_disponibilita"/></td>
									<s:if test="%{disponibilitaProdotti.get(#p.ID_Prodotto) == 0}">
										<td><input type="text" name="<s:property value="ID_Prodotto"/>" disabled="disabled" size="3" value="<s:property value="0"/>"/></td>
										<td>Terminato</td>
									</s:if>
									<s:else>
										<td><input type="text" name="<s:property value="ID_Prodotto"/>" size="3" value="<s:property value="0"/>"/></td>
										<td><s:property value="%{disponibilitaProdotti.get(#p.ID_Prodotto)}"/></td>
									</s:else>
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
			alert("Errore input quantita' (" + params.quantita[key] + ")");
			return;
		}
	}
	$.get("confermaSchedaAcquisto", params, function(data){
		$("#content").html(data);
	});
});
</script>