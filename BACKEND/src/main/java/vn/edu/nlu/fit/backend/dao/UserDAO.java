package vn.edu.nlu.fit.backend.dao;

import vn.edu.nlu.fit.backend.model.Address;
import vn.edu.nlu.fit.backend.model.User;
import vn.edu.nlu.fit.backend.util.DBContext;
import vn.edu.nlu.fit.backend.util.PasswordUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

        // 1. Đăng nhập: Hỗ trợ Email hoặc SĐT
    public User login(String loginDetail, String password) {
        // Cắt khoảng trắng dư thừa để tránh lỗi nhập liệu
        String detail = loginDetail.trim();
        String sql = "SELECT * FROM users WHERE (email = ? OR phone = ?) AND status = 'Active'";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, detail);
            ps.setString(2, detail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("password");
                // Kiểm tra so khớp mật khẩu
                if (PasswordUtil.checkPassword(password, hashedPasswordFromDB)) {
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
                } else {
                    System.out.println(">>> LOGIN FAIL: Mật khẩu không khớp cho " + detail);
                }
            } else {
                System.out.println(">>> LOGIN FAIL: Không tìm thấy tài khoản hoặc bị khóa: " + detail);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

            // 2. Đăng ký tài khoản mới
    public boolean register(String email, String password, String fullName, String phone) {
        String sql = "INSERT INTO users (full_name, email, password, phone, status, role, avatar) " +
                "VALUES (?, ?, ?, ?, 'Active', 'User', 'default-avatar.png')";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName.trim());
            ps.setString(2, email.trim());
            ps.setString(3, PasswordUtil.hashPassword(password)); // Băm mật khẩu khi lưu
            ps.setString(4, phone.trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Quên mật khẩu - Bước 1: Lưu mã xác thực
    public boolean setResetCode(String accountInfo, String code) {
        String info = accountInfo.trim();
        String sql = "UPDATE users SET reset_token = ?, token_expiry = DATE_ADD(NOW(), INTERVAL 5 MINUTE) " +
                "WHERE email = ? OR phone = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setString(2, info);
            ps.setString(3, info);
            int rows = ps.executeUpdate();
            System.out.println(">>> SET RESET CODE: " + (rows > 0 ? "Thành công" : "Thất bại") + " cho " + info);
            return rows > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Quên mật khẩu - Bước 2: Kiểm tra mã xác thực
    public boolean verifyCode(String accountInfo, String code) {
        String info = accountInfo.trim();
        String sql = "SELECT id FROM users WHERE (email = ? OR phone = ?) AND reset_token = ? AND token_expiry > NOW()";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, info);
            ps.setString(2, info);
            ps.setString(3, code);
            return ps.executeQuery().next();
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Quên mật khẩu - Bước 3: Cập nhật mật khẩu mới
    public boolean updatePassword(String accountInfo, String newPassword) {
        String info = accountInfo.trim();
        String sql = "UPDATE users SET password = ?, reset_token = NULL, token_expiry = NULL " +
                "WHERE email = ? OR phone = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String hashedPass = PasswordUtil.hashPassword(newPassword);
            ps.setString(1, hashedPass);
            ps.setString(2, info);
            ps.setString(3, info);

            int rows = ps.executeUpdate();
            System.out.println(">>> UPDATE PASSWORD: " + (rows > 0 ? "Thành công" : "Thất bại") + " cho " + info);
            return rows > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    //4. User controller
    public boolean updateProfile(int userId, String newEmail, String fullName, String phone, String gender, String dob) {
        String sql = "UPDATE users SET email = ?, full_name = ?, phone = ?, gender = ?, dob = ? WHERE id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newEmail);
            ps.setString(2, fullName);
            ps.setString(3, phone);
            ps.setString(4, gender);
            ps.setString(5, dob);
            ps.setInt(6, userId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //4. user controller - dia chi

    //them dia chi
    public boolean addAddress(int userId, String detail, String city, String district, String ward) {
        String sql = "INSERT INTO addresses (user_id, address_detail, city, district, ward) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, detail);
            ps.setString(3, city);
            ps.setString(4, district);
            ps.setString(5, ward);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Address> getAddressesByUserId(int userId) {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM addresses WHERE user_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Address addr = new Address();
                addr.setId(rs.getInt("id"));
                addr.setAddressDetail(rs.getString("address_detail"));
                addr.setWard(rs.getString("ward"));
                addr.setDistrict(rs.getString("district"));
                addr.setCity(rs.getString("city"));
                list.add(addr);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Cập nhật địa chỉ
    public boolean updateAddress(int addrId, String detail, String ward, String district, String city) {
        String sql = "UPDATE addresses SET address_detail = ?, ward = ?, district = ?, city = ? WHERE id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, detail);
            ps.setString(2, ward);
            ps.setString(3, district);
            ps.setString(4, city);
            ps.setInt(5, addrId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}