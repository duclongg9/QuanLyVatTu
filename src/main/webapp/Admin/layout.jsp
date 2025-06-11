<%@ include file="header.jsp" %>

<div class="container-fluid position-relative bg-white d-flex p-0">

    <%@ include file="sidebar.jsp" %>

    <div class="content">
        <%@ include file="navbar.jsp" %>

        <!-- PAGE CONTENT -->
        <jsp:include page="${page}" />

        <%@ include file="footer.jsp" %>
    </div>

</div>
