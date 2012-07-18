<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:hidden name="idMembro" value="%{idMembro}"></s:hidden>
<!-- <h3 class="pageTitle">Prodotti acquistabili</h3> -->
<table id="listaProdotti">
	<tbody>
		<thead>
			<tr>
				<th>ID Prodotto</th>
				<th>Nome</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="listaProdotti">
				<tr>
					<td><s:property value="ID_Prodotto"/></td>
					<td><s:property value="nome"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</tbody>
</table>
<script>
$("#breadcrumbs_container > article").html(
	"<a href='#'>Home</a><div class='breadcrumb_divider'></div><a href='#'>Ordini aperti</a><div class='breadcrumb_divider'></div><a href='#' class='current'>Prodotti acquistabili</a>"
);
</script>