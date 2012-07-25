<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idOrdine" value="%{idOrdine}"/>
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
			<td>
				<a id="inviaNotificaChiusuraOrdine" href="inviaNotificaChiusuraOrdine">
					<!-- <img src="images/admin/icn_alert_success.png"/> -->
				</a>
			</td>
		</tr>
	</tbody>
</table>
<style>
#inviaNotificaChiusuraOrdine
{
	background-image:url("images/admin/icn_alert_success.png");
	display:block;
	width:18px;
	height:18px;
	margin:0 auto;
}
</style>
<script>
$("#inviaNotificaChiusuraOrdine").click(function(e){
	e.stopPropagation();
	e.preventDefault();
	var target = $(e.target);
	var params = {
		idOrdine:$("#idOrdine").val()	
	};
	$.get(target.attr("href"), params, function(data){
		
	});
});
</script>