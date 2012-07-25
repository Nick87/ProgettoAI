<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idOrdine" value="%{idOrdine}"/>
<s:hidden id="idMembro" value="%{idMembro}"/>
<div id="resultNotificationArea">
<s:property value="%{strProva}"/>
	<s:actionerror/>
	<s:actionmessage/>
</div>
<table id="tabellaOrdiniChiusi">
	<thead>
		<tr>
			<th>ID Ordine</th>
			<th>Data apertura</th>
			<th>Data chiusura</th>
			<th>Invia notifica</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><s:property value="%{ordine.ID_Ordine}"/></td>
			<td><s:property value="%{ordine.data_apertura}"/></td>
			<td><s:property value="%{ordine.data_chiusura}"/></td>
			<td><a id="inviaNotificaChiusuraOrdine" href="inviaNotificaChiusuraOrdine"></a></td>
		</tr>
	</tbody>
</table>
<script>
$("#inviaNotificaChiusuraOrdine").click(function(e){
	e.stopPropagation();
	e.preventDefault();
	var target = $(e.target);
	var params = {
		idOrdine:$("#idOrdine").val(),
		idMembro:$("#idMembro").val()
	};
	$.get(target.attr("href"), params, function(data){
		$("#content").html(data);
	});
});
</script>