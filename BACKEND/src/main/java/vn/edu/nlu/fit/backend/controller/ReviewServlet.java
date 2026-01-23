package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dao.ReviewDAO;
import vn.edu.nlu.fit.backend.model.Review;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReviewServlet", urlPatterns = {"/reviews"})
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ================= 1. PARAMS ================= */
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // filter rating ("" hoặc null = all)
        Integer rating = null;
        String ratingParam = request.getParameter("rating");
        if (ratingParam != null && !ratingParam.isEmpty()) {
            try {
                rating = Integer.parseInt(ratingParam);
            } catch (Exception ignored) {}
        }

        // sort
        String sort = request.getParameter("sort");
        if (sort == null) sort = "newest"; // default

        // pagination
        int page = parseInt(request.getParameter("page"), 1);
        int size = parseInt(request.getParameter("size"), 5);
        int offset = (page - 1) * size;

        /* ================= 2. DAO ================= */
        List<Review> reviews = reviewDAO.getReviews(
                productId,
                rating,
                sort,
                offset,
                size
        );

        /* ================= 3. SEND TO JSP ================= */
        request.setAttribute("reviews", reviews);

        // fragment JSP (AJAX load)
        request.getRequestDispatcher("/WEB-INF/views/review-items.jsp")
                .forward(request, response);
    }

    /* ================= UTIL ================= */
    private int parseInt(String value, int defaultVal) {
        try {
            return value != null ? Integer.parseInt(value) : defaultVal;
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
