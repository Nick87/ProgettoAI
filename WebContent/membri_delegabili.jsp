<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="%{listaUtentiDelegabili.size() == 0}">
	<div class="alert_info">Non ci sono utenti delegabili</div>
</s:if>
<s:else>
	<s:form id="listaDatiDelega" action="creaDelega" method="post">
		<s:select id="lista_ordini" label="Scegli un ordine chiuso" name="ordine_scelto"  list="listaOrdini"/>
		<s:select label="Scegli un Delegato" name="utente_scelto" list="listaUtentiDelegabili"/>
<!-- 		<input type="image" src="images/remove.jpeg" alt="Rimuovi Delega" title="Rimuovi Delega"/> -->
- 		<s:submit type="image" method="execute" value="Conferma Delega" src="images/conferma.png" title="Conferma Delega" label="Conferma Delega"  align="right" />
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
$("#listaDatiDelega").on("submit", function(e){
	e.preventDefault();
	var params = {
		idMembro:$("#idMembro").val(),
		ordine_scelto:$("#lista_ordini").val(),
		utente_scelto:$("#listaDatiDelega_utente_scelto").val(),
		lista_utenti_delegabili:$(this).serializeJSON()
	};
	$.get("creaDelega", params,function(data){
		$("#content").html(data);
	});
});
</script>