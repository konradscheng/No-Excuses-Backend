
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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class FriendLocations
 */
@WebServlet("/FriendLocations")
public class FriendLocations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FriendLocations() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray friendLocations = new JSONArray();

		String username = request.getParameter("username");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/NoExcuses?user=root&password=sqlpassword&useSSL=false&allowPublicKeyRetrieval=true");

			ps = conn.prepareStatement("SELECT * FROM Following WHERE username=?");
			ps.setString(1, username);

			rs = ps.executeQuery();

			while (rs.next()) {
				// populate data structure
				String friendusername = rs.getString("friendname");
				System.out.println(rs.getString("friendname"));
				ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
				ps.setString(1, friendusername);
				ResultSet friendrs = null;
				friendrs = ps.executeQuery();
				while (friendrs.next()) {
					String latitude = friendrs.getString("latitude");
					String longitude = friendrs.getString("longitude");
					System.out.println(friendusername + " " + latitude + " " + longitude);
					// add this information to a data structure
					JSONObject user = new JSONObject();
					user.put(username, new JSONObject().put("latitude", latitude).put("longitudue", longitude));
				}
			}

			response.setStatus(200);
			response.setHeader("friendLocations", friendLocations.toString());

			// return this information
			/*
			 * { [{name:{ latitude: val longitude: val }, ...}] }
			 */

		} catch (SQLException sqle) {
			response.setStatus(400);
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
