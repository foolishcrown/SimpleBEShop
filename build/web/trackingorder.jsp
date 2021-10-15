<%-- 
    Document   : trankingorder
    Created on : Sep 22, 2021, 6:05:29 PM
    Author     : Shang
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tracking your order</title>
        <style>
            .Title{
                color: yellow;
                background: black;
                text-align: center;
                font-weight: bold;
            }
            .grid {
                display: grid;
                gap: 1rem;
                grid-auto-flow: column;
            }
        </style>
    </head>
    <body>
        <h1 class="Title">*YELLOW MOON SHOP*</h1>
        <a href="MainController"><input type="submit" value="Back to home page" /></a>

        <form action="MainController" method="GET">
            <h1>TRACKING ORDER</h1>     
            <table>
                <tbody>
                    <tr>
                        <td>Enter your [Order ID]: </td>
                        <td>
                            <input type="search" name="txtSearchOrder"/>
                            <input hidden="" name="action" value="TrackingOrder"/>
                        </td>
                        <td><input type="submit" value="Check"/></td>
                    </tr>
                </tbody>
            </table>    
        </form>
        <div class="grid">
            <c:if test="${requestScope.ORDER != null}" var="isExist">
                <table border="1">
                    <tbody>
                        <tr>
                            <td>Order ID:</td>
                            <td>#${requestScope.ORDER.id}</td>
                        </tr>
                        <tr>
                            <td>Customer name:</td>
                            <td>${requestScope.ORDER.customerName}</td>
                        </tr>
                        <tr>
                            <td>Customer phone:</td>
                            <td>+84 ${requestScope.ORDER.customerPhone}</td>
                        </tr>
                        <tr>
                            <td>Customer address:</td>
                            <td>${requestScope.ORDER.customerAddress}</td>
                        </tr>
                        <tr>
                            <td>Order date:</td>
                            <td>${requestScope.ORDER.orderAt}</td>
                        </tr>
                        <tr>
                            <td>Done date:</td>
                            <td>${requestScope.ORDER.doneAt == NULL ? 'Not yet' : requestScope.ORDER.doneAt}</td>
                        </tr>
                        <tr>
                            <td>Status:</td>
                            <td>${requestScope.ORDER.status == 1 ? 'Waiting' : requestScope.ORDER.status == 2 ? 'Delivering' : requestScope.ORDER.status == 3 ? 'Success': 'Deny'}</td>
                        </tr>
                        <tr>
                            <td>Payment Method:</td>
                            <td>${requestScope.ORDER.paymentMethod == 1 ? 'COD' : 'Paypal' }</td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${!isExist}">
                <h3>${requestScope.MSG}</h3>
            </c:if>
            <c:set var="details" value="${requestScope.DETAILS}"/>
            <c:if test="${details != null}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${details}" var="detail" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td><img src="${detail.value.imageURL}" style="max-width: 100px"></td>
                                <td>${detail.value.name}</td>
                                <td>
                                    <span style="font-weight: bold">${detail.value.quantityCart}</span>
                                </td>
                                <td>${detail.value.priceCurrency}</td>
                                <td>${detail.value.subtotalPriceCurrency}</td>
                            </tr>
                        </c:forEach>  
                        <tr>
                            <td colspan='5'></td>
                            <td>${details.getTotal()}</td>
                        </tr>

                    </tbody>
                </table>
            </c:if> 
        </div>
    </body>
</html>
