package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/header")
public class HeaderController extends HttpServlet {

    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ========== ALL CATEGORIES (dropdown menu) ==========
        List<Category> categories = categoryDAO.getAll();
        request.setAttribute("categories", categories);

        // ========== TOP 5 CATEGORIES (menu bar – home) ==========
        List<Category> topCategories = categoryDAO.getTopSellingCategories(5);
        request.setAttribute("topCategories", topCategories);

        // ========== USER LOGIN ==========
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("authUser");
            request.setAttribute("authUser", user);
        }

        // Không forward view
    }
}
