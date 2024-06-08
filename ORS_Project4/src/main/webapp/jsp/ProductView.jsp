<%@page import="com.rays.pro4.controller.ProductCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>User Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2020',
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ProductBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PRODUCT_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						List rlist = (List) request.getAttribute("rlist");
					%>


					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Product </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Product </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th align="left">Product Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="productName"
						placeholder="Enter Product Name" size="26"
						value="<%=DataUtility.getStringData(bean.getProductName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("productName", request)%></font></td>

				</tr>



				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Product Price <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="productAmmount"
						placeholder="Enter Product Ammount" size="26"
						value="<%=DataUtility.getStringData(bean.getProductAmmount())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("productAmmount", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>

				</tr>
				<tr>
					<th align="left">Purchase Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="purchaseDate"
						placeholder="Enter Purchase Date" size="26" readonly="readonly"
						id="udatee"
						value="<%=DataUtility.getDateString(bean.getPurchaseDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("purchaseDate", request)%></font></td>
				</tr>

				&nbsp;
				<tr>
					<th align="left">Product Description<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="description"
						placeholder="Enter Description" size="26"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>

				</tr>
				<%-- 	<tr>
					<th align="left">Category<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="category"
						placeholder="Enter Category" size="26"
						value="<%=DataUtility.getStringData(bean.getCategory())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("category", request)%></font></td>

				</tr> --%>
				&nbsp; &nbsp;
				<br>
				<%-- 		<tr>
					<th align="left"> Product Category<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("High", "High");
							map.put("Medium", "Medium");
							map.put("Low", "Low");
							

							String hlist = HTMLUtility.getList("category", String.valueOf(bean.getCategory()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("category", request)%></font></td>
				</tr>
				
 --%><tr>
				 <label>Product Category</font> :
				</label>
				<%=HTMLUtility.getList("category", String.valueOf(bean.getCategory()), rlist)%>
	<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("category", request)%></font></td>
				
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ProductCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ProductCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ProductCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ProductCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>