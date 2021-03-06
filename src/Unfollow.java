

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
 * Servlet implementation class Unfollow
 */
@WebServlet("/Unfollow")
public class Unfollow extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Unfollow() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
	    Statement st = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String username = request.getParameter("username");
	    String friendusername = request.getParameter("friendusername");
	    
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost/NoExcuses?user=root&password=sqlpassword&useSSL=false&allowPublicKeyRetrieval=true");
			
	        ps = conn.prepareStatement("DELETE FROM Friends WHERE username=? AND friendusername=?");
	        ps.setString(1, username);
	        ps.setString(2, friendusername);
	        
	        ps.execute();
	        
	        System.out.println("Friend was successfully unfollowed!");
	    	response.setStatus(200);
	        //return that friend was successfully unfollowed (200)
	        //return fail (400)
	        
	    } catch (SQLException sqle) {
	    	response.setStatus(400);
	    	System.out.println (sqle.getMessage());
	    } catch (ClassNotFoundException cnfe) {
	    	System.out.println (cnfe.getMessage());
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
