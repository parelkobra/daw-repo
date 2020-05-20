<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>	
<title>Users</title>
<style type="text/css">
	a:link {
		color: #333;
		text-decoration: none;
	}

	a:visited {
		color: #333;
	}

	a:hover {
		color: gray;
	}

	a:active {
  		color: #333;
	}

	.btn:hover {
	    background-color: white !important;
	}
</style>
</head>
<body>
<script type="text/javascript">
	function deleteConfirmation() {
		confirm('Do you really want to delete the user?');
	}	
</script>
	<div class="container">
		<h1 style="display:inline-block;"><a href="${pageContext.request.contextPath}">Users</a></h1>
		<form action="${pageContext.request.contextPath}/find-user" class="form-inline my-2 my-lg-0" style="display: inline-block;float:right;margin-bottom:8px;margin-top:30px;">
			<input class="form-check-input" type="radio" name="radioOption" id="inlineRadio1" value="username" checked>
			<label class="form-check-label" for="inlineRadio1" style="font-weight: 500;margin-right: 10px;">By username</label>
			<input class="form-check-input" type="radio" name="radioOption" id="inlineRadio2" value="email">
			<label class="form-check-label" for="inlineRadio2" style="font-weight: 500;margin-right: 10px;">By email</label>
	      	<input class="form-control mr-sm-2" type="text" placeholder="Search" name="input" value="" aria-label="Search" required>
	      	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">
	      		<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
	      	</button>
	    </form>
		<table class="table">
			<tr>
				<th>Username</th>
				<th>Name</th>
				<th>Email</th>
				<th>Rank</th>
				<th></th>
			</tr>
			<c:forEach var="user" items="${requestScope.users}">
				<tr>
					<td>${user.username}</td>
					<td>${user.name}</td>
					<td>${user.email}</td>
					<td>${user.rank}</td>
					<td style="text-align:center;" id="bin-glyph">
						<a onclick="return confirm('Do you really want to delete the user?')" href="${pageContext.request.contextPath}/delete?username=${user.username}">
							<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<button type="button" class="btn" data-toggle="modal" data-target="#create-user-modal">Create user</button>
		
		<!-- User creation alert -->
		<c:if test="${requestScope.userCreated == true}">
			<div class="alert alert-success" role="alert" style="margin-top:20px;">
  				<h4 class="alert-heading">Success!</h4>
				<p>User created successfully.</p>
			</div>
		</c:if>
		
		<c:if test="${requestScope.userCreated == false}">
			<div class="alert alert-danger" role="alert" style="margin-top:20px;">
  				<h4 class="alert-heading">Error!</h4>
				<p>The user couldn't be created because the username and/or the email is already on use.</p>
			</div>
		</c:if>
		  		
		<!-- Create user modal -->
		<div class="modal fade" id="create-user-modal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Create user</h4>	
					</div>
					<div class="modal-body">
						<form action="${pageContext.request.contextPath}/create-user"> 
							<div class="form-group">
								<label for="username">Username</label>
								<input type="text" class="form-control" placeholder="Enter username" id="username" name="username" value="" required>
							</div>
							<div class="form-group">
								<label>Password</label>
								<input type="password" minlength="5" class="form-control" placeholder="Enter password" name="password" value="" required>
							</div>
							<div class="form-group">
								<label>Name</label>
								<input type="text" class="form-control" placeholder="Enter name" name="name" value="" required>
							</div>
							<div class="form-group">
								<label>Email</label>
								<input type="email" class="form-control" placeholder="Enter email" name="email" value="" required>
							</div>
							<br><button type="submit" class="btn">Submit</button>
						</form>	
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>	
					</div>
				</div>	
			</div>
		</div>

	</div>
</body>
</html>
