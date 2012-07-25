<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="util.LoginStatus" %>
<%
	LoginStatus status = (LoginStatus) session.getAttribute("status");
	String tipoMembro = status.getTipoMembro();
	String username = status.getUsername();
	int idMembro = status.getID_Membro();
%>

<!doctype html>
<html>
<head>
<title>Pannello di amministrazione</title>
<link rel="stylesheet" href="css/admin/style.css" type="text/css"/>
<script src="js/jquery-1.7.2.min.js"></script>
<script>
var windowLoaded = false;
$(function(){
	$.fn.serializeJSON = function() {
		var json = {};
		jQuery.map($(this).serializeArray(), function(n, i){
			json[n['name']] = n['value'];
		});
		return json;
	};
	$("html").on("click", "ul.errorMessage li, ul.actionMessage li, .alert_info, .alert_warning, .alert_error, .alert_success", function(){
		var target;
		if($(this).is("ul.errorMessage li") || $(this).is("ul.actionMessage li"))
			target = $(this).closest("ul");
		else
			target = $(this);
		target.fadeOut("medium", function(){
			$(this).remove();
		});
	});
	$("#allCommands").on("click", "li > a", function(e){
		e.stopPropagation();
		e.preventDefault();
		var target = $(e.target);
		if(target.attr("id") == "logout" || target.attr("href") == "#")
			return true;
		if(target.attr("href") == "listaMieiOrdini" ||
		   target.attr("href") == "listaMessaggi" ||
		   target.attr("href") == "listaDiscussioni")
		{
			$("ul#" + target.attr("href")).slideToggle();
			if(target.html() == "HIDE")
				target.html("SHOW");
			else
				target.html("HIDE");
		}
		else
		{
			var params = { 
				idMembro:<%= idMembro %>,
				tipoMembro:"<%= tipoMembro %>",
				username:"<%= username %>"
			};
			history.pushState(params, null, target.attr("href"));
			$.get(target.attr("href"), params, function(data){
				$("#content").html(data);
			});
		}
	});
	$("#breadcrumbs_container a").click(function(){ return false; });
	
	window.addEventListener("popstate", function(e){
		if(windowLoaded){
			windowLoaded = false;
			return;
		}
		if(window.location.href.split("/").pop() == "login.action"){
			$("#content").empty();
			return;
		}
        $.get(window.location, e.state, function(data){
			$("#content").html(data);
		});
	});
});
$(window).load(function(){
	/***** 
		Chrome quando l'utente entra mostra la welcome dentro la welcome
		perche' l'evento 'popstate' viene scatenato anche al caricamento
		della pagina
	*****/
	windowLoaded = true;
});
</script>
</head>
<body>
	<header id="header">
		<div>
			<h1 class="site_title">Amministrazione</h1>
			<h2 class="section_title">GAS - Gruppo D'Acquisto Solidale</h2>
			<div class="btn_logout">
				<a href="<s:url action="logout"/>">Logout</a>
			</div>
		</div>
	</header>
	<section id="secondary_bar">
		<div class="user">
			<p><%= username %></p>
		</div>
		<div id="breadcrumbs_container">
			<!-- <article class="breadcrumbs"><a href="index.html">Website Admin</a><div class="breadcrumb_divider"></div><a class="current">Dashboard</a></article> -->
			<article class="breadcrumbs"><a href="#" class="current">Home</a></article>
		</div>
	</section>
	
	<aside id="sidebar" class="column">
		<div id="allCommands">
			<% if(tipoMembro.equals("U")) { %>
			<h3>Ordini</h3>
			<ul>
		 	  	<li><a href="listaOrdiniAperti">Ordini Aperti</a></li>
		 	  	<li><a href="#">I miei ordini</a><a class="toggleLink" href="listaMieiOrdini">HIDE</a>
		 	  		<ul id="listaMieiOrdini">
		 	  			<s:url action="mieiOrdini" var="ordiniAperti">
						  	<s:param name="tipoOrdini">aperti</s:param>
						</s:url>
						<s:url action="mieiOrdini" var="ordiniChiusi">
						  	<s:param name="tipoOrdini">chiusi</s:param>
						</s:url>
		 	  			<li><a href="${ordiniAperti}">Ordini aperti</a></li>
		 	  			<li><a href="${ordiniChiusi}">Ordini chiusi</a></li>
		 	  		</ul>
		 	  	</li>
	 	  	</ul>
	 	  	<h3>Comunicazioni</h3>
	 	  	<ul>
		 	  	<li>
		 	  		<div id="notificheDiv" class="clearfix">
		 	  			<a href="leggiNotifiche" class="left">Notifiche</a>
		 	  			<s:if test="%{numeroNotificheNonLette > 0}">
		 	  				<span class="numberBubble"><s:property value="%{numeroNotificheNonLette}"/></span>
		 	  			</s:if>
	 	  			</div>
 	  			</li>
		 	  	<li><a href="listaMessaggi">Messaggi</a></li>
		 	  	<li><a href="#">Discussioni</a><a class="toggleLink" href="listaDiscussioni">HIDE</a>
		 	  		<ul id="listaDiscussioni">
		 	  			<li><a href="nuovaDiscussione">Nuova Discussione</a></li>
		 	  			<li><a href="sommarioDiscussioni">Discussioni esistenti</a></li>
		 	  		</ul>
		 	  	</li>
	 	  	</ul>
	 	  	<h3>Altro</h3>
	 	  	<ul>
	 	  		<li><a href="delega">Delega</a></li>
	 	  	</ul>
			<% } else if(tipoMembro.equals("F")) { %>
			<ul>
				<li><a href="listino">Listino</a></li>
		 	  	<li><a href="messaggi">Messaggi</a></li>
		 	  	<li><a href="statoOrdineFornitore">Stato ordine</a></li>
		 	  	<li><a href="statisticheFornitore">Statistiche</a></li>
	 	  	</ul>
			<% } else if(tipoMembro.equals("R")) { %>
			<ul>
				<li><a href="listino">Listino</a></li>
				<li><a href="statoOrdineResponsabile">Stato ordine</a></li>
		 	  	<li><a href="statisticheResponsabile">Statistiche</a></li>
		 	  	<li><a href="inviaNotifica">Invia notifica</a></li>
		 	  	<li><a href="messaggi">Messaggi</a></li>
		 	  	<li><a href="riepilogoRitiro">Riepilogo ritiro</a></li>
	 	  	</ul>
			<% } else if(tipoMembro.equals("A")) { %>
			<ul>
				<li><a href="listaUtenti">Lista utenti</a></li>
				<li><a href="scaricaLog">Scarica log</a></li>
			</ul>
			<% } %>
		</div>
	</aside>
	
	<section id="contentWrapper">
		<div id="content"></div>
	</section>
</body>
</html>
