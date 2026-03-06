package vn.edu.nlu.fit.backend.filter;

import vn.edu.nlu.fit.backend.dao.CategoryDAO;
import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class HeaderFilter implements Filter {

    private CategoryDAO categoryDAO;

    @Override
    public void init(FilterConfig filterConfig) {
        categoryDAO = new CategoryDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        /* ===== 1. AUTH USER ===== */
        if (session != null && session.getAttribute("authUser") != null) {
            request.setAttribute("authUser", session.getAttribute("authUser"));
        }

        /* ===== 2. CATEGORIES (DROPDOWN) ===== */
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);

        /* ===== 3. TOP CATEGORIES (MENU BAR) ===== */
        List<Category> topCategories = categoryDAO.getTopSellingCategories(6);
        request.setAttribute("topCategories", topCategories);

        /* ===== 4. CART COUNT (OPTIONAL – SAFE DEFAULT) ===== */
        if (session != null && session.getAttribute("cartItemCount") != null) {
            request.setAttribute("cartItemCount", session.getAttribute("cartItemCount"));
        } else {
            request.setAttribute("cartItemCount", 0);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
