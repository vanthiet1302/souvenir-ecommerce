package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Category;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DBContext {

    // Get all
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // get by id
    public Category getCategoryById(int id) {
        String sql = "SELECT id, name, image FROM categories WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // top selling
    public List<Category> getTopSellingCategories(int limit) {
        String sql = """
            SELECT c.id, c.name
            FROM categories c
            JOIN products p ON c.id = p.category_id
            GROUP BY c.id, c.name
            ORDER BY SUM(p.total_sold) DESC
            LIMIT ?
        """;

        List<Category> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // top selling id
    public List<Integer> getTopSellingCategoryIds(int limit) {
        String sql = """
            SELECT category_id
            FROM products
            GROUP BY category_id
            ORDER BY SUM(total_sold) DESC
            LIMIT ?
        """;

        List<Integer> ids = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("category_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    // not in top sell
    public List<Category> getCategoriesNotIn(List<Integer> usedIds) {

        if (usedIds == null || usedIds.isEmpty()) {
            return getAllCategories();
        }

        StringBuilder sql = new StringBuilder(
                "SELECT id, name FROM categories WHERE id NOT IN ("
        );

        for (int i = 0; i < usedIds.size(); i++) {
            sql.append("?");
            if (i < usedIds.size() - 1) sql.append(",");
        }
        sql.append(")");

        List<Category> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < usedIds.size(); i++) {
                ps.setInt(i + 1, usedIds.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
