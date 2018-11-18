
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IsFollowing
 */
@WebServlet("/IsFollowing")
public class IsFollowing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IsFollowing() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String username = "Tom";
		String friendusername = request.getParameter("friendusername");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/NoExcuses?user=root&password=sqlpassword&useSSL=false&allowPublicKeyRetrieval=true");

			ps = conn.prepareStatement("SELECT * FROM Following WHERE username=? friendusername=?");
			ps.setString(1, username);
			ps.setString(2, friendusername);

			rs = ps.executeQuery();

			Boolean found = false;

			while (rs.next()) {
				found = true;
			}

			if (found) {
				// return status code indicating this user is following the other user
			} else {
				// return status code indicating this user is not following the other user
			}

		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
	}
}
