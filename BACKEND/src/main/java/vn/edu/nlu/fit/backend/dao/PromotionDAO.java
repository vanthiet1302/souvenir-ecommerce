package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Promotion;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class PromotionDAO {

    private static final String SELECT_ACTIVE_PROMOTION = """
        SELECT id, product_id, discount_percent, start_date, end_date
        FROM promotions
        WHERE product_id = ?
          AND (start_date IS NULL OR start_date <= NOW())
          AND (end_date IS NULL OR end_date >= NOW())
        ORDER BY discount_percent DESC
        LIMIT 1
    """;

    public Promotion getActivePromotionByProductId(int productId) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ACTIVE_PROMOTION)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapPromotion(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Promotion mapPromotion(ResultSet rs) throws Exception {
        Timestamp start = rs.getTimestamp("start_date");
        Timestamp end   = rs.getTimestamp("end_date");

        return new Promotion(
                rs.getInt("id"),
                rs.getInt("product_id"),
                rs.getInt("discount_percent"),
                start != null ? start.toLocalDateTime() : null,
                end != null ? end.toLocalDateTime() : null
        );
    }
}
