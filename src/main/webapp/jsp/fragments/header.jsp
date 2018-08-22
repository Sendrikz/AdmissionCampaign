<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head><title>Header</title></head>
<body>
<hr/>
It is header
<form name="redirectForm" method="POST" action="controller">
    <button>
        <input type="hidden" name="command" value="returnToMain" />
        Main
    </button>
</form>
<form name="logOutFrom" method="POST" action="controller">
    <button>
        <input type="hidden" name="command" value="logOut" />
        LogOut
    </button>
</form>
<hr/>
</body></html>
