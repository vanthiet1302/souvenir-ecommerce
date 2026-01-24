package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.model.Review;
import vn.edu.nlu.fit.backend.service.ReviewService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/reviews")
public class ReviewController extends HttpServlet {

    private ReviewService reviewService;

    @Override
    public void init() {
        reviewService = new ReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId = parseInt(request.getParameter("productId"), -1);
        if (productId == -1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Integer rating = null;
        String ratingParam = request.getParameter("rating");
        if (ratingParam != null && !ratingParam.isEmpty()) {
            rating = parseInt(ratingParam, null);
        }

        String sort = request.getParameter("sort");
        if (sort == null) sort = "newest";

        int page = parseInt(request.getParameter("page"), 1);
        int size = parseInt(request.getParameter("size"), 5);
        int offset = (page - 1) * size;

        List<Review> reviews =
                reviewService.getReviews(productId, rating, sort, offset, size);

        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/WEB-INF/views/review-items.jsp")
                .forward(request, response);
    }

    private Integer parseInt(String val, Integer def) {
        try {
            return val != null ? Integer.parseInt(val) : def;
        } catch (Exception e) {
            return def;
        }
    }
}
