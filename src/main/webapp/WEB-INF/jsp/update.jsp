<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
<div>
	<form method="post" id="updateForm">
		<select id="customers" name="rid">
			<c:forEach items="${customersList }" var="customers">
				<option value="${customers.rid }">${customers.username }</option>
			</c:forEach>
		</select>
		<input type="hidden" name="username" id="username" value="">
		<hr>
		<div id="permissionsDiv">
			<c:forEach items="${permissions }" var="permission">
				<input type="checkbox" name="permissions" value="${permission.pid }">${permission.permissionname}&nbsp;&nbsp;
			</c:forEach>
			<div style="text-align:center">
				<input type="button" value="修改" id="updateBtn">
			</div>
		</div>
	</form>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			if("${customersList != null}"){
				checkPermissions("${customersList[0].rid }");
				$("#customers").change(function(){
					checkPermissions($("#customers").val());
				});
			}
			
			$("#updateBtn").click(function(){
				$("#username").val($("#customers option:selected").text());
				$.post("${pageContext.request.contextPath }/user/updatePermission",$("#updateForm").serialize(),function(data){
					if(data){
						alert("success");
// 						$("#rightDiv",window.parent.document).load("${pageContext.request.contextPath }/user/update");
					}else{
						alert("error");
					}
				});
			});
		});
		function checkPermissions(rid){
			$.get("${pageContext.request.contextPath }/user/findPermissions/"+rid,function(data){
				$.each(data,function(index,val){
					$("#permissionsDiv input[value="+val.pid+"]").attr("checked","checked");
				});
			});
		}
	</script>
</div>
</body>
</html>