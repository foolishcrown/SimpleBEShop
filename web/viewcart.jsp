<%-- 
    Document   : viewcart
    Created on : Sep 17, 2021, 10:12:26 PM
    Author     : Shang
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ hàng</title>
        <style>
            .Title{
                color: yellow;
                background: black;
                text-align: center;
                font-weight: bold;
            }
            .error{
                color: red;
            }
        </style>
        <script src="lib/jquery-3.4.1.js" type="text/javascript"></script>
        <script src="lib/jquery.validate.js" type="text/javascript"></script>
        <script src="lib/additional-methods.js" type="text/javascript"></script>
        <script type="text/javascript">
            $().ready(function () {
                $("#confirmCartForm").validate({
                    rules: {
                        accountName: {
                            required: true,
                            rangelength: [2, 50]
                        },
                        accountPhone: {
                            required: true,
                            pattern: "[0-9]{9,10}"
                        },
                        accountAddress: {
                            required: true,
                            rangelength: [5, 500]
                        }
                    },
                    messages: {
                        accountName: {
                            required: "Please input receiver's name",
                            rangelength: "Name lenght is from 2 to 50"
                        },
                        accountPhone: {
                            required: "Please input the phone number of the receiver",
                            pattern: "Wrong phone number format"
                        },
                        accountAddress: {
                            required: "Please input address that your want us delivery to",
                            rangelength: "Address lenght should from 5 to 500 character"
                        }
                    }
                });
            });
        </script>
        <c:if test="${not empty MsgError}">
            <script>
                window.addEventListener("load", function () {
                    alert("${MsgError}");
                });
            </script>
        </c:if>
    </head>
    <body>
        <h1 class="Title">*YELLOW MOON SHOP*</h1>
        <a href="MainController"><input type="submit" value="Back to home page" /></a><br/>
        <h1>Your shopping cart</h1>
        <br/>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty cart}" var="notEmptyCart">
            <form action="MainController" id="confirmCartForm" method="POST">
                <input type="submit" value="Confirm" onclick="return confirm('Confirm your order?')" />
                <input hidden="" name="action" value="ConfirmCart"/>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Select</th>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${cart}" var="product" varStatus="counter">
                            <tr>
                                <td><input type="checkbox" name="checkedItems" checked="" value="${product.value.id}" /></td>
                                <td>${counter.count}</td>
                                <td>${product.value.name}</td>
                                <td>
                                    <c:url value="MainController" var="decrease">
                                        <c:param name="action" value="UpdateCart" ></c:param>
                                        <c:param name="itemId" value="${product.value.id}" ></c:param>
                                        <c:param name="ChangeButton" value="Decrease"></c:param>
                                    </c:url>
                                    <a href="${decrease}"><button type="button" name="ChangeButton">-</button></a>
                                    <span style="font-weight: bold">${product.value.quantityCart}</span>
                                    <c:url value="MainController" var="increase">
                                        <c:param name="action" value="UpdateCart" ></c:param>
                                        <c:param name="itemId" value="${product.value.id}" ></c:param>
                                        <c:param name="ChangeButton" value="Increase"></c:param>
                                    </c:url>
                                    <a href="${increase}"><button type="button" name="ChangeButton">+</button></a>
                                </td>
                                <td>${product.value.priceCurrency}</td>
                                <td>${product.value.subtotalPriceCurrency}</td>
                                <td>
                                    <c:url value="MainController" var="removeCart">
                                        <c:param name="action" value="RemoveCart" ></c:param>
                                        <c:param name="itemId" value="${product.value.id}" ></c:param>
                                    </c:url>
                                    <a href="${removeCart}"><button type="button" onclick="return confirm('Are you sure you want to remove this item from cart?')">X</button></a>
                                </td>
                            </tr>
                        </c:forEach>  
                        <tr>
                            <td colspan='5'></td>
                            <td>${cart.getTotal()}</td>
                        </tr>

                    </tbody>
                </table>
                <c:if test="${sessionScope.ACCOUNT == null}">
                    <br/>
                    <h3>Vui lòng nhập thông tin của quý khách hàng</h3>
                    <table border="1">
                        <tbody>
                            <tr>
                                <td><b>Họ và tên:</b></td>
                                <td><input type="text" name="accountName" id="accountName" /></td>
                                <td><i>Ex: Nguyễn Văn A</i></td>
                            </tr>
                            <tr>
                                <td><b>Số điện thoại:</b></td>
                                <td><span>+84</span><input type="tel" maxlength="10" id="accountPhone" name="accountPhone"  /></td>
                                <td><i>Ex: +84 771231552</i></td>
                            </tr>
                            <tr>
                                <td><b>Địa chỉ:</b></td>
                                <td><input type="text" name="accountAddress" id="accountAddress" required="" /></td>
                                <td><i>Ex: 123 Hồng Bàng, phường 11, quận 5</i></td>
                            </tr>
                        </tbody>
                    </table>

                </c:if>        

            </form>
        </c:if>
        <c:if test="${!notEmptyCart}">
            You don't have any item, please take back to the home page and add items to cart!
        </c:if>

    </body>
</html>
