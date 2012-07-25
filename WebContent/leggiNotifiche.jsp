<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.List, gas.DAO.Notifica, java.text.SimpleDateFormat" %>

<h3 class="pageTitle">Clicca su una notifica per visualizzarla</h3>
<table id="tabellaNotifiche">
	<thead>
		<tr>
			<th>Notifica</th>
			<th>Data</th>
			<th>letta/non letta</th>
		</tr>
	</thead>
	<tbody>
	<s:iterator value="listaNotifiche" status="n">
		<tr>
			<td><span class="handPointer indexNotifica"><s:property value="#n.index + 1"/></span></td>
			<td><s:date name="data" format="dd/MM/YYYY"/></td>
			<td>
				<div class="markAsReadUnreadDiv clearfix">
					<a class="markAsRead" href="markAsRead"></a>
					<a class="markAsUnread" href="markAsUnread"></a>
				</div>
				<input type="hidden" value="<s:property value="ID_Notifica"/>"/>
			</td>
		</tr>
		<tr class="hide">
			<td colspan="3"><span class="testoNotifica"><s:property value="testo"/></span></td>
		</tr>
	</s:iterator>
	</tbody>
</table>
<script>
$("#tabellaNotifiche").on("click", ".indexNotifica", function(e){
	var notifica = $(e.target).closest("tr").next();
	if(notifica.is(":visible"))
		notifica.fadeOut("fast");
	else
		notifica.fadeIn("fast");		
});
</script>