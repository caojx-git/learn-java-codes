package web.dao;

import web.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;


public class UserDao {

	/**
	 * 通过用户名查找用户
	 * @param con
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public User getByUserName(Connection con, String userName)throws Exception{
		User resultUser=null;
		String sql="select * from t_user where userName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser=new User();
			resultUser.setId(rs.getInt("id"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
		}
		return resultUser;
	}

	/**
	 * 通过用户名查找用户权限
	 * @param con
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public Set<String> getRoles(Connection con, String userName) throws Exception{
		Set<String> roles=new HashSet<String>();
		String sql="select * from t_user u,t_role r where u.roleId=r.id and u.userName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			roles.add(rs.getString("roleName"));
		}
		return roles;
	}

	/**
	 * 通用用户名查找权限
	 * @param con
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public Set<String> getPermissions(Connection con, String userName)throws Exception {
		Set<String> permissions=new HashSet<String>();
		String sql="select * from t_user u,t_role r,t_permission p where u.roleId=r.id and p.roleId=r.id and u.userName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			permissions.add(rs.getString("permissionName"));
		}
		return permissions;
	}
}
