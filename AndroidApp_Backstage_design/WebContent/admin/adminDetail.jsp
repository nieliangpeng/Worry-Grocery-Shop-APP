
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<%@page errorPage="../common/UserError.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath}/user"
	scope="application"></c:set>
<c:set var="path_" value="${pageContext.request.contextPath}"
	scope="application"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${path}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${path}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${path}/css/user_style.css" rel="stylesheet" type="text/css" />
<link href="${path}/skins/all.css" rel="stylesheet" type="text/css" />
<script src="${path}/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${path}/js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
<script src="${path}/js/common_js.js" type="text/javascript"></script>
<script src="${path}/js/footer.js" type="text/javascript"></script>
<script src="${path}/layer/layer.js" type="text/javascript"></script>
<script src="${path}/js/iCheck.js" type="text/javascript"></script>
<script src="${path}/js/custom.js" type="text/javascript"></script>
<title>管理员中心</title>

</head>

<body>
${updateAdminMsg}
<script type="text/javascript">
function sub() {
	
	var upimg=document.getElementById("phone").value;
	if (upimg.length==0){
		alert("请选择一个头像");
		
		return false;
	}
	return true;
}
</script>
	
	<!--用户中心样式-->
	<div class="user_style clearfix">
		<div class="user_center clearfix">
			<!--左侧样式-->
			
			<!--右侧样式-->
			<div class="right_style" style="float:left;">
				<!--消费记录样式-->
				<div class="user_address_style">
					<div class="title_style">
						<em></em>管理员信息
					</div>
					<!--用户信息样式-->
					<!--个人信息-->
					<div class="Personal_info" id="Personal">
							
							<ul class="xinxi">
								
								
								<li>
									<label>手机号码：</label> 
										<span>
											<strong style="color: green;">${admin.admin_phone}</strong>
										</span>
								</li>
								<li>
									<label>真实姓名：</label> 
										<span>
											<strong style="color: green;">
												${admin.admin_name}
											</strong>
										</span>
								</li>

							</ul>
							
						
						<form action="${path_}/updateAdminPhone.action" method="post" enctype="multipart/form-data" onsubmit="return sub();">
							<ul class="Head_portrait">
								<li class="User_avatar"><img width="200px;" height="200px;" alt="" src="${pageContext.request.contextPath}/upload/admin/${admin.admin_phone}/${admin.admin_header}"></li>
								<li><input type="file" name="phone" id="phone"/></li>
								<li><input name="name" type="submit" value="上传头像" class="submit" /></li>
								<li><input type="hidden" name="AdminTelephone" value="${admin.admin_phone}" /> </li>
							</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	

</body>
</html>
