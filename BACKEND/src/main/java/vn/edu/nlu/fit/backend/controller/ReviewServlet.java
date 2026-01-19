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

        String productIdRaw = request.getParameter("productId");
        if (productIdRaw == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing productId");
            return;
        }

        int productId = Integer.parseInt(productIdRaw);

        String ratingRaw = request.getParameter("rating");
        String sort = request.getParameter("sort");

        Integer rating = null;
        if (ratingRaw != null && !ratingRaw.isEmpty()) {
            rating = Integer.parseInt(ratingRaw);
        }

        if (sort == null || sort.isEmpty()) {
            sort = "newest";
        }

        /* ================= DAO ================= */

        List<Review> reviewList =
                reviewDAO.getReviewsByProductWithFilter(productId, rating, sort);

        ReviewSummary reviewSummary =
                reviewDAO.getReviewSummaryByProductId(productId);

        Map<Integer, Integer> ratingMap =
                reviewDAO.countReviewsByRating(productId);

        /* ================= SET ATTRIBUTE ================= */

        request.setAttribute("reviewList", reviewList);
        request.setAttribute("reviewSummary", reviewSummary);
        request.setAttribute("ratingMap", ratingMap);

        request.setAttribute("selectedRating", rating);
        request.setAttribute("selectedSort", sort);

        /* ================= FORWARD ================= */

        request.getRequestDispatcher("/WEB-INF/views/review-items.jsp")
                .forward(request, response);
    }
}
