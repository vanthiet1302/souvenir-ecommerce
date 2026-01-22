package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dao.ReviewDAO;
import vn.edu.nlu.fit.backend.model.Review;
import vn.edu.nlu.fit.backend.model.ReviewSummary;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        /* ===== FILTER ===== */
        Integer rating = null;
        String ratingRaw = request.getParameter("rating");
        if (ratingRaw != null && !ratingRaw.isEmpty() && !"all".equals(ratingRaw)) {
            rating = Integer.parseInt(ratingRaw);
        }

        String sort = request.getParameter("sort");
        if (sort == null || sort.isEmpty()) {
            sort = "newest";
        }

        /* ===== PAGINATION ===== */
        int page = 1;
        int size = 5;

        try {
            page = Integer.parseInt(request.getParameter("page"));
            size = Integer.parseInt(request.getParameter("size"));
        } catch (Exception ignored) {}

        int offset = (page - 1) * size;

        /* ===== DAO ===== */
        List<Review> reviews =
                reviewDAO.getReviewsByProductWithFilter(
                        productId, rating, sort, offset, size
                );

        /* ===== VIEW ===== */
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/WEB-INF/views/review-items.jsp")
                .forward(request, response);
    }
}
