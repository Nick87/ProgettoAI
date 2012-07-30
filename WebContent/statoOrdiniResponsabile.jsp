<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idMembro" value="%{idMembro}"/>
<div id="resultNotificationArea">
<s:property value="%{strProva}"/>
	<s:actionerror/>
	<s:actionmessage/>
</div>
<table id="tabellaOrdini">
	<thead>
		<tr>
			<th>ID Ordine</th>
			<th>Data apertura</th>
			<th>Data chiusura</th>
			<th>Notificato</th>
			<th>Successo</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="lista_ordini">
			<tr>
				<td><s:property value="ID_Ordine"/></td>
				<td><s:date name="data_apertura" format="dd/MM/yyyy"/></td>
				<td><s:date name="data_chiusura" format="dd/MM/yyyy"/></td>
				<td>
					<s:property value="notificato"/>
				</td>
				<td>
					<s:property value="successo"/>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Stato ordini</a>"
);
</script>