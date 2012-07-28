<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idMembro" value="%{idMembro}"/>
<s:hidden id="numeroMessaggiNonLetti" value="%{numeroMessaggiNonLetti}"/>
<s:if test="%{listaMessaggi.size() == 0}">
	<h3 class="pageTitle">Non ci sono messaggi da visualizzare</h3>
</s:if>
<s:else>
	<div id="resultNotificationArea">
		<s:actionerror/>
		<s:actionmessage/>
	</div>
	<h3 class="pageTitle">Clicca sull'ID di un messaggio per visualizzarlo</h3>
	<table id="tabellaMessaggi">
		<thead>
			<tr>
				<th>Messaggio</th>
				<th>Data</th>
				<th>Letto/Non letto</th>
				<th>Conferma</th>
				<th>Elimina</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="listaMessaggi" status="n" var="l">
			<tr
				<s:if test="%{idMessaggiNonLetti.get(#l.ID_Messaggio)}">
				class="unreadMessaggio"
				</s:if>
			>
				<td><span class="handPointer indexMessaggio"><s:property value="#n.index + 1"/></span></td>
				<td><s:date name="data" format="dd/MM/YYYY"/></td>
				<td>
					<div class="markMessaggioAsReadUnreadDiv clearfix">
						<a class="markMessaggioAsRead" href="markMessaggioAsReadUnread"></a>
						<a class="markMessaggioAsUnread" href="markMessaggioAsReadUnread"></a>
					</div>
					<input type="hidden" value="<s:property value="ID_Messaggio"/>"/>
				</td>
				<td>
					<div class="confermaMessaggioDiv">
						<a class="confermaMessaggio" href="confermaMessaggio"></a>
						<input type="hidden" value="<s:property value="ID_Messaggio"/>"/>
					</div>
				</td>
				<td>
					<div class="eliminaMessaggioDiv">
						<a class="eliminaMessaggio" href="eliminaMessaggio"></a>
						<input type="hidden" value="<s:property value="ID_Messaggio"/>"/>
					</div>
				</td>
			</tr>
			<tr class="hide trTestoMessaggio">
				<td colspan="4"><span class="testoMessaggio"><s:property value="testo"/></span></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
</s:else>
<script>
function updateNumberBubble(numeroNotificheNonLette)
{
	var numberBubble = $("#allCommands #messaggiDiv span.numberBubble");
	numberBubble.html(numeroNotificheNonLette);
	if(numeroNotificheNonLette <= 0)
		numberBubble.hide();
	else
		numberBubble.show();
}
$("#tabellaMessaggi").on("click", function(e){
	e.preventDefault();
	e.stopPropagation();
	var target = $(e.target);
	if(target.is(".indexMessaggio")) {
		$(".trTestoMessaggio:visible").fadeOut("fast");
		var messaggio = $(e.target).closest("tr").next();
		if(messaggio.is(":visible"))
			messaggio.fadeOut("fast");
		else
			messaggio.fadeIn("fast");
	} else if(target.is(".markMessaggioAsRead") || target.is(".markMessaggioAsUnread")) {
		if(target.is(".markMessaggioAsRead"))
			target.closest("tr").removeClass("unreadMessaggio");
		else
			target.closest("tr").addClass("unreadMessaggio");
		var params = {
			idMessaggio:target.closest("td").children("input").eq(0).val(),
			idMembro:$("#idMembro").val(),
			readUnread:target.is(".markMessaggioAsRead") ? "READ" : "UNREAD"
		};
		$.get(target.attr("href"), params, function(data){			
			var obj = JSON.parse(data);
			updateNumberBubble(obj.numeroMessaggiNonLetti);
		});
	} else if(target.is(".confermaMessaggio")) {
		var params = {
			idMessaggio:target.next().val(),
			idMembro:$("#idMembro").val()
		};
		$.get(target.attr("href"), params, function(data){
			var obj = JSON.parse(data);
			if(obj.result == "ok") {
				var div = $("<div>").addClass("alert_success").html("Messaggio confermato con successo");
				$("#resultNotificationArea").empty().append(div);
			}
		});
	} else if(target.is(".eliminaMessaggio")) {
		var params = {
			idMessaggio:target.next().val(),
			idMembro:$("#idMembro").val()
		};
		$.get(target.attr("href"), params, function(data){			
			var obj = JSON.parse(data);
			if(obj.result == "ok") {
				updateNumberBubble(obj.numeroMessaggiNonLetti);
				var trTarget = target.closest("tr");
				trTarget.next().remove();
				trTarget.remove();				
				if($("#tabellaMessaggi tbody tr").length == 0){
					$("#tabellaMessaggi tr").remove();
					$("h3.pageTitle").html("Non ci sono messaggi da visualizzare");
				}
			}
		});
	}
});
updateNumberBubble($("#numeroMessaggiNonLetti").val());
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Messaggi</a>"
);
</script>