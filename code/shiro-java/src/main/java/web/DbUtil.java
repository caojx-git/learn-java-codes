package web;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 * @author
 *
 */
public class DbUtil {

	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception{
		Class.forName("org.mariadb.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mariadb://192.168.46.131:3306/db_shiro", "root", "root");
		return con;
	}

	/**
	 * 关闭数据库连接
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con)throws Exception{
		if(con!=null){
			con.close();
		}
	}

	public static void main(String[] args) {
		DbUtil dbUtil=new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
	}
}
