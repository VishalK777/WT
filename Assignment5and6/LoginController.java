package com.candid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String un = request.getParameter("username");
        String pw = request.getParameter("password");

// Connect to mysql(mariadb) and verify username password

        try {
            Class.forName("org.mariadb.jdbc.Driver");
// loads driver
            Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test", "root", "root"); // gets a new connection

            PreparedStatement ps = c.prepareStatement("select userName,pass from student where userName=? and pass=?");
            ps.setString(1, un);
            ps.setString(2, pw);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                response.sendRedirect("success.html");
                return;
            }
            response.sendRedirect("error.html");
            return;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
