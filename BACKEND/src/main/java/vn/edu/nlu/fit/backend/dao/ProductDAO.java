package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Product;
import vn.edu.nlu.fit.backend.Enums.ProductSort;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DBContext {

    /* ================= HOME PAGE ================= */

    // Sản phẩm bán chạy
    public List<Product> getBestSellingProducts(int limit) {
        String sql = """
            SELECT *
            FROM products
            ORDER BY total_sold DESC
            LIMIT ?
        """;
        return getProductsByLimit(sql, limit);
    }

    // Sản phẩm bán chạy theo từng Category (HomePage)
    public List<Product> getTopSellingByCategory(int categoryId, int limit) {
        String sql = """
            SELECT *
            FROM products
            WHERE category_id = ?
            ORDER BY total_sold DESC
            LIMIT ?
        """;

        List<Product> list = new ArrayList<>();

        try (Connection conn = getConnection();
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
    // sản phẩm mới
    public List<Product> getNewestProducts(int limit) {
        String sql = """
        SELECT *
        FROM products
        ORDER BY id DESC
        LIMIT ?
    """;
        return getProductsByLimit(sql, limit);
    }
    // sẩn phẩm đánh giá cao
    public List<Product> getTopRatedProducts(int limit) {
        String sql = """
        SELECT *
        FROM products
        ORDER BY avg_rating DESC, review_count DESC
        LIMIT ?
    """;
        return getProductsByLimit(sql, limit);
    }

    // Lấy sản phẩm sau khi đã lọc
    public List<Product> getProductsByCategoryWithFilter(
            int categoryId,
            Integer minPrice,
            Integer maxPrice,
            ProductSort sort,
            int offset,
            int limit) {

        List<Product> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT *
        FROM products
        WHERE category_id = ?""");

        if (minPrice != null) {
            sql.append(" AND original_price >= ?");
        }
        if (maxPrice != null) {
            sql.append(" AND original_price <= ?");
        }

        // SORT
        if (sort != null) {
            switch (sort) {
                case PRICE_ASC -> sql.append(" ORDER BY original_price ASC");
                case PRICE_DESC -> sql.append(" ORDER BY original_price DESC");
                case NEWEST -> sql.append(" ORDER BY id DESC");
                default -> sql.append(" ORDER BY total_sold DESC");
            }
        } else {
            sql.append(" ORDER BY total_sold DESC");
        }

        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setInt(index++, categoryId);

            if (minPrice != null) {
                ps.setInt(index++, minPrice);
            }
            if (maxPrice != null) {
                ps.setInt(index++, maxPrice);
            }

            ps.setInt(index++, limit);
            ps.setInt(index, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {list.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // Đếm tổng sản phẩm
    public int countProductsByCategoryWithFilter(
            int categoryId,
            Integer minPrice,
            Integer maxPrice
    ) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*) 
        FROM products 
        WHERE category_id = ?
    """);

        if (minPrice != null) {
            sql.append(" AND original_price >= ?");
        }
        if (maxPrice != null) {
            sql.append(" AND original_price <= ?");
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setInt(index++, categoryId);

            if (minPrice != null) {
                ps.setInt(index++, minPrice);
            }
            if (maxPrice != null) {
                ps.setInt(index, maxPrice);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /* ================= PRODUCT TYPE ================= */
    // Danh sách sản phẩm theo Category
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT *
            FROM products
            WHERE category_id = ?
            ORDER BY total_sold DESC
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /* ================= RANDOM RELATED (PRODUCT TYPE) ================= */
    public List<Product> getRandomRelated(int limit) {
        String sql = """
        SELECT *
        FROM products
        ORDER BY RAND()
        LIMIT ?
    """;

        return getProductsByLimit(sql, limit);
    }


    /* ================= PRODUCT DETAIL ================= */
    // Lấy chi tiết sản phẩm
    public Product getProductById(int id) {
        String sql = """
            SELECT *
            FROM products
            WHERE id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapProduct(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /* ================= RELATED ================= */
    public List<Product> getRelatedProducts(int categoryId, int excludeProductId, int limit) {
        String sql = """
        SELECT *
        FROM products
        WHERE category_id = ?
          AND id <> ?
        ORDER BY total_sold DESC
        LIMIT ?
    """;

        List<Product> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, excludeProductId);
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

    // Dùng cho các truy vấn có LIMIT
    private List<Product> getProductsByLimit(String sql, int limit) {
        List<Product> list = new ArrayList<>();

        try (Connection conn = getConnection();
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

    // Mapping ResultSet -> Product
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
