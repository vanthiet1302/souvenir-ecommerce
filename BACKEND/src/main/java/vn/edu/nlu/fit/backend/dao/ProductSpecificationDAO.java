package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.ProductSpecification;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationDAO extends DBContext {

    public List<ProductSpecification> getByProductId(int productId) {
        List<ProductSpecification> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM product_specifications
            WHERE product_id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ProductSpecification(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getString("spec_name"),
                        rs.getString("spec_value")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
