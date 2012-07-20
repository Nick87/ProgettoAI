<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="chatWrapper">
	<div class="messagesArea">
		<ul>
			<s:iterator value="chat">
				<li>
					<s:property value="data"/><br/>
					<s:property value="testo"/><br/>
				</li>
			</s:iterator>
		</ul>
	</div>
	<div class="textareaDiv">
		<textarea id="textareaMessage" rows="10" cols="80"></textarea>
	</div>
</div>