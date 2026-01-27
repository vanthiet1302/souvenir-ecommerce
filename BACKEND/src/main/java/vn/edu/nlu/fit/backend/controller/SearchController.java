package vn.edu.nlu.fit.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.backend.dao.ProductDAO;
import vn.edu.nlu.fit.backend.model.Product;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        query = query.trim();

        // Search products
        List<Product> products = productDAO.searchProducts(query);

        request.setAttribute("query", query);
        request.setAttribute("products", products);
        request.setAttribute("totalResults", products.size());

        request.getRequestDispatcher("/WEB-INF/views/search-results.jsp").forward(request, response);
    }
}