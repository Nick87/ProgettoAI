<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:actionerror/>
<h3 class="pageTitle">La tua delega e' stata inviata all'utente scelto e al responsabile dell'ordine.</h3>
<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Delega</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Conferma Delega</a>"
);
</script>