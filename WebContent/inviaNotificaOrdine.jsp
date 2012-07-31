<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="idMembro" value="%{idMembro}"/>

<s:if test="%{lista_ordini.size() == 0}">
	<h3 class="pageTitle">Non ci sono ordini chiusi da notificare</h3>
</s:if>
<s:else>
	<div id="resultNotificationArea">
	<s:property value="%{strProva}"/>
		<s:actionerror/>
		<s:actionmessage/>
	</div>
	<h3 class="pageTitle">Clicca sull'indice di una notifica per inserire del testo custom</h3>
	<table id="tabellaOrdiniChiusiNonNotificati">
		<thead>
			<tr>
				<th>Ordine</th>
				<th>Data apertura</th>
				<th>Data chiusura</th>
				<th>Stato</th>
				<th>Notifica utenti</th>
				<th>Notifica fornitore</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="lista_ordini" status="ordine">
				<tr>
					<td>
						<a href="#" class="orderIndex noStyle"><s:property value="#ordine.index + 1"/></a>
					</td>
					<td><s:date name="data_apertura" format="dd/MM/yyyy"/></td>
					<td><s:date name="data_chiusura" format="dd/MM/yyyy"/></td>
					<td><s:property value="successo"/></td>
					<td>
						<a class="inviaNotificaOrdine" href="#"></a>
						<input type="hidden" name="idOrdine" value="<s:property value="ID_Ordine"/>"/>
						<input type="hidden" name="successo" value="<s:property value="successo"/>"/>
						<input type="hidden" name="dest" value="utenti"/>
					</td>
					<td>
						<a class="inviaNotificaOrdine" href="#"></a>
						<input type="hidden" name="idOrdine" value="<s:property value="ID_Ordine"/>"/>
						<input type="hidden" name="successo" value="<s:property value="successo"/>"/>
						<input type="hidden" name="dest" value="fornitore"/>
					</td>
				</tr>
				<tr class="hide textareaTr handPointer">
					<td colspan="6">
						<textarea placeholder="Inserisci il testo della notifica" rows="3" cols="50"></textarea>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>
<script>
$("#tabellaOrdiniChiusiNonNotificati").on("click", "a", function(e){
	e.stopPropagation();
	e.preventDefault();
	var target = $(e.target);
	if(target.is(".orderIndex")){
		target.closest("tr").next().toggle("fast");
		$(".textareaTr").each(function(){
			if($(this)[0] !== target.closest("tr").next()[0])
				$(this).hide("fast");
		});		
	} else {
		var params = {
			idMembro:$("#idMembro").val(), //e' l'id del responsabile
			idOrdine:target.next().val(),
			successo:target.next().next().val(),
			who:target.next().next().next().val(),
			content:target.closest("tr").next().find("textarea").val()
		};
		$.get("doInviaNotificaOrdine", params, function(data){
			$("#content").html(data);
		});
	}	
});
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Comunicazioni</a><div class='breadcrumb_divider'></div><a href='#'>Gestione notifiche</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Invia notifica</a>"
);
</script>