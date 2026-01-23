package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Review;
import vn.edu.nlu.fit.backend.model.ReviewSummary;
import vn.edu.nlu.fit.backend.util.DBContextT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

public class ReviewDAO extends DBContextT {

    /**
     * Lấy danh sách review theo product với:
     * - rating: nếu null -> tất cả
     * - sort: "newest" (default) or "oldest"
     * - paging: offset, limit
     */
    public List<Review> getReviewsByProductWithFilter(
            int productId,
            Integer rating,
            String sort,
            int offset,
            int limit
    ) {
        List<Review> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.id, r.product_id, r.user_id, r.user_name, r.rating, r.comment, r.created_at ")
                .append("FROM reviews r ")
                .append("WHERE r.product_id = ? ");

        if (rating != null) {
            sql.append("AND r.rating = ? ");
        }

        if ("oldest".equalsIgnoreCase(sort)) {
            sql.append("ORDER BY r.created_at ASC ");
        } else {
            sql.append("ORDER BY r.created_at DESC ");
        }

        sql.append("LIMIT ? OFFSET ?");

        try (Connection conn = getConnection();
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
    // Summary

    public ReviewSummary getReviewSummaryByProductId(int productId) {
        String sql = "SELECT AVG(rating) AS avg_rating, COUNT(*) AS total_reviews " +
                "FROM reviews WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double avg = rs.getDouble("avg_rating");
                int total = rs.getInt("total_reviews");

                ReviewSummary s = new ReviewSummary();
                s.setProductId(productId);
                s.setAvgRating(Math.round(avg * 10.0) / 10.0); // 1 decimal
                s.setTotalReviews(total);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReviewSummary(); // mặc định 0
    }
    // count raiting

    public Map<Integer, Integer> countReviewsByRating(int productId) {
        Map<Integer, Integer> map = new HashMap<>();
        // init 1..5 = 0
        for (int i = 1; i <= 5; i++) map.put(i, 0);

        String sql = "SELECT rating, COUNT(*) AS cnt FROM reviews " +
                "WHERE product_id = ? GROUP BY rating";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int rating = rs.getInt("rating");
                int cnt = rs.getInt("cnt");
                map.put(rating, cnt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
    // thêm review
    public boolean addReview(Review r) {
        String sql = "INSERT INTO reviews (product_id, user_id, user_name, rating, comment, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getProductId());
            ps.setInt(2, r.getUserId());
            ps.setString(3, r.getUserName());
            ps.setInt(4, r.getRating());
            ps.setString(5, r.getComment());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ----------------- Helpers ----------------- */
    private Review mapReview(ResultSet rs) throws Exception {
        Review r = new Review();
        r.setId(rs.getInt("id"));
        r.setProductId(rs.getInt("product_id"));
        r.setUserId(rs.getInt("user_id"));
        r.setUserName(rs.getString("user_name"));
        r.setRating(rs.getInt("rating"));
        r.setComment(rs.getString("comment"));
        Timestamp t = rs.getTimestamp("created_at");
        r.setCreatedAt(t != null ? new Date(t.getTime()) : null);
        return r;
    }
}
