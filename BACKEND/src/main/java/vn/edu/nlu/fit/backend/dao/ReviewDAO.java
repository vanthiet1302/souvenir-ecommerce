package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Review;
import vn.edu.nlu.fit.backend.model.ReviewSummary;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

public class ReviewDAO extends DBContext {

    /* ================= BASIC ================= */

    // Lấy danh sách đánh giá theo sản phẩm (mặc định mới nhất)
    public List<Review> getReviewsByProductId(int productId) {
        List<Review> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM reviews
            WHERE product_id = ?
            ORDER BY created_at DESC
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapReview(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= SUMMARY ================= */

    // Tổng số đánh giá + điểm trung bình
    public ReviewSummary getReviewSummaryByProductId(int productId) {
        String sql = """
            SELECT 
                COUNT(*) AS total_reviews,
                ROUND(AVG(rating), 1) AS avg_rating
            FROM reviews
            WHERE product_id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ReviewSummary(
                        rs.getInt("total_reviews"),
                        rs.getDouble("avg_rating")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReviewSummary(0, 0);
    }

    /* ================= STATISTICS ================= */

    // Thống kê số lượng review theo số sao (1–5)
    public Map<Integer, Integer> countReviewsByRating(int productId) {
        Map<Integer, Integer> map = new HashMap<>();

        String sql = """
            SELECT rating, COUNT(*) AS count
            FROM reviews
            WHERE product_id = ?
            GROUP BY rating
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                map.put(rs.getInt("rating"), rs.getInt("count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /* ================= FILTER + SORT ================= */

    // Lọc & sắp xếp review
    public List<Review> getReviewsByProductWithFilter(
            int productId,
            Integer rating,
            String sort
    ) {
        List<Review> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT *
            FROM reviews
            WHERE product_id = ?
        """);

        if (rating != null) {
            sql.append(" AND rating = ?");
        }

        if ("oldest".equals(sort)) {
            sql.append(" ORDER BY created_at ASC");
        } else {
            sql.append(" ORDER BY created_at DESC");
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setInt(index++, productId);

            if (rating != null) {
                ps.setInt(index++, rating);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapReview(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= MAPPING ================= */

    private Review mapReview(ResultSet rs) throws Exception {
        return new Review(
                rs.getInt("id"),
                rs.getInt("product_id"),
                rs.getInt("user_id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                Timestamp.valueOf(
                        rs.getTimestamp("created_at").toLocalDateTime()
                )
        );
    }
}
