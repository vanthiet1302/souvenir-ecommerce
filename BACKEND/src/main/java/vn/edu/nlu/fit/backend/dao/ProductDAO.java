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

    /* ================= SQL BASE ================= */
    private static final String BASE_SELECT = """
        SELECT id, category_id, name, description, original_price, discount_percent, sale_price,
               image_url, stock_quantity, total_sold, avg_rating, review_count
        FROM products
    """;

    /* ================= HOME PAGE ================= */

    public List<Product> getBestSellingProducts(int limit) {
        String sql = BASE_SELECT + " ORDER BY total_sold DESC LIMIT ?";
        return getProductsByLimit(sql, limit);
    }

    public List<Product> getNewestProducts(int limit) {
        String sql = BASE_SELECT + " ORDER BY id DESC LIMIT ?";
        return getProductsByLimit(sql, limit);
    }

    public List<Product> getTopRatedProducts(int limit) {
        String sql = BASE_SELECT + " ORDER BY avg_rating DESC, review_count DESC LIMIT ?";
        return getProductsByLimit(sql, limit);
    }

    public List<Product> getTopSellingByCategory(int categoryId, int limit) {
        String sql = BASE_SELECT + """
            WHERE category_id = ?
            ORDER BY total_sold DESC
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

    public List<Product> getProductsByCategory(int categoryId) {
        String sql = BASE_SELECT + """
            WHERE category_id = ?
            ORDER BY total_sold DESC
        """;

        List<Product> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
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

    public List<Product> getProductsByCategoryWithFilter(
            int categoryId,
            Integer minPrice,
            Integer maxPrice,
            ProductSort sort,
            int offset,
            int limit) {

        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SELECT);
        sql.append(" WHERE category_id = ?");

        if (minPrice != null) sql.append(" AND original_price >= ?");
        if (maxPrice != null) sql.append(" AND original_price <= ?");

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

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setInt(idx++, categoryId);
            if (minPrice != null) ps.setInt(idx++, minPrice);
            if (maxPrice != null) ps.setInt(idx++, maxPrice);
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

    public int countProductsByCategoryWithFilter(int categoryId, Integer minPrice, Integer maxPrice) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*)
            FROM products
            WHERE category_id = ?
        """);

        if (minPrice != null) sql.append(" AND original_price >= ?");
        if (maxPrice != null) sql.append(" AND original_price <= ?");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setInt(idx++, categoryId);
            if (minPrice != null) ps.setInt(idx++, minPrice);
            if (maxPrice != null) ps.setInt(idx, maxPrice);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /* ================= PRODUCT DETAIL ================= */

    public Product getProductById(int id) {
        String sql = BASE_SELECT + " WHERE id = ?";

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
        String sql = BASE_SELECT + """
            WHERE category_id = ?
              AND id <> ?
            ORDER BY total_sold DESC
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
        Product p = new Product(
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
        p.setDiscountPercent(rs.getInt("discount_percent"));
        p.setSalePrice(rs.getObject("sale_price") != null ? rs.getDouble("sale_price") : null);
        return p;
    }

    /* ================= ADMIN METHODS ================= */

    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) as total FROM products";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> getTopSellingProducts(int limit) {
        String sql = BASE_SELECT + " ORDER BY total_sold DESC LIMIT ?";
        return getProductsByLimit(sql, limit);
    }

    public List<Product> getAllProducts() {
        String sql = BASE_SELECT + " ORDER BY id DESC";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductsWithPagination(int offset, int limit) {
        String sql = BASE_SELECT + " ORDER BY id DESC LIMIT ? OFFSET ?";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertProduct(Product product) {
        String sql = """
            INSERT INTO products (category_id, name, description, original_price, 
                                 discount_percent, sale_price, image_url, stock_quantity, 
                                 total_sold, avg_rating, review_count)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0)
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getOriginalPrice());
            ps.setInt(5, product.getDiscountPercent());
            ps.setObject(6, product.getSalePrice());
            ps.setString(7, product.getImage());
            ps.setInt(8, product.getStockQuantity());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProduct(Product product) {
        String sql = """
            UPDATE products 
            SET category_id = ?, name = ?, description = ?, 
                original_price = ?, discount_percent = ?, sale_price = ?,
                image_url = ?, stock_quantity = ?
            WHERE id = ?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getOriginalPrice());
            ps.setInt(5, product.getDiscountPercent());
            ps.setObject(6, product.getSalePrice());
            ps.setString(7, product.getImage());
            ps.setInt(8, product.getStockQuantity());
            ps.setInt(9, product.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ================= SEARCH ================= */
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT p.*, c.category_name
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.id
            WHERE LOWER(p.name) LIKE LOWER(?) 
               OR LOWER(p.description) LIKE LOWER(?) 
               OR LOWER(c.category_name) LIKE LOWER(?)
            ORDER BY p.total_sold DESC, p.avg_rating DESC
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setCategoryName(rs.getString("category_name"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setOriginalPrice(rs.getDouble("original_price"));
                p.setImage(rs.getString("image_url"));
                p.setStockQuantity(rs.getInt("stock_quantity"));
                p.setTotalSold(rs.getInt("total_sold"));
                p.setAvgRating(rs.getDouble("avg_rating"));
                p.setReviewCount(rs.getInt("review_count"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

