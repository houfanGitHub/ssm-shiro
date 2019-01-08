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
		<h2>顶部标题</h2>
		<p style="text-align:right">
			<shiro:principal property="username" />|
			<a href="${pageContext.request.contextPath }/logout">退出</a>
		</p>
	</div>
	<div style="width:19%;float: left;position: relative;" id="leftDiv">
		<ul>用户功能
			<li><a href="javascript:void(0);" url="${pageContext.request.contextPath }/user/select">查询</a></li>
			<li><a href="javascript:void(0);" url="${pageContext.request.contextPath }/user/insert">添加</a></li>
			<li><a href="javascript:void(0);" url="${pageContext.request.contextPath }/user/update">修改</a></li>
			<li><a href="javascript:void(0);" url="${pageContext.request.contextPath }/user/delete">删除</a></li>
		</ul>
	</div>
	<div style="width: 80%;float: left; position: relative;" id="rightDiv">
		<h2>首页</h2>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("a").click(function(){
				$("#rightDiv").load($(this).attr("url"));
			})
		});
	</script>
</body>
</html>