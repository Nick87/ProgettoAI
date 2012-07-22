<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="idMittente" id="idMittente" value="%{idMembro}" var="mitt"/>
<div id="sendDiscussioneResultBox"></div>
<table id="listaUtentiDestinatari">
	<thead>
		<tr>
			<th>Nome utente</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="utenti" var="u">
			<tr>
				<td>
					<span class="userName"><s:property value="nome"/></span><br/>
					<div class="textareaMessageDiv">
						<textarea placeholder="Inserisci il testo del messaggio..." rows="3" cols="50"></textarea><br/>
						<button class="inviaMessaggioBtn">Invia</button>
						<input type="hidden" name="idDestinatario" value="<s:property value="ID_Membro"/>"/>
					</div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<script>
$(".textareaMessageDiv").hide();
$("#sendDiscussioneResultBox").on("click", "div", function(e){
	var target = $(e.target);
	var me = $(this);
	target.fadeOut("medium", function(){
		me.empty();
	});
});
$("#listaUtentiDestinatari").on("click", function(e){
	var target = $(e.target);
	if(target.is(".userName"))
		target.closest("td").find(".textareaMessageDiv").slideToggle("medium");
	else if(target.is(".inviaMessaggioBtn"))
	{
		var params = {
			idMittente:$("#idMittente").val(),
			idDestinatario:target.parent().children("input[type=hidden]").attr("value"),
			messageContent:target.parent().children("textarea").val()
		};
		if(params.messageContent == "") return;
		$("#sendDiscussioneResultBox").empty().append($("<div>").addClass("loader"));
		$.get("doInviaDiscussione", params, function(data){
			var res = JSON.parse(data);
			var div = $("<div>").html(res.message);
			if(res.result == "success")
				div.addClass("alert_success");
			else if(res.result == "error")
				div.addClass("alert_error");
			$("#sendDiscussioneResultBox").empty().append(div);
		});
	}
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#'>Discussioni</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Nuova discussione</a>"
);
</script>