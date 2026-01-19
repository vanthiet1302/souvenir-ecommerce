package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.ProductSpecification;
import vn.edu.nlu.fit.backend.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationDAO extends DBContext {

    private static final String SELECT_BY_PRODUCT_ID = """
        SELECT id, product_id, spec_name, spec_value
        FROM product_specifications
        WHERE product_id = ?
    """;

    public List<ProductSpecification> getByProductId(int productId) {
        List<ProductSpecification> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_PRODUCT_ID)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapSpecification(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private ProductSpecification mapSpecification(ResultSet rs) throws Exception {
        return new ProductSpecification(
                rs.getInt("id"),
                rs.getInt("product_id"),
                rs.getString("spec_name"),
                rs.getString("spec_value")
        );
    }
}
