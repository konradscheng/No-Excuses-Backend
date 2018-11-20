
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

import org.json.JSONArray;

/**
 * Servlet implementation class SearchUsers
 */
@WebServlet("/SearchUsers")
public class SearchUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String searchQuery = request.getParameter("search");
		String searchQuery = request.getParameter("search");
		String[] terms = searchQuery.split("\\s+");

		System.out.println("search query:" + searchQuery);
		for (int i = 0; i < terms.length; ++i) {
			terms[i] = "\'%" + terms[i] + "%\'";
			if (i > 0) {
				terms[i] = " OR username LIKE " + terms[i];
			}
		}
		searchQuery = "";
		for (String t : terms) {
			System.out.println("terms: " + t);
			searchQuery += t;
		}
		System.out.println("search query:" + searchQuery);

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		JSONArray users = new JSONArray();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// protocol: connecting from jdbc to mysql//hostname:port/database_name
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/NoExcuses?user=root&password=lol123&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM Users WHERE username LIKE " + searchQuery + " ORDER BY username");
//			ps.setString(1, searchQuery);

			rs = ps.executeQuery();

			while (rs.next()) {
				// populate data structure of searched users
				System.out.println(rs.getString("username"));
				users.put(rs.getString("username"));

			}

			response.setStatus(200);
			response.setHeader("users", users.toString());
			// return list of found users
			// set status code to 200 for success, 400 for fail
			// [names, names2, name2]

		} catch (SQLException sqle) {
			response.setStatus(400);
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
