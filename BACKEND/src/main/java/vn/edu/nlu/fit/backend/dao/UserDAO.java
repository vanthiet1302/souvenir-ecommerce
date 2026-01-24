package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Address;
import vn.edu.nlu.fit.backend.model.User;
import vn.edu.nlu.fit.backend.util.DBContext;
import vn.edu.nlu.fit.backend.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /* =========================
       1. ĐĂNG NHẬP
     ========================= */
    public User login(String loginDetail, String password) {
        String sql = "SELECT * FROM users WHERE (email = ? OR phone = ?) AND status = 'Active'";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loginDetail.trim());
            ps.setString(2, loginDetail.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next() && PasswordUtil.checkPassword(password, rs.getString("password"))) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAvatar(rs.getString("avatar"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setCreatedAt(rs.getString("created_at"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* =========================
       2. ĐĂNG KÝ
     ========================= */
    public boolean register(String email, String password, String fullName, String phone) {
        String sql = "INSERT INTO users (full_name, email, password, phone, status, role, avatar) " +
                "VALUES (?, ?, ?, ?, 'Active', 'User', 'default-avatar.png')";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, PasswordUtil.hashPassword(password));
            ps.setString(4, phone);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* =========================
       3. QUÊN MẬT KHẨU
     ========================= */
    public boolean setResetCode(String accountInfo, String code) {
        String sql = "UPDATE users SET reset_token = ?, token_expiry = DATE_ADD(NOW(), INTERVAL 5 MINUTE) " +
                "WHERE email = ? OR phone = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.setString(2, accountInfo.trim());
            ps.setString(3, accountInfo.trim());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyCode(String accountInfo, String code) {
        String sql = "SELECT id FROM users WHERE (email = ? OR phone = ?) " +
                "AND reset_token = ? AND token_expiry > NOW()";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountInfo.trim());
            ps.setString(2, accountInfo.trim());
            ps.setString(3, code);
            return ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resetPassword(String accountInfo, String newPassword) {
        String sql = "UPDATE users SET password = ?, reset_token = NULL, token_expiry = NULL " +
                "WHERE email = ? OR phone = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, PasswordUtil.hashPassword(newPassword));
            ps.setString(2, accountInfo.trim());
            ps.setString(3, accountInfo.trim());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* =========================
       4. ĐỔI MẬT KHẨU
     ========================= */
    public boolean checkPassword(int userId, String rawPassword) {
        String sql = "SELECT password FROM users WHERE id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && PasswordUtil.checkPassword(rawPassword, rs.getString("password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePasswordByUserId(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, PasswordUtil.hashPassword(newPassword));
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* =========================
       5. CẬP NHẬT PROFILE
     ========================= */
    public boolean updateProfile(int userId, String fullName, String phone, String gender, String dob) {
        String sql = "UPDATE users SET full_name = ?, phone = ?, gender = ?, dob = ? WHERE id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, gender);
            ps.setString(4, dob);
            ps.setInt(5, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* =========================
       6. ĐỊA CHỈ
     ========================= */

    // THÊM ĐỊA CHỈ – nếu là địa chỉ đầu tiên thì set mặc định
    public boolean addAddress(int userId, String detail, String city, String district, String ward) {

        if (detail == null || detail.isBlank()) return false;

        String checkSql = "SELECT COUNT(*) FROM addresses WHERE user_id = ?";
        String insertSql = "INSERT INTO addresses (user_id, address_detail, city, district, ward, is_default) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new DBContext().getConnection()) {

            boolean isFirst = false;
            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setInt(1, userId);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    isFirst = true;
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, userId);
                ps.setString(2, detail);
                ps.setString(3, city);
                ps.setString(4, district);
                ps.setString(5, ward);
                ps.setInt(6, isFirst ? 1 : 0);
                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Address> getAddressesByUserId(int userId) {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM addresses WHERE user_id = ? ORDER BY is_default DESC, id DESC";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Address addr = new Address();
                addr.setId(rs.getInt("id"));
                addr.setUserId(rs.getInt("user_id"));          // ✅ FIX QUAN TRỌNG
                addr.setAddressDetail(rs.getString("address_detail"));
                addr.setWard(rs.getString("ward"));
                addr.setDistrict(rs.getString("district"));
                addr.setCity(rs.getString("city"));
                addr.setIsDefault(rs.getInt("is_default"));
                list.add(addr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean updateAddress(int addressId, String detail, String ward, String district, String city) {
        String sql = "UPDATE addresses SET address_detail = ?, ward = ?, district = ?, city = ? WHERE id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, detail);
            ps.setString(2, ward);
            ps.setString(3, district);
            ps.setString(4, city);
            ps.setInt(5, addressId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAddress(int addressId) {
        String sql = "DELETE FROM addresses WHERE id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, addressId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setDefaultAddress(int userId, int addressId) {
        String resetSql = "UPDATE addresses SET is_default = 0 WHERE user_id = ?";
        String setSql   = "UPDATE addresses SET is_default = 1 WHERE id = ? AND user_id = ?";

        try (Connection conn = new DBContext().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(resetSql);
                 PreparedStatement ps2 = conn.prepareStatement(setSql)) {

                ps1.setInt(1, userId);
                ps1.executeUpdate();

                ps2.setInt(1, addressId);
                ps2.setInt(2, userId);
                ps2.executeUpdate();

                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
