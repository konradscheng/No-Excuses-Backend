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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject user = new JSONObject();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/NoExcuses?user=root&password=sqlpassword&useSSL=false&allowPublicKeyRetrieval=true");

			ps = conn.prepareStatement("SELECT * FROM Users WHERE username=? AND userpassword=? AND loggedin=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, "no");

			rs = ps.executeQuery();

			Boolean found = false;

			while (rs.next()) {
				ps = conn.prepareStatement("UPDATE Users SET loggedin='yes' WHERE username=? AND userpassword=?");
				ps.setString(1, username);
				ps.setString(2, password);
				ps.executeUpdate();
				found = true;

				user.put("username", username);
				user.put("password", password);
				user.put("ticketNumber", rs.getString("ticketNumber"));
			}

			if (found) {
				System.out.println("User is authenticated!");
				response.setStatus(200);
				response.setHeader("user", user.toString());
				// return JSON of username, email, ticketNumber
			} else {
				System.out.println("Wrong username/password, user already exists, or user is already logged in!");
				response.setStatus(400);
				// return that log in credentials are wrong or user is already logged in (400)
			}

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
