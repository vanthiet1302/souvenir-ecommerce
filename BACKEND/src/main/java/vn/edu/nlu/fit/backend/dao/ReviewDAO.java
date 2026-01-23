package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Review;
import vn.edu.nlu.fit.backend.model.ReviewSummary;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.*;
import java.util.*;

public class ReviewDAO {

    // Get reviews with filter + sort + paging
    public List<Review> getReviewsByProductWithFilter(
            int productId,
            Integer rating,
            String sort,
            int offset,
            int limit
    ) {
        List<Review> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT r.id,
                   r.product_id,
                   r.user_id,
                   u.full_name AS user_name,
                   r.rating,
                   r.comment,
                   r.created_at
            FROM reviews r
            JOIN users u ON r.user_id = u.id
            WHERE r.product_id = ?
        """);

        if (rating != null) {
            sql.append(" AND r.rating = ? ");
        }

        if ("oldest".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY r.created_at ASC ");
        } else {
            sql.append(" ORDER BY r.created_at DESC ");
        }

        sql.append(" LIMIT ? OFFSET ? ");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setInt(idx++, productId);

            if (rating != null) {
                ps.setInt(idx++, rating);
            }

            ps.setInt(idx++, limit);
            ps.setInt(idx, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapReview(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Review summary
    public ReviewSummary getReviewSummaryByProductId(int productId) {
        String sql = """
            SELECT COUNT(*) AS total_reviews,
                   AVG(rating) AS avg_rating
            FROM reviews
            WHERE product_id = ?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total_reviews");
                double avg = rs.getDouble("avg_rating");

                return new ReviewSummary(
                        total,
                        Math.round(avg * 10.0) / 10.0
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ReviewSummary(0, 0.0);
    }

    // Count reviews by rating
    public Map<Integer, Integer> countReviewsByRating(int productId) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 5; i++) map.put(i, 0);

        String sql = """
            SELECT rating, COUNT(*) AS cnt
            FROM reviews
            WHERE product_id = ?
            GROUP BY rating
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                map.put(rs.getInt("rating"), rs.getInt("cnt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    // Add review
    public boolean addReview(Review r) {
        String sql = """
            INSERT INTO reviews (product_id, user_id, rating, comment, created_at)
            VALUES (?, ?, ?, ?, NOW())
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getProductId());
            ps.setInt(2, r.getUserId());
            ps.setInt(3, r.getRating());
            ps.setString(4, r.getComment());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mapping ResultSet → Review
    private Review mapReview(ResultSet rs) throws Exception {
        Review r = new Review();
        r.setId(rs.getInt("id"));
        r.setProductId(rs.getInt("product_id"));
        r.setUserId(rs.getInt("user_id"));
        r.setUserName(rs.getString("user_name"));
        r.setRating(rs.getInt("rating"));
        r.setComment(rs.getString("comment"));
        r.setCreatedAt(rs.getTimestamp("created_at"));
        return r;
    }
}
