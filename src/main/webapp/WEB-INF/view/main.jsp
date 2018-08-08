<html><head><title>Welcome</title></head>
<body>
<h3>Welcome</h3>
<hr/>
<%= request.getAttribute("user") %>, hello!
Your name is <%= request.getAttribute("name")%>
Your role is <%= request.getAttribute("user_role")%>
Your role is <%= request.getAttribute("role")%>
<hr/>
</body></html>