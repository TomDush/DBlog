<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:ui="http://java.sun.com/jsf/facelets">

<h:head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<ui:insert name="headtitle">
		<title>DBlog</title>
	</ui:insert>
</h:head>
<h:body>
	<div id="main">
		<div id="header">
			<ui:insert name="header">
				<ui:include src="/template/dblog_header.jsp" />
			</ui:insert>
		</div>
		<div id="content">
			<ui:insert name="content" />
		</div>
		<div id="footer">
			<ui:insert name="footer">
				<ui:include src="/template/footer.jsp" />
			</ui:insert>
		</div>
	</div>
</h:body>
</html>