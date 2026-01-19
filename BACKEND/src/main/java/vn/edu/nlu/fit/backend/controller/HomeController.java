package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.dao.HomeDAO;
import vn.edu.nlu.fit.backend.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    private HomeDAO homeDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        homeDAO = new HomeDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // ===== HEADER DATA (DÙNG CHUNG) =====
        List<Category> allCategories = categoryDAO.getAll();
        request.setAttribute("categories", allCategories);

        // ===== HOME DATA =====
        List<Category> topCategories =
                homeDAO.getTopCategoriesForHome();

        List<Category> extensionCategories =
                homeDAO.getExtensionSections();

        request.setAttribute("topCategories", topCategories);
        request.setAttribute("extensionCategories", extensionCategories);

        // Đánh dấu page hiện tại là HOME
        request.setAttribute("page", "HOME");

        request.getRequestDispatcher("/views/home.jsp")
                .forward(request, response);
    }
}
