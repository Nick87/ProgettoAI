<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>GAS - Gruppo D'Acquisto Solidale</title>
<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<link rel="stylesheet" href="css/animate.css" type="text/css" media="all">
<script type="text/javascript" src="js/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/tms-0.3.js"></script>
<script type="text/javascript" src="js/tms_presets.js"></script>
<script type="text/javascript" src="js/jcarousellite.js"></script>
<script type="text/javascript" src="js/jquery.msgpanel.js"></script>
<script>
$(document).ready(function () {
    $('#warningpanel').msgpanel({
        panelClass: 'msgpanel-warning-msg',
        speed: 'slow',
        panelOpacity: 0.5
    });
    $("#loginForm").submit(function(e){
    	if($("#userInput").val() == "" || $("#passInput").val() == ""){
    		$('#warningpanel').data('msgpanel').showPanel("Riempire tutti i campi");
    		setTimeout(function(){
    			$('#warningpanel').data('msgpanel').hidePanel();
    		}, 5000);
        	return false;
        }
    });
    $("#loginForm input").focus(function(e){
    	if($(this).is("input[type=submit]")) return;
    	$('#warningpanel').data('msgpanel').hidePanel();
    });
    $('#warningpanel').click(function(e){
    	$(this).data('msgpanel').hidePanel();
    });
    $('.slider')._TMS({
		preset:'diagonalFade',
		easing:'easeOutQuad',
		duration:800,
		pagination:false,
		slideshow:6000
	});
	$("#testimonials").jCarouselLite({
		btnNext: ".down",
		btnPrev: ".up",
		auto:5000,
		visible: 1,
		speed: 600,
		vertical: true,
        circular: true,
		easing: 'easeOutCirc'
	});
	if($("#erroreDB").val() == "true"){
		$('#warningpanel').data('msgpanel').showPanel($("#erroreDBMessage").val());
		setTimeout(function(){
			$('#warningpanel').data('msgpanel').hidePanel();
		}, 5000);
    }
});
function animate(id, effect)
{
	var target = $("#"+id);
	target.removeClass(effect).addClass(effect);
	setTimeout(function(){
		target.removeClass(effect);
	}, 1000);
}
</script>
</head>
<body id="page1">
<s:hidden id="erroreDB" value="%{erroreDB}"/>
<s:hidden id="erroreDBMessage" value="%{erroreDBMessage}"/>
<div class="body1">
	<div id="warningpanel"></div>
	<div class="body2">
		<div class="main">
			<header>
				<div class="wrapper overflowVisible">
					<div id="title">
						<div>GAS</div>
						<div>Gruppo d'Acquisto Solidale</div>
					</div>
					<s:form id="loginForm" action="login" method="post">
						<s:textfield id="userInput" name="username" key="label.username" size="20" />
						<s:password id="passInput" name="password" key="label.password" size="20" />
						<s:submit method="execute" key="label.login" align="right" />
					</s:form>
					<div id="div_registrazione">
						<a href="<s:url action="registrazione"/>">Registrati</a>
					</div>
				</div>
				<div class="wrapper">
					<div class="slider">
					  <ul class="items">
					  
						<li><img src="images/img1.jpg" alt=""></li>
						<li><img src="images/img2.jpg" alt=""></li>
						<li><img src="images/img3.jpg" alt=""></li>
						<li><img src="images/img4.jpg" alt=""></li>
						<!--
						<li><img src="images/slider_1.jpg" alt=""></li>
						<li><img src="images/slider_2.jpg" alt=""></li>
						<li><img src="images/slider_3.jpg" alt=""></li>
						<li><img src="images/slider_4.jpg" alt=""></li>
						<li><img src="images/slider_5.jpg" alt=""></li>
						-->
					  </ul>
					</div>
				</div>
			</header>
		</div>
	</div>
	</div>
	<div class="body3">
		<div class="main">
			<article id="content">
				<div class="wrapper">
					<section class="col1">
						<h2 class="under">A tutto GAS!</h2>
						<div class="wrapper">
							<p class="pad_bot1">ndcaecati cupiditate non provident, similique sunt in culpa.</p>
							<p>Et harum quidem r eligendi optio cumque nihil impedit quo minus id quod maxime placeat</p>
						</div>
					</section>
					<section class="col2 pad_left1">
						<h2>Dicono di noi</h2>
						<div class="testimonials">
						<div id="testimonials">
						  <ul>
							<li>
								<div>“Nam libero tempore, cum soluta nobis eligendi quo minus quod maxime placeat facere.”</div>
								<span><strong class="color1">James Coloway,</strong><br>Director</span>
							</li>
							<li>
								<div>“Nam libero tempore, cum soluta nobis eligendi quo minus quod maxime placeat facere.”</div>
								<span><strong class="color1">James Coloway,</strong> <br>Director</span>
							</li>
							<li>
								<div>“Nam libero tempore, cum soluta nobis eligendi quo minus quod maxime placeat facere.”</div>
								<span><strong class="color1">James Coloway,</strong> <br>Director</span>
							</li>
						  </ul>
						</div>
						<a href="#" class="up"></a>
						<a href="#" class="down"></a>
						</div>
					</section>
				</div>
			</article>
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