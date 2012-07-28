<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tipoOrdini" value="%{tipoOrdini}"/>
<s:if test="%{listaOrdini.size() == 0}">
	<div class="alert_info">Non hai ordini chiusi al momento</div>
</s:if>
<s:else>
	<s:form id="listaDatiDelega" action="creaDelega" method="post">
		<s:select id="lista_ordini" label="Scegli un ordine chiuso" name="ordine_scelto" list="listaOrdini"/>
<%--  				<s:select label="Delegato"  --%>
<%--   					name="utente_scelto"  --%>
<%--   			list="listaUtentiDelegabili"/> --%>
<%--     			<s:submit method="execute" value="Conferma Delega" align="left" /> --%>
		<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>	
	</s:form>	
</s:else>
<script>
$("#lista_ordini option").on("click", function(e){
	var params = {
		idMembro:$("#idMembro").val(),
		ordine_scelto:$("#lista_ordini").val()
	};
	console.log(params);
	$.get("getMembriDelegabili", params,function(data){
		$("#content").html(data);
	});
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Altro</a><div class='breadcrumb_divider'></div><a href='#'>Gestione deleghe</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Crea/Modifica delega</a>"
);
</script>