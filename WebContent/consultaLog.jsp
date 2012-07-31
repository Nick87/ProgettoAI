<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden id="idMembro" value="%{idMembro}"></s:hidden>
<s:hidden id="tipoMembro" value="%{tipoMembro}"></s:hidden>
<s:if test="%{lista_log.size() == 0}">
	<div class="alert_info">Non ci sono righe nel Log</div>
</s:if>
<s:else>
	<table id="listaLog">
		<thead>
			<tr>
				<th>ID_Operazione</th>
				<th>Testo</th>
				<th>Data e Ora</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="lista_log" var="l">
				<tr>
					<td><s:property value="ID_Operazione"/></td>
					<s:if test="%{#l.ID_Operazione == 1}">
						<s:set name="riga" value="%{'1'}"/>
					</s:if>
					<s:else>
						<s:set name="riga" value="%{'0'}"/>
					</s:else>
					<td><s:property value="content"/></td>
					<td><s:date name="timestamp" format="dd/MM/yyyy kk:mm:ss"/></td>
				</tr>
			</s:iterator>
			<tr>
				<td></td>
				<td>
					<s:if test="%{num_pagina_int > 0}">
						<input class="input_image_indietro" type="image" src="images/indietro.png" alt="Indietro" title="Indietro"/>
						<s:hidden id="num_pagina_indietro" value="%{num_pagina_int - 1}"/>
					</s:if>
					<s:if test="%{#riga == '0'}">
						<input class="input_image_avanti" type="image" src="images/avanti.png" alt="Avanti" title="Avanti"/>
						<s:hidden id="num_pagina_avanti" value="%{num_pagina_int + 1}"/>
					</s:if>
				</td>
				<td></td>					
			</tr>
		</tbody>
	</table>
</s:else>
<script>
$(document).ready(function() {	 
	$('.input_image_indietro').click(function(e){
		e.preventDefault();
		var index = $( this ).index($("a"));
		var params = {
			idMembro:$("#idMembro").val(),
			tipoMembro:$("#tipoMembro").val(),
			num_pag:$("#num_pagina_indietro").val()
		};
		$.get("consultaLog", params, function(data){
			$("#content").html(data);
		});            		           
	});
	$('.input_image_avanti').click(function(e){
		e.preventDefault();
		var index = $( this ).index( $("a") );
		var params = {
			idMembro:$("#idMembro").val(),
			tipoMembro:$("#tipoMembro").val(),
			num_pag:$("#num_pagina_avanti").val()
		};
		$.get("consultaLog", params, function(data){
			$("#content").html(data);
		});            		           
	});
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Gestione log</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Consulta log</a>"
);
</script>
