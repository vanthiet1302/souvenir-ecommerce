package vn.edu.nlu.fit.backend.controller;

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

    @Override
    public void init() {
        homeDAO = new HomeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Category> topCategories = homeDAO.getTopCategoriesForHome();
        List<Category> extensionCategories = homeDAO.getExtensionSections();

        request.setAttribute("topCategories", topCategories);
        request.setAttribute("extensionCategories", extensionCategories);

        request.getRequestDispatcher("/home/homepage.jsp")
                .forward(request, response);
    }
}
