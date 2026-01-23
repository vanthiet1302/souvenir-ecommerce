package vn.edu.nlu.fit.backend.controller;

import vn.edu.nlu.fit.backend.dao.*;
import vn.edu.nlu.fit.backend.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/product")
public class ProductDetailController extends HttpServlet {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private PromotionDAO promotionDAO;
    private ProductSpecificationDAO specificationDAO;
    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        promotionDAO = new PromotionDAO();
        specificationDAO = new ProductSpecificationDAO();
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ================= VALIDATE PRODUCT ID ================= */
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Category category = categoryDAO.getCategoryById(product.getCategoryId());

        /* ================= PROMOTION ================= */
        Promotion promotion =
                promotionDAO.getActivePromotionByProductId(productId);

        /* ================= SPECIFICATIONS ================= */
        List<ProductSpecification> specifications =
                specificationDAO.getByProductId(productId);

        /* ================= REVIEWS ================= */
        ReviewSummary summary =
                reviewDAO.getReviewSummaryByProductId(productId);

        Map<Integer, Integer> ratingCount =
                reviewDAO.countReviewsByRating(productId);

        // fill missing stars (1–5)
        for (int i = 1; i <= 5; i++) {
            ratingCount.putIfAbsent(i, 0);
        }

        Map<Integer, Integer> ratingPercent = new HashMap<>();
        int totalReviews = summary.getTotalReviews();

        for (int star = 1; star <= 5; star++) {
            int count = ratingCount.get(star);
            int percent = totalReviews == 0 ? 0 : (count * 100 / totalReviews);
            ratingPercent.put(star, percent);
        }

        /* ================= RELATED PRODUCTS ================= */
        List<Product> relatedProducts =
                productDAO.getRelatedProducts(
                        product.getCategoryId(),
                        productId,
                        6
                );

        /* ================= HEADER ================= */
        request.setAttribute("page", "PRODUCT_DETAIL");
        request.setAttribute("breadcrumbCategory", category);
        request.setAttribute("breadcrumbProduct", product);

        /* ================= PAGE DATA ================= */
        request.setAttribute("product", product);
        request.setAttribute("promotion", promotion);
        request.setAttribute("specifications", specifications);

        request.setAttribute("avgRating", summary.getAvgRating());
        request.setAttribute("totalReviews", summary.getTotalReviews());
        request.setAttribute("ratingCount", ratingCount);
        request.setAttribute("ratingPercent", ratingPercent);

        request.setAttribute("relatedProducts", relatedProducts);

        request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp")
                .forward(request, response);
    }
}
