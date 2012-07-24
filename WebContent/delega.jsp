<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="tipoOrdini" value="%{tipoOrdini}"/>
<s:if test="%{listaOrdini.size() == 0}">
	<div class="alert_info">Non hai ordini chiusi al momento</div>
</s:if>
<s:else>
	<s:form id="listaDatiDelega" action="creaDelega" method="post">
		<s:select label="Ordine" name="ordine_scelto" list="listaOrdini"/>
		<s:select label="Delegato" name="utente_scelto" list="listaUtentiDelegabili"/>
		<s:submit method="execute" value="Conferma Delega" align="left" />
		<s:hidden id="idMembro" value="%{idMembro}"/>	
	</s:form>	
</s:else>
<script>
$("#listaDatiDelega").on("submit", function(e){
	e.preventDefault();
	var params = {
		idMembro:$("#idMembro").val(),
		ordine_scelto:$("#listaDatiDelega_ordine_scelto").val(),
		utente_scelto:$("#listaDatiDelega_utente_scelto").val()
		//lista_utenti_delegabili:$(this).serializeJSON()
	};
	console.log(params);
	$.get("creaDelega", params,function(data){
		$("#content").html(data);
	});
});
</script>