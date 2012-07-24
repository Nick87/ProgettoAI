<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>GAS - Gruppo D'Acquisto Solidale</title>
<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<link rel="stylesheet" href="css/login_retry/style.css" type="text/css" />
</head>
<body id="page1">
<div class="body1">
	<div class="body2">
		<div class="main">
			<header>
				<div class="wrapper overflowVisible">
					<div id="title">
						<div>GAS</div>
						<div>Gruppo d'Acquisto Solidale</div>
					</div>
				</div>
				<div class="loginWrapper">
				<div id="message">Nome utente e/o password errati.</div>
				<div id="loginbox">
					<div id="login-inner">
						<s:form id="loginFormRetry" action="login" method="post">
							<s:textfield id="userInput" name="username" key="label.username" size="20" />
							<s:password id="passInput" name="password" key="label.password" size="20" />
							<s:submit method="execute" key="label.login" align="right" />
						</s:form>
						<div id="back">Torna alla <a href="index.jsp">home page</a> oppure <a href="<s:url action="registrazione"/>">registrati</a></div>
					</div>
				</div>
			</div>
			</header>
		</div>
	</div>
</div>
<div class="body4">
	<div class="main">
		<article id="content2">
			<div class="wrapper">
				<section id="authors" class="col3">
					<h4>Chi siamo</h4>
					<ul class="list1">
						<li><a href="#">Nicola Alessandro Domingo</a></li>
						<li><a href="#">Roberto Gullo</a></li>
						<li><a href="#">Walter Mazzei</a></li>
						<li><a href="#">Antonino Sireci</a></li>
					</ul>
				</section>
				<section id="contacts" class="col2 right">
					<h4>Dove siamo</h4>
					<ul class="address">
						<li><span>Paese:</span>Italia</li>
						<li><span>Citt&agrave;:</span>Torino</li>
						<li><span>Indirizzo:</span>Corso Duca degli Abruzzi, 24</li>
						<li><span>Telefono:</span>011 0906254</li>
						<li><span>Sito web:</span><a href="http://www.polito.it">Politecnico di Torino</a></li>
					</ul>
				</section>
			</div>
		</article>
	</div>
</div>
</body>
</html>