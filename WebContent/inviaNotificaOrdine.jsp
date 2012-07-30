<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idMembro" value="%{idMembro}"/>

<s:if test="%{lista_ordini.size() == 0}">
	<h3 class="pageTitle">Non ci sono ordini chiusi da notificare</h3>
</s:if>
<s:else>
	<div id="resultNotificationArea">
	<s:property value="%{strProva}"/>
		<s:actionerror/>
		<s:actionmessage/>
	</div>
	<table id="tabellaOrdiniChiusiNonNotificati">
		<thead>
			<tr>
				<th>Ordine</th>
				<th>Data apertura</th>
				<th>Data chiusura</th>
				<th>Stato</th>
				<th>Invia notifica</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="lista_ordini" status="ordine">
				<tr>
					<td><s:property value="#ordine.index + 1"/></td>
					<td><s:date name="data_apertura" format="dd/MM/yyyy"/></td>
					<td><s:date name="data_chiusura" format="dd/MM/yyyy"/></td>
					<td><s:property value="successo"/></td>
					<td>
						<a class="inviaNotificaOrdine" href="#"></a>
						<input type="hidden" value="<s:property value="ID_Ordine"/>"/>
						<input type="hidden" value="<s:property value="successo"/>"/>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>
<script>
$("#tabellaOrdiniChiusiNonNotificati").click(function(e){
	e.stopPropagation();
	e.preventDefault();
	var target = $(e.target);
	var params = {
		idMembro:$("#idMembro").val(), //e' l'id del responsabile
		idOrdine:target.next().val(),
		successo:target.next().next().val()
	};
	$.get("doInviaNotificaOrdine", params, function(data){
		$("#content").html(data);
	});
});
$("#breadcrumbs_container > article").html(
		"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#'>Gestione notifiche</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Invia notifica</a>"
	);
</script>