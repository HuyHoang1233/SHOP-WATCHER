<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #0055ff 0%, #00d4ff 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            overflow: hidden;
            width: 100%;
            max-width: 400px;
        }
        .card-header {
            background-color: #fff;
            border-bottom: none;
            padding-top: 30px;
            text-align: center;
        }
        .btn-login {
            background: linear-gradient(to right, #0055ff, #00d4ff);
            border: none;
            color: white;
            padding: 10px;
            font-weight: bold;
        }
        .btn-login:hover {
            opacity: 0.9;
            color: white;
        }
    </style>
</head>
<body>
    <%
        String username_value = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("c_user")) {
                    username_value = cookie.getValue();
                    }
                }
        }
    
    
    %>
    
    <jsp:include page="/WEB-INF/views/header.jsp" /> 
    <div class="card login-card p-4">
        <div class="card-header">
            <h3 class="fw-bold text-primary"><i class="fas fa-eye"></i> ShopWatcher</h3>
            <p class="text-muted">Đăng nhập để quản lý theo dõi</p>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger mt-2" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
        </div>
        <div class="card-body">
            <form action="login" method="post">
                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-user"></i></span>
                        <input type="text" class="form-control" name="username" placeholder="Nhập username" value="<%= username_value %>" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Mật khẩu</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-lock"></i></span>
                        <input type="password" class="form-control" name="password" placeholder="Nhập mật khẩu" required>
                    </div>
                </div>
                <div class="d-flex justify-content-between mb-3">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="remember" id="remember">
                        <label class="form-check-label" for="remember">Ghi nhớ</label>
                    </div>
                    <a href="#" class="text-decoration-none">Quên mật khẩu?</a>
                </div>
                <button type="submit" class="btn btn-login w-100 rounded-pill">ĐĂNG NHẬP</button>
            </form>
        </div>
        <div class="card-footer bg-white text-center border-0 py-3">
            <small>Chưa có tài khoản? <a href="register.jsp" class="fw-bold text-primary">Đăng ký ngay</a></small>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" /> 
</body>
</html>