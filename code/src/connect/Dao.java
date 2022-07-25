package connect;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import base.*;

public class Dao {
	// MySQL 5.7 - JDBC驱动名及数据库URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost:3306/library?useSSL=false&characterEncoding=UTF-8";
	
	// 数据库用户名及密码
	static final String USER_STRING = "root";
	static final String PW_STRING = "147030";
	
	// 管理员信息
	public static String ano = "2000521";
	public static String aname = "测试人员";
	public static String contact = "17300000000";
	
	// 普通用户信息
	public static String cno = "1000001";
	public static String cname = "小李";
	public static String ccontact = "15300000000";
	
	// 参数
	public int year[][] = {{0, Integer.MAX_VALUE}, {0, 1980}, {1981, 2000}, {2001, 2010}, {2011, 2020}};
	public int price[][] = {{0, Integer.MAX_VALUE}, {0, 50}, {50, 100}, {100, 200}, {200, Integer.MAX_VALUE}};
	
	public String now = LocalDateTime.now().toString().replace('T', ' ');
	public String borrowDate[][] = {{"1000-01-01 00:00:00", "9999-12-31 23:59:59"}, {CalTime.calculate(-7, 0), now}, {CalTime.calculate(-1, 1), now},
									{CalTime.calculate(-3, 1), now}, {CalTime.calculate(-1, 2), now}};
	public String returnDate[] = {"", " and return_ddl is null", " and return_date is null"};
	
