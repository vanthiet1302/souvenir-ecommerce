package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.User;
import vn.edu.nlu.fit.backend.service.HeaderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/header")
public class HeaderController extends HttpServlet {

    private HeaderService headerService;

    @Override
    public void init() {
        headerService = new HeaderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Category> categories = headerService.getAllCategories();
        List<Category> topCategories = headerService.getTopCategories(5);

        request.setAttribute("categories", categories);
        request.setAttribute("topCategories", topCategories);

        HttpSession session = request.getSession(false);
        if (session != null) {
            request.setAttribute("authUser",
                    (User) session.getAttribute("authUser"));
        }
    }
}
