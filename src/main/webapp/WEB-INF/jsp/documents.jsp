<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>SpringHibernateUploader</title>
	<style type="text/css">
		html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, input {
    		font-size: 100%;
		}
		body {
			font-family: sans-serif;
			font-size:12px;
		}
		.data, .data td {
			border-collapse: collapse;
			width: 550px;
			border: 1px solid #aaa;
			padding: 2px;
		}
		.data th {
			background-color: #9ECFFF;
    		color: black;
		    font-weight: bold;
		}
	h1, h2, h3 {
	    font-family: Trebuchet MS,Liberation Sans,DejaVu Sans,sans-serif;
	    font-weight: bold;
	}
	h1 {
	    font-size: 170%;
	}		
	h2 {
	    font-size: 140%;
	}	
	h3 {
	    font-size: 120%;
	}	
	
	</style>
</head>
<body>

<h2>SpringHibernateUploader</h2>

<h3>Upload a File</h3>
<form:form method="post" action="save.html" commandName="document" enctype="multipart/form-data">
	<form:errors path="*" cssClass="error"/>
	<table>
	<tr>
		<td><form:label path="name">Name</form:label></td>
		<td><form:input path="name" /></td> 
	</tr>
	<tr>
		<td><form:label path="description">Description</form:label></td>
		<td><form:textarea path="description" /></td>
	</tr>
	<tr>
		<td><form:label path="content">File</form:label></td>
		<td><input type="file" name="file" id="file"></input></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="Add File"/>
		</td>
	</tr>
</table>	
</form:form>

<br/>
<h3>File List</h3>
<c:if  test="${!empty documentList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>Description</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${documentList}" var="document">
	<tr>
		<td width="100px">${document.name}</td>
		<td width="250px">${document.description}</td>
		<td width="20px">
			<a href="${pageContext.request.contextPath}/download/${document.id}.html">Download File</a> 
		
			<a href="${pageContext.request.contextPath}/remove/${document.id}.html"
				onclick="return confirm('Are you sure you want to delete this document?')">Remove File</a> 
		</td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
