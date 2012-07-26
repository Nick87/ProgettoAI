<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idDiscussione" value="%{idDiscussione}"/>
<s:hidden id="idMittente" value="%{idMittente}"/>
<s:hidden id="idDestinatario" value="%{idDestinatario}"/>
<s:hidden id="usernameMittente" value="%{usernameMittente}"/>
<s:hidden id="usernameDestinatario" value="%{usernameDestinatario}"/>
<div id="chatWrapper">
	<div id="messagesArea">
		<ul id="messageList">
			<s:iterator value="chat">
				<li>
					<input type="hidden" class="idMessaggioDiscussione" value="<s:property value="ID_Messaggio_Discussione"/>"/>
					<div class="dateSenderDiv">
						<span class="dateSpan"><s:date name="timestamp" format="dd/MM/yyyy - kk:mm:ss"/></span><span class="senderSpan"><s:property value="%{mapIdMembroUsername.get(ID_Membro_Mittente)}"/></span>
					</div>
					<div class="contentMessageDiv"><pre class="innerText"><s:property value="testo"/></pre></div>
				</li>
			</s:iterator>
		</ul>
	</div>
	<div id="textareaDiv" class="clearfix">
		<textarea id="textareaMessage"></textarea>
		<button id="sendMessageBtn">Invia</button>
	</div>

