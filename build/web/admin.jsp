<%-- 
    Document   : admin
    Created on : Sep 18, 2021, 12:32:18 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
        <style>
            .Title{
                color: yellow;
                background: black;
                text-align: center;
                font-weight: bold;
            }
            .overdate{
                background: lightsalmon;
            }
        </style>
        <script>
            function changeSelect() {
                var x = document.querySelector('input[name="SearchOpt"]:checked').value;
                if (x === 'SearchByName') {
                    document.getElementById('txtSearch').disabled = false;
                    document.getElementById('minPrice').disabled = true;
                    document.getElementById('maxPrice').disabled = true;
                    document.getElementById('category').disabled = true;
                    document.getElementById('minPrice').value = null;
                    document.getElementById('maxPrice').value = null;
                    document.getElementById('category').value = 0;
                } else if (x === 'SearchByPrice') {
                    document.getElementById('txtSearch').disabled = true;
                    document.getElementById('minPrice').disabled = false;
                    document.getElementById('maxPrice').disabled = false;
                    document.getElementById('category').disabled = true;
                    document.getElementById('txtSearch').value = null;
                    document.getElementById('category').value = 0;
                } else if (x === 'SearchByCategory') {
                    document.getElementById('txtSearch').disabled = true;
                    document.getElementById('minPrice').disabled = true;
                    document.getElementById('maxPrice').disabled = true;
                    document.getElementById('category').disabled = false;
                    document.getElementById('txtSearch').value = null;
                    document.getElementById('minPrice').value = null;
                    document.getElementById('maxPrice').value = null;
                }
            }
        </script>
        <c:if test="${not empty MsgError}">
            <script>
                window.addEventListener("load", function () {
                    alert("${MsgError}");
                });
            </script>
        </c:if>
    </head>
    <body onload="changeSelect()">
        <h1 class="Title">*YELLOW MOON SHOP*</h1>
        <h1>Admin Page</h1>
        <c:if test="${sessionScope.ACCOUNT != null}" var="isLogin">
            Welcome, ${sessionScope.ACCOUNT.name}<br/>
            <a href="MainController?action=Logout"><input type="submit" value="Log out"/></a>
            </c:if>
        <a href="MainController?action=GetCreateForm"><input type="submit" value="Create new"/></a>
        <form action="MainController" method="GET">
            <input type="radio" name="SearchOpt" value="SearchByName" onclick="changeSelect()" <c:if test="${sessionScope.OPTSEARCH eq 'SearchByName'}">checked</c:if>>
            Name:<input type="search" name="txtSearch" value="${sessionScope.SEARCH}" id="txtSearch"/>
            <br/>
            <input type="radio" name="SearchOpt" value="SearchByPrice" onclick="changeSelect();" <c:if test="${sessionScope.OPTSEARCH eq 'SearchByPrice'}">checked</c:if> >
                Price:
                <input name="minPrice" type="number" value="${sessionScope.MINPRICE}" id="minPrice"/><span>VND</span>
            --
            <input name="maxPrice" type="number" value="${sessionScope.MAXPRICE}" id="maxPrice"/><span>VND</span>
            <br/>
            <input type="radio" name="SearchOpt" value="SearchByCategory" onclick="changeSelect()" <c:if test="${sessionScope.OPTSEARCH eq 'SearchByCategory'}">checked</c:if>>
                Category:
                <select name="category" id="category">
                    <option value="0" selected disabled hidden>Choose here</option>
                <c:forEach items="${requestScope.CATE}" var="category">
                    <c:if test="${sessionScope.SELECTEDCATE eq category.id}" var="selectedCate">
                        <option selected value="${category.id}">${category.name}</option>
                    </c:if>
                    <c:if test="${!selectedCate}">
                        <option value="${category.id}">${category.name}</option>
                    </c:if>
                </c:forEach>
            </select>
            <br/>
            Status
            <select name="status" id="status">
                <c:if test="${sessionScope.SELECTEDSTATUS eq 1}" var="selectedStatus">
                    <option selected value="1">Active</option>
                </c:if>
                <c:if test="${!selectedStatus}">
                    <option value="1">Active</option>
                </c:if>
                <c:if test="${sessionScope.SELECTEDSTATUS eq 2}" var="selectedStatus">
                    <option selected value="2">Inactive</option>
                </c:if>
                <c:if test="${!selectedStatus}">
                    <option value="2">Inactive</option>
                </c:if>
            </select>

            <br/>
            <input name="action" value="AdminSearch" hidden=""/>
            <input type="submit" value="Search"/>
            <br/>
            <br/>
            <c:if test="${PAGES > 0}">
                Trang:
                <c:forEach begin="0" end="${PAGES - 1}" var="index" varStatus="counter">
                    <input type="submit" name="indexPage" value="${counter.count}" class="${(sessionScope.INDEXPAGE - 1) eq index ? "red" : ""}"/>
                </c:forEach>
            </c:if>
        </form>
        <c:if test="${requestScope.LIST != null}" var="listProduct">
            <c:if test="${not empty requestScope.LIST}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Created Date</th>
                            <th>Expiration Date</th>
                            <th>Stock</th>
                                <c:if test="${sessionScope.SELECTEDSTATUS eq 1}">
                                <th>Edit</th>
                                </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.LIST}" var="product" varStatus="counter">
                            <c:set var="now" value="<%= new java.sql.Date(new java.util.Date().getTime())%>" />
                            <tr class="${now ge product.expirationDate or product.quantity == 0 ? "overdate" : ""}">
                                <td>${counter.count}</td>
                                <td><img src="${product.imageURL}" style="max-width: 100px"></td>
                                <td>${product.name}</td>
                                <td>${product.description}</td>
                                <td>${product.priceCurrency}</td>
                                <td>${product.createdDate}</td>
                                <td>${product.expirationDate}</td>
                                <td>${product.quantity}</td>
                                <c:if test="${sessionScope.SELECTEDSTATUS eq 1}">
                                    <td>
                                        <c:url value="MainController" var="editProduct">
                                            <c:param name="action" value="EditProduct" ></c:param>
                                            <c:param name="itemId" value="${product.id}" ></c:param>
                                        </c:url> 
                                        <a href="${editProduct}"><button type="button">Change</button></a>
                                    </td>

                                    <td>
                                        <c:url value="MainController" var="disableProduct">
                                            <c:param name="action" value="DisableProduct" ></c:param>
                                            <c:param name="itemId" value="${product.id}" ></c:param>
                                        </c:url>
                                        <a href="${disableProduct}"><button type="button" onclick="return confirm('Are you sure you want to disable this product from product list?')">X</button></a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:if>
        <c:if test="${!listProduct}">   
            <h3 class="red">Không tìm thấy mặt hàng!</h3>
        </c:if>

    </body>
</html>
