
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
 * Servlet implementation class Location
 */
@WebServlet("/Location")
public class Location extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("in doGet");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		String username = request.getParameter("username");

		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// protocol: connecting from jdbc to mysql//hostname:port/database_name
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/NoExcuses?user=root&password=lol123&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM Users WHERE username = ?");
			ps.setString(1, username);

			rs = ps.executeQuery();

			while (rs.next()) {
				// send location back
				System.out.println(rs.getString("latitude"));
				System.out.println(rs.getString("longitude"));
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());

		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally { // close streams, databases, etc here
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
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing streams: " + sqle.getMessage());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		System.out.println("in doPost");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		String username = request.getParameter("username");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		System.out.println(latitude + " " + longitude);
//		String ticketNumber = "123";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// protocol: connecting from jdbc to mysql//hostname:port/database_name
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/NoExcuses?user=root&password=lol123&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM Users WHERE username = ?");
			ps.setString(1, username);

			rs = ps.executeQuery();

			while (rs.next()) {
				// update location in database
				System.out.println(rs.getString("latitude"));
				System.out.println(rs.getString("longitude"));

				ps = conn.prepareStatement("UPDATE Users SET latitude = ?, longitude = ? WHERE username = ?");
				ps.setString(1, latitude);
				ps.setString(2, longitude);
				ps.setString(3, username);
				ps.executeUpdate();
				
				
				
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());

		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally { // close streams, databases, etc here
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
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing streams: " + sqle.getMessage());
			}
		}

	}

}
