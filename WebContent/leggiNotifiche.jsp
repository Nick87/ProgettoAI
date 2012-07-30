<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.List, gas.DAO.Notifica, java.text.SimpleDateFormat" %>

<s:hidden id="idMembro" value="%{idMembro}"/>
<s:hidden id="numeroNotificheNonLette" value="%{numeroNotificheNonLette}"/>
<s:if test="%{listaNotifiche.size() == 0}">
	<h3 class="pageTitle">Non ci sono notifiche da visualizzare</h3>
</s:if>
<s:else>
	<h3 class="pageTitle">Clicca sull'ID di una notifica per visualizzarla</h3>
	<table id="tabellaNotifiche">
		<thead>
			<tr>
				<th>Notifica</th>
				<th>Data</th>
				<th>Letta/Non letta</th>
				<th>Elimina</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="listaNotifiche" status="n" var="l">
			<tr
				<s:if test="%{idNotificheNonLette.get(#l.ID_Notifica)}">
				class="unreadNotification"
				</s:if>
			>
				<td>
					<span class="handPointer indexNotifica"><s:property value="#n.index + 1"/></span>
					<input type="hidden" value="<s:property value="ID_Notifica"/>"/>
				</td>
				<td><s:date name="data" format="dd/MM/YYYY"/></td>
				<td>
					<div class="markNotificaAsReadUnreadDiv clearfix">
						<a class="markNotificaAsRead" href="markNotificaAsReadUnread"></a>
						<a class="markNotificaAsUnread" href="markNotificaAsReadUnread"></a>
					</div>
					<input type="hidden" value="<s:property value="ID_Notifica"/>"/>
				</td>
				<td>
					<div class="eliminaNotificaDiv">
						<a class="eliminaNotifica" href="eliminaNotifica"></a>
						<input type="hidden" value="<s:property value="ID_Notifica"/>"/>
					</div>
				</td>
			</tr>
			<tr class="hide trTestoNotifica">
				<td colspan="4"><span class="testoNotifica"><s:property value="testo"/></span></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
</s:else>
<script>
function updateNumberBubble(numeroNotificheNonLette)
{
	var numberBubble = $("#allCommands #notificheDiv span.numberBubble");
	numberBubble.html(numeroNotificheNonLette);
	if(numeroNotificheNonLette <= 0)
		numberBubble.hide();
	else
		numberBubble.show();
}
$("#tabellaNotifiche").on("click", function(e){
	e.preventDefault();
	e.stopPropagation();
	var target = $(e.target);
	if(target.is(".indexNotifica")) {
		$(".trTestoNotifica:visible").fadeOut("fast");
		target.closest("tr").removeClass("unreadNotification");
		var notifica = $(e.target).closest("tr").next();
		if(notifica.is(":visible"))
			notifica.fadeOut("fast");
		else
			notifica.fadeIn("fast");
		var params = {
			idNotifica:target.closest("td").children("input").eq(0).val(),
			idMembro:$("#idMembro").val(),
			readUnread:"READ"
		};
		$.get("markNotificaAsReadUnread", params, function(data){			
			var obj = JSON.parse(data);
			updateNumberBubble(obj.numeroNotificheNonLette);
		});
	} else if(target.is(".markNotificaAsRead") || target.is(".markNotificaAsUnread")) {
		if(target.is(".markNotificaAsRead"))
			target.closest("tr").removeClass("unreadNotification");
		else
			target.closest("tr").addClass("unreadNotification");
		var params = {
			idNotifica:target.closest("td").children("input").eq(0).val(),
			idMembro:$("#idMembro").val(),
			readUnread:target.is(".markNotificaAsRead") ? "READ" : "UNREAD"
		};
		$.get(target.attr("href"), params, function(data){			
			var obj = JSON.parse(data);
			updateNumberBubble(obj.numeroNotificheNonLette);
		});
	} else if(target.is(".eliminaNotifica")) {
		var params = {
			idNotifica:target.next().val(),
			idMembro:$("#idMembro").val()
		};
		$.get(target.attr("href"), params, function(data){			
			var obj = JSON.parse(data);
			if(obj.result == "ok") {
				updateNumberBubble(obj.numeroNotificheNonLette);
				var trTarget = target.closest("tr");
				trTarget.next().remove();
				trTarget.remove();				
				if($("#tabellaNotifiche tbody tr").length == 0){
					$("#tabellaNotifiche tr").remove();
					$("h3.pageTitle").html("Non ci sono notifiche da visualizzare");
				}
			}
		});
	}
});
updateNumberBubble($("#numeroNotificheNonLette").val());
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#'>Gestione notifiche</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Leggi notifiche</a>"
);
</script>