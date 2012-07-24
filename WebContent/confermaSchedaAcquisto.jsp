<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:actionerror/>
<h3 class="pageTitle">La tua scheda di acquisto e' stata inserita nei nostri Database.
					 <p>Puoi sempre aggiornarla consultando la voce "I Miei Ordini".</p></h3>
<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Ordini aperti</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Prodotti acquistabili</a>"
);
</script>