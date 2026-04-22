package com;

import com.jdbc.config.JDBCConnection;
import com.user.vo.UserDTO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        List<UserDTO> users = new ArrayList<>();
        String sql = "SELECT user_id, name, role FROM users";

        try (
            Connection con = JDBCConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
        ){
            while (rs.next()){
                UserDTO user = new UserDTO(
                    rs.getLong("user_id"),
                    rs.getString("name"),
                    rs.getString("role")
                );
                users.add(user);
            }

            for (UserDTO u:users){
                System.out.println(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
