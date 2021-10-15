<%-- 
    Document   : home
    Created on : Sep 8, 2021, 6:27:44 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <style>
            .red {
                color: red;
                font-weight: bold;
            }
            .Title{
                color: yellow;
                background: black;
                text-align: center;
                font-weight: bold;
            }
            .searchForm{
                background: ivory;
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
         <c:if test="${not empty SUCCESS}">
            <script>
                window.addEventListener("load", function () {
                    alert("${SUCCESS}");
                });
            </script>
        </c:if>
        
    </head>
    <body onload="changeSelect()">
        <h1 class="Title">*YELLOW MOON SHOP*</h1>
        <c:if test="${sessionScope.ACCOUNT != null}" var="isLogin">
            Welcome, ${sessionScope.ACCOUNT.name}<br/>
            <a href="MainController?action=Logout"><input type="submit" value="Log out" /></a>
            <a href="MainController?action=ViewTrackPage"><input type="submit" value="Tracking your order" /></a>
        </c:if>
        <c:if test="${!isLogin}">
            <a href="login.html"><input type="submit" value="Log in" /></a>
        </c:if>
        <a href="viewcart.jsp"><input type="submit" value="View cart" /></a>
        
        <br/>
        <br/>
        <form action="MainController" method="GET" class="searchForm">
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
                    <c:if test="${sessionScope.SELECTEDCATE eq category.id}" var="selected">
                        <option selected value="${category.id}">${category.name}</option>
                    </c:if>
                    <c:if test="${!selected}">
                        <option value="${category.id}">${category.name}</option>
                    </c:if>
                </c:forEach>
            </select>
            <br/>
            <input name="action" value="Search" hidden=""/>
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
                            <th>Buy</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.LIST}" var="product" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td><img src="${product.imageURL}" style="max-width: 100px"></td>
                                <td>${product.name}</td>
                                <td>${product.description}</td>
                                <td>${product.priceCurrency}</td>
                                <td>${product.createdDate}</td>
                                <td>${product.expirationDate}</td>
                                <td>${product.quantity}</td>
                                <td>
                                    <form action="MainController" method="POST">
                                        <input name="action" value="AddCart" hidden=""/>
                                        <input name="productId" value="${product.id}" hidden=""/>
                                        <input type="submit" value="Add to cart" />
                                    </form>
                                </td>
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
