<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idMittente" value="%{idMittente}"/>
<s:hidden id="idDestinatario" value="%{idDestinatario}"/>
<div id="chatWrapper">
	<div id="messagesArea">
		<ul id="messageList">
			<s:iterator value="chat">
				<li>
					<div class="dateSenderDiv">
						<span class="dateSpan"><s:date name="timestamp" format="dd/MM/yyyy - kk:mm:ss"/></span>
						<span class="senderSpan"><s:property value="%{mapIdMembroUsername.get(ID_Membro_Mittente)}"/></span>
					</div>
					<div class="contentMessageDiv"><s:property value="testo"/></div>
				</li>
			</s:iterator>
		</ul>
	</div>
	<div id="textareaDiv" class="clearfix">
		<div id="textareaMessage" contenteditable="true"></div>
		<button id="sendMessageBtn">Invia</button>
	</div>
</div>
<script>
$("#sendMessageBtn").click(function(){
	var messageContent = $("#textareaMessage").html();
	if(messageContent == "") return;
	var params = {
		idMittente:$("#idMittente").val(),
		idDestinatario:$("#idDestinatario").val(),
		messageContent:messageContent
	};
	$.get("doInviaDiscussione", params, function(data){
		var ret = JSON.parse(data);
		var li = $("<li>");
		var dateSenderDiv = $("<div>").addClass("dateSenderDiv");
		var dateSpan = $("<span>").addClass("dateSpan").html(ret.timestamp);
		var senderSpan = $("<span>").addClass("senderSpan").html(ret.usernameMittente);
		var contentMessageDiv = $("<div>").addClass("contentMessageDiv").html(ret.testo);
		dateSenderDiv.append(dateSpan).append(senderSpan);		
		li.append(dateSenderDiv).append(contentMessageDiv);
		$("ul#messageList").append(li);
		$("#textareaMessage").empty();
	});
});
</script>
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
}

.dateSenderDiv
{
	font-size:12px;
	color:red;
	font-weight:bold;
	font-style:italic;
}
.dateSpan { color:red; }
.senderSpan { color:green; }
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
.contentMessageDiv
{
	font-style:italic;
	font-size:14px;
}
#sendMessageBtn
{
	float:right;
	width:8%;
	padding:10px;
	height:114px;
}
</style>