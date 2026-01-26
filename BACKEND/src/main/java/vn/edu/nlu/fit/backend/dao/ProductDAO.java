package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.Enums.ProductSort;
import vn.edu.nlu.fit.backend.model.Product;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    /* =====================================================
       BASE SELECT (KHÔNG WHERE – KHÔNG ORDER – KHÔNG LIMIT)
       ===================================================== */
    private static final String BASE_SELECT = """
        SELECT
            p.id,
            p.category_id,
            p.name,
            p.description,
            p.original_price,
            p.image_url,
            p.stock_quantity,
            p.total_sold,
            COALESCE(ROUND(AVG(r.rating), 1), 0) AS avg_rating,
            COUNT(r.id) AS review_count
        FROM products p
        LEFT JOIN reviews r
            ON p.id = r.product_id
        GROUP BY
            p.id,
            p.category_id,
            p.name,
            p.description,
            p.original_price,
            p.image_url,
            p.stock_quantity,
            p.total_sold
        
    """;

    /* ================= HOME PAGE ================= */

    public List<Product> getBestSellingProducts(int limit) {
        String sql = BASE_SELECT + " ORDER BY p.total_sold DESC LIMIT ?";
        return getProductsByLimit(sql, limit);
    }

    public List<Product> getNewestProducts(int limit) {
        String sql = BASE_SELECT + " ORDER BY p.id DESC LIMIT ?";
        return getProductsByLimit(sql, limit);
    }

    public List<Product> getTopRatedProducts(int limit) {
        String sql = BASE_SELECT + " ORDER BY avg_rating DESC, review_count DESC LIMIT ?";
        return getProductsByLimit(sql, limit);
    }

    public List<Product> getTopSellingByCategory(int categoryId, int limit) {
        String sql = """
            SELECT * FROM (
                """ + BASE_SELECT + """
            ) t
            WHERE t.category_id = ?
            ORDER BY t.total_sold DESC
            LIMIT ?
        """;

        List<Product> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* ================= PRODUCT TYPE ================= */

    public List<Product> getProductsByCategoryWithFilter(
            int categoryId,
            Integer minPrice,
            Integer maxPrice,
            Integer rating,
            ProductSort sort,
            int offset,
            int limit
    ) {

        List<Product> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT * FROM (
                """ + BASE_SELECT + """
            ) t
            WHERE t.category_id = ?
        """);

        if (minPrice != null) sql.append(" AND t.original_price >= ?");
        if (maxPrice != null) sql.append(" AND t.original_price <= ?");
        if (rating != null)   sql.append(" AND t.avg_rating >= ?");

        if (sort != null) {
            switch (sort) {
                case PRICE_ASC  -> sql.append(" ORDER BY t.original_price ASC");
                case PRICE_DESC -> sql.append(" ORDER BY t.original_price DESC");
                case NEWEST     -> sql.append(" ORDER BY t.id DESC");
                default         -> sql.append(" ORDER BY t.total_sold DESC");
            }
        } else {
            sql.append(" ORDER BY t.total_sold DESC");
        }

        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setInt(idx++, categoryId);
            if (minPrice != null) ps.setInt(idx++, minPrice);
            if (maxPrice != null) ps.setInt(idx++, maxPrice);
            if (rating != null)   ps.setInt(idx++, rating);
            ps.setInt(idx++, limit);
            ps.setInt(idx, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countProductsByCategoryWithFilter(
            int categoryId,
            Integer minPrice,
            Integer maxPrice,
            Integer rating
    ) {

        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) FROM (
                SELECT p.id
                FROM products p
                LEFT JOIN reviews r ON p.id = r.product_id
                WHERE p.category_id = ?
        """);

        if (minPrice != null) sql.append(" AND p.original_price >= ?");
        if (maxPrice != null) sql.append(" AND p.original_price <= ?");

        sql.append(" GROUP BY p.id ");

        if (rating != null) sql.append(" HAVING AVG(r.rating) >= ? ");

        sql.append(") t");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setInt(idx++, categoryId);
            if (minPrice != null) ps.setInt(idx++, minPrice);
            if (maxPrice != null) ps.setInt(idx++, maxPrice);
            if (rating != null)   ps.setInt(idx, rating);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /* ================= PRODUCT DETAIL ================= */

    public Product getProductById(int id) {
        String sql = """
            SELECT * FROM (
                """ + BASE_SELECT + """
            ) t
            WHERE t.id = ?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapProduct(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> getRelatedProducts(int categoryId, int excludeId, int limit) {

        String sql = """
        SELECT
            p.id,
            p.category_id,
            p.name,
            p.description,
            p.original_price,
            p.image_url,
            p.stock_quantity,
            p.total_sold,
            COALESCE(ROUND(AVG(r.rating), 1), 0) AS avg_rating,
            COUNT(r.id) AS review_count
        FROM products p
        LEFT JOIN reviews r ON p.id = r.product_id
        WHERE p.category_id = ?
          AND p.id <> ?
        GROUP BY p.id
        ORDER BY p.total_sold DESC
        LIMIT ?
    """;

        List<Product> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, excludeId);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    /* ================= COMMON ================= */

    private List<Product> getProductsByLimit(String sql, int limit) {
        List<Product> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Product mapProduct(ResultSet rs) throws Exception {
        return new Product(
                rs.getInt("id"),
                rs.getObject("category_id") != null ? rs.getInt("category_id") : null,
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("original_price"),
                rs.getString("image_url"),
                rs.getInt("stock_quantity"),
                rs.getInt("total_sold"),
                rs.getDouble("avg_rating"),
                rs.getInt("review_count")
        );
    }
}
