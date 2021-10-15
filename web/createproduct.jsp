<%-- 
    Document   : createproduct
    Created on : Sep 21, 2021, 7:28:43 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new</title>
        <style>
            .Title{
                color: yellow;
                background: black;
                text-align: center;
                font-weight: bold;
            }
            .areatext{
                height:100px;
                width: 200px;
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
                $("#createProductForm").validate({
                    rules: {
                        productImage: {
                            required: true
                        },
                        productName: {
                            required: true,
                            rangelength: [2, 100]
                        },
                        productDescription: {
                            required: true,
                            rangelength: [2, 500]
                        },
                        productPrice: {
                            required: true,
                            range: [1000, 10000000]
                        },
                        productQuantity: {
                            required: true,
                            range: [1, 10000],
                        },
                        createdDate: {
                            required: true,
                        },
                        expirationDate: {
                            required: true,
                            greaterThan: "#createdDate"
                        },
                        category: {
                            required: true
                        }
                    },
                    messages: {
                        productImage: {
                            required: "Please choose an image for product"
                        },
                        productName: {
                            required: "Please enter name",
                            rangelength: "Please enter your name with length of 2 to 100"
                        },
                        productDescription: {
                            required: "Please enter description",
                            rangelength: "Please enter your description with length of 2 to 500"
                        },
                        productPrice: {
                            required: "Please enter price",
                            range: "Please enter your price from 1.000VND to 10.000.000VND"
                        },
                        productQuantity: {
                            required: "Please enter quantity",
                            range: "Please enter your quantity from 1 to 10000"
                        },
                        expirationDate: {
                            greaterThan: "Expiration date must after create date"
                        },
                        category: {
                            required: "Please choose a category"
                        }
                    }
                });
            });


            function onImageChange(input) {
                var reader = new FileReader();
                reader.onload = function (event) {
                    if (input.files && input.files[0]) {
                        $("#thumbnail").attr("src", event.target.result);
                    }
                };
                var image = input.files[0];
                reader.readAsDataURL(image);
            }
            ;
        </script>

    </head>
    <body>
        <h1 class="Title">*YELLOW MOON SHOP*</h1>
        <a href="MainController"><input type="submit" value="Back to home page" /></a>
        <h1>Create product</h1>
        <c:if test="${requestScope.ErrorMSG != null}">
            <h4 class="error">${requestScope.ErrorMSG}</h4>
        </c:if>
        <form action="MainController" id="createProductForm" method="POST" enctype="multipart/form-data">
            <input hidden="" name="action" value="CreateProduct"/>
            <div style="position:fixed; top:25%; right: 10%">
                <img id="thumbnail" style="max-width: 200px"/>
            </div>
            <table>
                <tbody>
                    <tr>
                        <td>Image:</td>
                        <td>
                            <input type="file" id="productImage" name="productImage" accept="image/*" onchange="onImageChange(this)" required=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>Name:</td>
                        <td><input id="productName" name="productName"/></td>
                    </tr>
                    <tr>
                        <td>Description:</td>
                        <td>
                            <input type="text" class="areatext" name="productDescription" id="productDescription" />
                        </td>
                    </tr>
                    <tr>
                        <td>Price:</td>
                        <td><input type="number"  id="productPrice" name="productPrice"/> (VND)</td>
                    </tr>
                    <tr>
                        <td>Created Date:</td>
                        <td>
                            <c:set var="now" value="<%= new java.sql.Date((new java.util.Date().getTime()) + (30 * 24 * 60 * 60 * 1000))%>" />
                            <input type="date"  id="createdDate" min="${now}" name="createdDate"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Expiration Date:</td>
                        <td><input type="date" id="expirationDate" name="expirationDate"/></td>
                    </tr>
                    <tr>
                        <td>Category:</td>
                        <td>
                            <select name="category" id="category" >
                                <option value="0" selected disabled hidden>Choose here</option>
                                <c:forEach items="${requestScope.CATE}" var="category">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Quantity:</td>
                        <td>
                            <input type="number" min="1" name="productQuantity" id="productQuantity"/> (Bánh)
                        </td>
                    </tr>
                </tbody>
            </table>

            <input type="submit" value="Create"/>
        </form>

    </body>
</html>