<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#'>Discussioni</a><div class='breadcrumb_divider'></div><a href='#' class='current'>" + $("#usernameDestinatario").val() + "</a>"
);
$("#messagesArea").animate({ scrollTop:$("#messagesArea")[0].scrollHeight-$('#messagesArea').height() }, 300);
$("#sendMessageBtn").click(function(){
	var messageContent = $("#textareaMessage").val();
	if(messageContent == "") return;
	var params = {
		idMittente:$("#idMittente").val(),
		idDestinatario:$("#idDestinatario").val(),
		messageContent:messageContent.trim()
	};
	$.get("doInviaDiscussione", params, function(data){
		var ret = JSON.parse(data);
		
		var lastIdMessaggioDiscussione = $("ul#messageList > li:last-child input[type=hidden].idMessaggioDiscussione").val();
		if(ret.idMessaggioDiscussione <= lastIdMessaggioDiscussione)
			return;

		var li = $("<li>");
		var hidden = $("<input>").attr("type", "hidden")
								 .attr("class", "idMessaggioDiscussione")
								 .attr("value", ret.idMessaggioDiscussione);
		var dateSenderDiv = $("<div>").addClass("dateSenderDiv");
		var dateSpan = $("<span>").addClass("dateSpan").html(ret.timestamp);
		var senderSpan = $("<span>").addClass("senderSpan").html(ret.usernameMittente);
		var contentMessageDiv = $("<div>").addClass("contentMessageDiv");
		var pre = $("<pre>").addClass("innerText").text(ret.testo);
		contentMessageDiv.append(pre);
		dateSenderDiv.append(dateSpan).append(senderSpan);		
		li.append(hidden);
		li.append(dateSenderDiv).append(contentMessageDiv);
		$("ul#messageList").append(li);
		$("#textareaMessage").val("");
		$("#messagesArea").animate({ scrollTop:$("#messagesArea")[0].scrollHeight-$('#messagesArea').height() }, 300);
	});
});
refresh_chat();
function refresh_chat()
{
	var selector = "ul#messageList > li:last-child input[type=hidden].idMessaggioDiscussione";
	var params = {
		idDiscussione:$("#idDiscussione").val(),
		lastIdMessaggioDiscussione:$(selector).val()
	};
	if(!params.idDiscussione || !params.lastIdMessaggioDiscussione) return;
	$.get("getDeltaDiscussioni", params, function(data){
		if(data != "")
		{
			var map = JSON.parse(data);
			var mappaIdUsername = map.mappaIdUsername;
			var deltaDiscussioni = map.deltaDiscussioni;
			var itemsToDelete = 0;
			/*
			*	Dato che l'invio di un messaggio e la richiesta delle discussioni delta
			*	sono asincrone, puo' capitare che io scriva un messaggio, es con ID 2,
			*	e poco prima che venga inserito nel DOM della chat view parta refresh_chat
			*	che vede come id dell'ultimo messaggio non 2, ma bensi' 1, visto che il messaggio
			*	con id 2 non e' ancora stato inserito nel DOM.
			*	Cio' fa si' che io veda spuntare il messaggio con ID 2 sulla chat 2 volte:
			*	la prima quando viene inserito nel DOM, la seconda quando viene recuperato dalla
			*	richiesta ajax a getDeltaDiscussioni ed inserita anche questa volta nel DOM.
			*	Per risolvere il problema si eliminano quei messaggi con ID <= all'ultimo ID
			*	prima di appenderli al DOM. Ricalcoliamo quindi l'ultimo ID perche' nel frattempo
			*	potrebbe essere cambiato
			*/
			var lastIdMessaggioDiscussioneUpdated = $(selector).val();
			$.each(deltaDiscussioni, function(index, item){				
				if(item.ID_Messaggio_Discussione <= lastIdMessaggioDiscussioneUpdated)
					itemsToDelete++;
			});
			if(itemsToDelete > 0){
				//Cominciamo dall'indice 0 perche' le discussioni sono ordinate per ID_Messaggio_Discussione
				deltaDiscussioni.splice(0, itemsToDelete);
			}
			$.each(deltaDiscussioni, function(index, item){
				var date = new Date(item.timestamp);
				var day = date.getDate();
				var month = date.getMonth() + 1;
				if(month < 10) month = "0" + month;
				var year = date.getFullYear();
				var hour = date.getHours();
				var minutes = date.getMinutes();
				var seconds = date.getSeconds();
				var stringDate = day + "/" + month + "/" + year + " - " + hour + ":" + minutes + ":" + seconds;
				
				var li = $("<li>").hide();
				var hidden = $("<input>").attr("type", "hidden")
										 .attr("class", "idMessaggioDiscussione")
										 .attr("value", item.ID_Messaggio_Discussione);
				var dateSenderDiv = $("<div>").addClass("dateSenderDiv");
				var dateSpan = $("<span>").addClass("dateSpan").html(stringDate);
				var senderSpan = $("<span>").addClass("senderSpan").html(mappaIdUsername[item.ID_Membro_Mittente]);
				var contentMessageDiv = $("<div>").addClass("contentMessageDiv");
				var pre = $("<pre>").addClass("innerText").text(item.testo);
				contentMessageDiv.append(pre);
				dateSenderDiv.append(dateSpan).append(senderSpan);		
				li.append(hidden);
				li.append(dateSenderDiv).append(contentMessageDiv);
				$("ul#messageList").append(li);
				li.slideDown(200);
				$("#messagesArea").animate({ scrollTop:$("#messagesArea")[0].scrollHeight-$('#messagesArea').height() }, 200);
			});
		}
	});	
	setTimeout(refresh_chat, 2000);
}
</script>
</div>
<style>
#chatWrapper
{
	width:100%;
	background: -moz-linear-gradient(left,  rgba(255,255,255,1) 0%, rgba(206,206,206,0) 100%);
	background: -webkit-gradient(linear, left top, right top, color-stop(0%,rgba(255,255,255,1)), color-stop(100%,rgba(206,206,206,0)));
	background: -webkit-linear-gradient(left,  rgba(255,255,255,1) 0%,rgba(206,206,206,0) 100%);
	background: -o-linear-gradient(left,  rgba(255,255,255,1) 0%,rgba(206,206,206,0) 100%);
	background: -ms-linear-gradient(left,  rgba(255,255,255,1) 0%,rgba(206,206,206,0) 100%);
	background: linear-gradient(to right,  rgba(255,255,255,1) 0%,rgba(206,206,206,0) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#00cecece',GradientType=1 );
}
#messagesArea
{
	height:350px;
	overflow-y:scroll;
	border:1px solid black;
	margin-bottom:5px;
	border-radius:5px;
}
ul#messageList
{
	list-style:none;
	margin:0;
	padding:0px 5px;
	height:100%;
}

ul#messageList li
{
	padding:10px;
}
ul#messageList li:not(:last-child)
{
	border-bottom:1px dotted #888888;	
}
#textareaMessage
{
	width:90%;
	max-width: 90%;
	min-width:90%;
	height:100px;
	min-height:100px;
	max-height: 100px;
	border:1px solid grey;
	padding:5px;
	border-radius:5px;
	box-shadow:1px 1px 1px black;
	font-family:"Courier";
	font-size:14px;
	overflow-y:scroll;
	float:left;
	background-color:transparent;
}

.dateSenderDiv
{
	font-size:12px;
	color:red;
	font-weight:bold;
	font-style:italic;
}
.dateSpan { color:red; }
.senderSpan { color:green; margin-left:3px; }
.senderSpan:after { content:":"; }
.clearfix:after
{
	display:block;
	content:".";
	clear:both;
	visibility: hidden;
	line-height: 0;
	height: 0;
}
.contentMessageDiv .innerText
{
	font-style:italic;
	font-size:12px;
	font-family:"Verdana";
	margin:0;
	padding:0;
}
#sendMessageBtn
{
	float:right;
	width:8%;
	padding:10px;
	height:114px;
}
</style>