package vn.edu.nlu.fit.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.backend.dao.OrderDAO;
import vn.edu.nlu.fit.backend.model.Order;
import vn.edu.nlu.fit.backend.model.OrderItem;
import vn.edu.nlu.fit.backend.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/orders")
public class UserOrderController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("detail".equals(action)) {
            viewOrderDetail(request, response, user);
        } else {
            viewOrderList(request, response, user);
        }
    }

    private void viewOrderList(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        // Get user's orders
        List<Order> orders = orderDAO.getOrdersByUserId(user.getId());

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/user/orders.jsp").forward(request, response);
    }

    private void viewOrderDetail(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("id"));
        Order order = orderDAO.getOrderById(orderId);

        // Check if order belongs to user
        if (order == null || order.getUserId() != user.getId()) {
            response.sendRedirect(request.getContextPath() + "/user/orders");
            return;
        }

        List<OrderItem> orderItems = orderDAO.getOrderItems(orderId);

        request.setAttribute("order", order);
        request.setAttribute("orderItems", orderItems);
        request.getRequestDispatcher("/user/order-detail.jsp").forward(request, response);
    }
}
