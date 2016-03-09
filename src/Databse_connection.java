import java.sql.*;

public class Databse_connection {

	public static void main(String[] args) throws SQLException {
		Connection conn = null;								// https://docs.oracle.com/javase/7/docs/api/java/sql/Connection.html
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "test";
		String driver = "com.mysql.jdbc.Driver";
		String username = "root";
		String password = "root";

		try {
			Class.forName(driver).newInstance();		//create object of driver
			conn=DriverManager.getConnection(url+dbName, username, password);	// connection to test database
			
			/************************* Statements *****************************/
			Statement stmt = conn.createStatement();							// create statement for executing SQL query
			ResultSet rs = stmt.executeQuery("select * from users");			// result of query will come in resultSet
			
			/*
			rs.next();															// moves the cursor; first time cursor is placed before first row
			System.out.println(rs.getString("name"));
			System.out.println(rs.getString(1));
			*/
			
			// Printing whole table in one go
			while(rs.next()){
				System.out.println(rs.getString(1)+" -- "+rs.getString(2)+" -- "+rs.getString(3));
			}
			
			/************************* PREPARED Statements *****************************/
			PreparedStatement pstmt=conn.prepareStatement("select * from users where name = ? and sex = ?");
			pstmt.setString(1, "B");
			pstmt.setString(2, "M");
			ResultSet prs = pstmt.executeQuery();
			System.out.println("***********************************************************************");
			while(prs.next()){
				System.out.println(prs.getString(1)+" -- "+prs.getString(2)+" -- "+prs.getString(3));
			}
			
			/************************* CALLABLE Statements *****************************/
			//CallableStatement cstmt=conn.prepareCall("{call getTestData(?,?,?,?)}");
			
			/************************* Add Row: Insert Statements *****************************/
			pstmt = conn.prepareStatement("insert into users values (?,?,?)");
			pstmt.setString(1, "Burfi");
			pstmt.setString(2, "Richmond");
			pstmt.setString(3, "F");
			int result = pstmt.executeUpdate();
			System.out.println("Returns : "+result);
			
		} catch (Exception e) {
			System.out.println("Database connection failed !!! ");
			e.printStackTrace();
		} finally{
			conn.close();							// Very important to close the connection; loose connections are bad
		}
		
	}
}