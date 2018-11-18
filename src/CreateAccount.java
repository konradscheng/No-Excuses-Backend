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
 * Servlet implementation class CreateAccount
 */
@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
	    Statement st = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String username = "Tom";
	    String password = "Cat";
	    String phonenumber = "123-456-7890";
	    String email = "email@gmail.com";
	    String lattitude = null;
	    String longitude = null;
	    String loggedin = "no";
	    
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost/NoExcuses?user=root&password=sqlpassword&useSSL=false&allowPublicKeyRetrieval=true");
			
	        ps = conn.prepareStatement("INSERT INTO Users (username, userpassword, phonenumber, email, lattitude, longitude, loggedin) VALUES(?, ?, ?, ?, ?, ?, ?)");
	        ps.setString(1, username);
	        ps.setString(2, password);
	        ps.setString(3, phonenumber);
	        ps.setString(4, email);
	        ps.setString(5, lattitude);
	        ps.setString(6, longitude);
	        ps.setString(7, loggedin);
	        
	        ps.execute();
	        
	        System.out.println("New user was successfully created!");
	        //return that user was successfully created
	        
	    } catch (SQLException sqle) {
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

