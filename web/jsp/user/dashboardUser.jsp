<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
<%@page contentType="text/html; charset=UTF-8" %>
            <title>Admin Dashboard</title>

            <link rel="stylesheet" href="CSS/layout.css" />
    </head>

    <body>
        <div class="wrapper">
        <div class="container">
<%@include file="sidebar.jsp" %>

                <!-- Main content -->
                <div class="main">
<%@include file="header.jsp" %>

                        <!-- Main content area -->
                        <main class="content">
                            <h1>Vị trí DashBoard</h1>
                        </main>


                </div>
               
        </div>
<%@ include file="footer.jsp" %>
                     </div>
    </body>

    </html>