
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class TicketAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TicketAuthentication() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("in init method");
	}

	// http;//localhost:8080/CSCI201-Servlets/First?fname=pat&lname=pacheco
	// ? separates website from data
	// & separates keys
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In service method");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		String ticketNumber = request.getParameter("ticketNumber");
		System.out.println(ticketNumber);
//		String ticketNumber = "123";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// protocol: connecting from jdbc to mysql//hostname:port/database_name
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/NoExcuses?user=root&password=lol123&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM NoExcuses.Tickets\n" + "	WHERE ticketNumber = ?;");
			ps.setString(1, ticketNumber);

			rs = ps.executeQuery();
			Boolean validated = false;
			while (rs.next()) {
				String v = rs.getString("validated");
				System.out.println(v);
				if (v.compareTo("yes") == 0) {
					// ticket already validated use a different one
					System.out.println("ticket already validated");
					return;
				} else {
					validated = true;
					// update the sql database so that this ticketnumber is now validated
					ps = conn.prepareStatement("UPDATE Tickets SET validated = 'yes' WHERE ticketNumber = ?");
					ps.setString(1, ticketNumber);
					ps.executeUpdate();
				}

			}
			if (validated) {
				// send
				System.out.println("validated");
			} else {
				// send
				System.out.println("not validated");
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

		String firstName = request.getParameter("fname");
		// authenticate login or send email if its an email

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String nextPage;
		if (firstName != null && firstName.equalsIgnoreCase("123")) {
			nextPage = "/success.jsp";
		} else {
			request.setAttribute("fnameError", "not validated.");
			nextPage = "/index.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
