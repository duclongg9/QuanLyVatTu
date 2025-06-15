<!--<head>
  <meta charset="UTF-8">
  <title>Warehouse Dashboard</title>
  <link rel="stylesheet" href="CSS/sidebar.css">  
</head>-->
<body>
  <aside class="sidebar">
    <h2>WareHourse Manager</h2>
    <div class="user-info">
      <img src="https://i.pravatar.cc/50" alt="avatar">
      <p>Helle<br><span class="online">? Online</span></p>
    </div>
    <input type="text" placeholder="Search...">
    <nav>
      <ul>
        <li><a href="#">Dashboard</a></li>
        <li><a href="#">General</a></li>
        <li><a href="#">Basic Elements</a></li>
        <li><a href="#">Simple tables</a></li>
        <li><a href="${pageContext.request.contextPath}/suppliercontroller">Supplier</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
      </ul>
    </nav>
  </aside>
</body>