	// 注册JDBC驱动
	public Dao() {
		try {
			Class.forName(JDBC_DRIVER);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// 连接数据库
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(URL, USER_STRING, PW_STRING);
	}
	
	// 检查管理员登录信息是否正确
	public boolean loginJudge(String usrname, String pwd, int mode) {
		String sql = "SELECT ano, contact FROM administrator WHERE aname=? and password=?";
		if(mode == 1) {
			sql = "SELECT cno, contact FROM card WHERE name=? and password=?";
		}
		
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usrname);
			ps.setString(2, pwd);
			// 查询
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(mode == 0) {
					ano = rs.getString("ano");
					aname = usrname;
					contact = rs.getString("contact");
				}
				else if(mode == 1) {
					cno = rs.getString("cno");
					cname = usrname;
					ccontact = rs.getString("contact");
				}

				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// 增加借书证
	public boolean addCard(String card, String name, String dept, int type) {
		String sql = "INSERT INTO card(cno, name, department, type) "
				+ "VALUES(?, ?, ?, ?)";
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, card);
			ps.setString(2, name);
			ps.setString(3, dept);
			ps.setInt(4, type);
			
			int line = ps.executeUpdate();
			if(line != 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// 删除借书证
	public boolean deleteCard(String card) {
		String sql = "DELETE FROM card WHERE cno=?";
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, card);
			
			int line = ps.executeUpdate();
			if(line != 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// 入库书号查询
	public Book addBookQuery(String bnoString) {
		Book book = null;
		String sql = "SELECT * FROM book WHERE bno=?";
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bnoString);
			ResultSet rs = ps.executeQuery();
			// 查询结果
			if(rs.next()) {
				String bno = rs.getString("bno");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String press = rs.getString("press");
				int year = rs.getInt("year");
				String author = rs.getString("author");
				float price = rs.getFloat("price");
				int total = rs.getInt("total");
				int stock = rs.getInt("stock");
				// 实例化Book类
				book = new Book(bno, category, title, press, year, author, price, total, stock);
			}	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	// 图书入库
	public boolean addBook(String bno, String category, String title, String press, int year,
			String author, float price, int num, boolean hasQueried) {
		String sql = "INSERT INTO book(bno, category, title, press, year, author, price, total, stock)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";		
		if(hasQueried) {
			sql = "UPDATE book SET total=total+?, stock=stock+? WHERE bno=?";
		}
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			if(hasQueried) {
				ps.setInt(1, num);
				ps.setInt(2, num);
				ps.setString(3, bno);
			}
			else {
				ps.setString(1, bno);
				ps.setString(2, category);
				ps.setString(3, title);
				ps.setString(4, press);
				ps.setInt(5, year);
				ps.setString(6, author);
				ps.setFloat(7, price);
				ps.setInt(8, num);
				ps.setInt(9, num);
			}
			
			int line = ps.executeUpdate();
			if(line != 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// 借书信息查询
	public ArrayList<BorrowRecord> queryBorrowBook(String cnoString) {
		ArrayList<BorrowRecord> br = new ArrayList<BorrowRecord>();
		String sql = "SELECT cno, bno, title, borrow_date, return_date, return_ddl, ano"
				+ " FROM borrow natural join book WHERE cno=?";
		int no = 0;
		
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cnoString);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String cno = rs.getString("cno");
				String bno = rs.getString("bno");
				String title = rs.getString("title");
				String borrow_date = rs.getString("borrow_date");
				String return_date = rs.getString("return_date");
				String return_ddl = rs.getString("return_ddl");
				String ano = rs.getString("ano");
				
				if(return_date != null) {
					String[] temp = return_date.split(" ");
					return_date = temp[0];
				}
				else {
					return_date = "未还";
				}
				
				if(return_ddl != null) {
					String[] temp = return_ddl.split(" ");
					return_ddl = temp[0];
				}
				else {
					return_ddl = "已还";
				}
				borrow_date = borrow_date.split(" ")[0];
				
				BorrowRecord temp = new BorrowRecord(++no, cno, bno, title, borrow_date, 
						return_date, return_ddl, ano);
				br.add(temp);				
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return br;
	}
	
	// 借书
	public String BorrowBook(String cnoString, String bnoString) {
		String sql1 = "SELECT return_date FROM borrow WHERE cno=? and bno=?";
		String sql2 = "SELECT stock FROM book WHERE bno=?";
		String sql3 = "UPDATE book SET stock=stock-1 WHERE bno=?";
		String sql4 = "INSERT INTO borrow(cno, bno, borrow_date, return_ddl, ano) "
				+ "VALUES(?, ?, now(), date_add(now(), interval 3 month), ?)";
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, cnoString);
			ps1.setString(2, bnoString);
			ResultSet rs1 = ps1.executeQuery();
			
			// 检查是否已经借了此书且未还
			while(rs1.next()) {
				if(rs1.getString("return_date") == null) {
					return "0";
				}
			}
			
			// 检查是否已达借书上限
			conn.setAutoCommit(false);
			String sql6 = "SELECT * FROM card WHERE cno=?";
			String sql7 = "SELECT count(bno) FROM borrow WHERE cno=? and return_date is null";
			PreparedStatement ps6 = conn.prepareStatement(sql6);
			ps6.setString(1, cnoString);
			ResultSet rs6 = ps6.executeQuery();
			if(rs6.next()) {
				int limit = rs6.getInt("limit");
				PreparedStatement ps7 = conn.prepareStatement(sql7);
				ps7.setString(1, cnoString);
				ResultSet rs7 = ps7.executeQuery();
				if(rs7.next()) {
					int count = rs7.getInt(1);
					if(count >= limit) {
						conn.commit();
						return "4";
					}
				}
			}
			
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, bnoString);
			ResultSet rs2 = ps2.executeQuery();
			if(rs2.next()) {
				int stock = rs2.getInt("stock");
				if(stock >= 1) {
					PreparedStatement ps3 = conn.prepareStatement(sql3);
					ps3.setString(1, bnoString);
					ps3.execute();
					PreparedStatement ps4 = conn.prepareStatement(sql4);
					ps4.setString(1, cnoString);
					ps4.setString(2, bnoString);
					ps4.setString(3, ano);
					ps4.execute();
					// 手动提交
					conn.commit();
					return "1";
				}
				else {
					String sql5 = "SELECT min(return_ddl) FROM borrow WHERE bno=? and return_date is null";
					PreparedStatement ps5 = conn.prepareStatement(sql5);
					ps5.setString(1, bnoString);
					ResultSet rs3 = ps5.executeQuery();
					if(rs3.next()) {
						String return_ddl = rs3.getString(1);
						conn.commit();
						return return_ddl;
					}
				}
			}
			else {
				conn.commit();
				return "2";
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return "3";
	}
	
	// 还书
	public int ReturnBook(String cnoString, String bnoString) {
		String sql1 = "SELECT * FROM book WHERE bno=?";
		String sql2 = "SELECT return_date FROM borrow WHERE cno=? and bno=?";
		String sql3 = "UPDATE borrow SET return_date=now(), return_ddl=NULL, ano=? WHERE cno=? and bno=? and return_date is NULL";
		String sql4 = "UPDATE book SET stock=stock+1 WHERE bno=?";
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, bnoString);
			ResultSet rs1 = ps1.executeQuery();
			if(rs1.next()) {
				conn.setAutoCommit(false);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, cnoString);
				ps2.setString(2, bnoString);
				ResultSet rs2 = ps2.executeQuery();
				
				// 检查是否借了或已归还本书
				boolean flag = true;
				while(rs2.next()) {
					if(rs2.getString("return_date") == null) {
						flag = false;
						break;
					}
				}
				
				if(flag) {
					conn.commit();
					return 1;
				}
				else {
					PreparedStatement ps3 = conn.prepareStatement(sql3);
					ps3.setString(1, ano);
					ps3.setString(2, cnoString);
					ps3.setString(3, bnoString);
					ps3.execute();
					
					PreparedStatement ps4 = conn.prepareStatement(sql4);
					ps4.setString(1, bnoString);
					ps4.execute();
					conn.commit();
					return 2;				
				}
			}
			else {
				return 0;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	// 图书查询
	public ArrayList<Book> QueryBook (String typeStr, String titleStr, String pressStr, String authorStr, int yearInd, int priceInd){
		ArrayList<Book> res = new ArrayList<Book>();
		String sql = "SELECT * FROM book WHERE price between ? and ? and year between ? and ?";
		if(!typeStr.equals("")) {
			sql += " and category='" + typeStr + "'";
		}
		if(!titleStr.equals("")) {
			sql += " and title like '%" + titleStr + "%'";
		}
		if(!pressStr.equals("")) {
			sql += " and press='" + pressStr + "'";
		}
		if(!authorStr.equals("")) {
			sql += " and author='" + authorStr + "'";
		}
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, price[priceInd][0]);
			ps.setInt(2, price[priceInd][1]);
			ps.setInt(3, year[yearInd][0]);
			ps.setInt(4, year[yearInd][1]);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String bno = rs.getString("bno");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String press = rs.getString("press");
				int year = rs.getInt("year");
				String author = rs.getString("author");
				float price = rs.getFloat("price");
				int total = rs.getInt("total");
				int stock = rs.getInt("stock");
				Book book = new Book(bno, category, title, press, year, author, price, total, stock);
				
				res.add(book);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	// 修改信息
	public int ModifyInfo(String noStr, String passwdStr, String contactStr, int mode) {
		String sql1 = "SELECT password FROM administrator WHERE ano=?";
		String sql2 = "UPDATE administrator SET";
		if(mode == 1) {
			sql1 = "SELECT password FROM card WHERE cno=?";
			sql2 = "UPDATE card SET";
		}

		if(!passwdStr.equals("")) {
			sql2 += " password='" + passwdStr + "'";
		}
		if(!contactStr.equals("")) {
			if(!passwdStr.equals("")) {
				sql2 += ",";
			}
			sql2 += " contact='" + contactStr + "'";			
		}
		
		if(mode == 0)
			sql2 += " WHERE ano=?";
		if(mode == 1)
			sql2 += " WHERE cno=?";
		
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, noStr);
			ResultSet rs1 = ps1.executeQuery();
			if(rs1.next()) {
				String oldPwd = rs1.getString("password");
				if(oldPwd.equals(passwdStr)) {
					return 1;
				}
				else {
					conn.setAutoCommit(false);
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, noStr);
					ps2.execute();
					conn.commit();
					if(mode == 0)
						contact = contactStr;
					if(mode == 1)
						ccontact = contactStr;
					return 2;
				}
			}
			else {
				return 0;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();		
		}
		
		return -1;
	}
	
	// 用户最近归还日期查询
	public String QueryLatestBorrow(String cnoString) {
		String sql1 = "SELECT min(return_ddl), bno FROM borrow WHERE cno=? and return_date is null";
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setString(1, cnoString);
			ResultSet rs1 = ps.executeQuery();
			if(rs1.next()) {
				String res = rs1.getString(1).split(" ")[0];
				String bno = rs1.getString(2);
				conn.setAutoCommit(false);
				String sql2 = "SELECT title FROM book WHERE bno=?";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, bno);
				ResultSet rs2 = ps2.executeQuery();
				if(rs2.next()) {
					res += " " + rs2.getString("title");					
				}
				conn.commit();
				return res;
			}
			else {
				return "1";
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return "0";
	}
	
	// 用户借阅信息查询
	public ArrayList<BorrowRecord> QueryUserBorrow(String cnoString, int borrowInd, int returnInd){
		ArrayList<BorrowRecord> res = new ArrayList<BorrowRecord>();
		String sql = "SELECT cno, bno, title, borrow_date, return_date, return_ddl, ano"
				+ " FROM borrow natural join book WHERE cno=? and borrow_date between ? and ?";
		sql += returnDate[returnInd];
		int no = 0;
		
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cnoString);
			ps.setString(2, borrowDate[borrowInd][0]);
			ps.setString(3, borrowDate[borrowInd][1]);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String cno = rs.getString("cno");
				String bno = rs.getString("bno");
				String title = rs.getString("title");
				String borrow_date = rs.getString("borrow_date");
				String return_date = rs.getString("return_date");
				String return_ddl = rs.getString("return_ddl");
				String ano = rs.getString("ano");
				
				if(return_date != null) {
					String[] temp = return_date.split(" ");
					return_date = temp[0];
				}
				else {
					return_date = "未还";
				}
				
				if(return_ddl != null) {
					String[] temp = return_ddl.split(" ");
					return_ddl = temp[0];
				}
				else {
					return_ddl = "已还";
				}
				borrow_date = borrow_date.split(" ")[0];
				
				BorrowRecord temp = new BorrowRecord(++no, cno, bno, title, borrow_date, 
						return_date, return_ddl, ano);
				res.add(temp);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();	
		}
		
		return res;
	}
	
	// 续借
	public String ContinueBorrow(String cnoString, String bnoString) {
		String sql1 = "SELECT return_date FROM borrow WHERE cno=? and bno=?";
		String sql2 = "UPDATE borrow SET return_ddl=date_add(return_ddl, interval 1 month), ano=? WHERE cno=? and bno=?";
		try {
			Connection conn = getConnection();
			// 输入参数
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, cnoString);
			ps1.setString(2, bnoString);
			ResultSet rs1 = ps1.executeQuery();
			
			// 检查是否已经借了此书且未还
			boolean flag = false;
			while(rs1.next()) {
				if(rs1.getString("return_date") == null) {
					flag = true;
				}
			}
			
			if(flag) {
				conn.setAutoCommit(false);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, Dao.ano);
				ps2.setString(2, cnoString);
				ps2.setString(3, bnoString);
				ps2.execute();
				conn.commit();
				return "2";
			}
			else {
				return "1";
			}
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}
		
		return "0";
	}
	
}
