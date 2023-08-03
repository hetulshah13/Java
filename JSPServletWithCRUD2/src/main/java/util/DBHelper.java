package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletContext;

public class DBHelper {
	private static Connection connection;
	private static String errorMessage;
	private static String url;
	private static String username;
	private static String password;
	private static String dbSchema;
	private static String driverClassName;
	private static final DBHelper _SELF = new DBHelper();
	
	private DBHelper() {
		// Private Constructor
	}
	
	public static DBHelper getInstance() {
		return _SELF;
	}

	public static void init(String url, String username, String password, String dbSchema, String driverClassName) {
		setUrl(url);
		setUsername(username);
		setPassword(password);
		setDriverClassName(driverClassName);
		setDBSchema(dbSchema);
	}
	
	public static void init(ServletContext context) {
		setDriverClassName(context.getInitParameter("DRIVER_CLASS"));
		setUsername(context.getInitParameter("DATABASE_USER"));
		setPassword(context.getInitParameter("DATABASE_PASSWORD"));
		setUrl(context.getInitParameter("DATABASE_PATH"));
		setDBSchema(context.getInitParameter("DATABASE_SCHEMA"));
	}
	
	public static void connect() {
		try {
			if(!isConnected()) {
				Class.forName(driverClassName);
				connection = DriverManager.getConnection("jdbc:mysql://"+url+"/"+dbSchema+"?characterEncoding=latin1&useConfigs=maxPerformance"
						, username, password);
			}
			System.out.println("DB Connection is successful");
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			errorMessage = ex.getMessage();
			System.err.println("Unable to connect DB");
		}
	}
	
	public static void disconnect() {
		try {
			if(isConnected()) {
				connection.close();
			}
			System.out.println("DB is disconnected");
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			errorMessage = ex.getMessage();
			System.err.println("Unable to disconnect DB");
		}
	}

	public static void clearErrorMessage() {
		DBHelper.errorMessage = "";
	}
	
	public static String getErrorMessage() {
		return errorMessage;
	}

	public static void setUrl(String url) {
		DBHelper.url = url;
	}

	public static void setDBSchema(String dbSchema) {
		DBHelper.dbSchema = dbSchema;
	}

	public static void setUsername(String username) {
		DBHelper.username = username;
	}

	public static void setPassword(String password) {
		DBHelper.password = password;
	}

	public static void setDriverClassName(String driverClassName) {
		DBHelper.driverClassName = driverClassName;
	}
	
	private static void fillParameters(PreparedStatement pstmt, Map<String, Object> params) throws SQLException {
		int pIndex = 0;
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			if(entry.getValue() == null) {
				pstmt.setString(++pIndex, null);
			} else if(entry.getValue().getClass().isArray()) {
				pstmt.setString(++pIndex, Arrays.toString((Object[]) entry.getValue()));
			} else { 
				pstmt.setString(++pIndex, String.valueOf(entry.getValue()));
			}
		}
	}

	public static int executeUpdate(String query, Map<String, Object> params) {
		try {
			if(isConnected()) {
				final PreparedStatement pstmt = connection.prepareStatement(query);
				fillParameters(pstmt, params);
				return pstmt.executeUpdate();
			}
			return -1;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			errorMessage = ex.getMessage();
			return -1;
		}
	}
	
	public static ResultSet executeQuery(String query, Map<String, Object> params) {
		try {
			if(isConnected()) {
				final PreparedStatement pstmt = connection.prepareStatement(query, 
						ResultSet.TYPE_SCROLL_SENSITIVE, 
						ResultSet.CONCUR_UPDATABLE);
				fillParameters(pstmt, params);
				return pstmt.executeQuery();
			}
			return null;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			errorMessage = ex.getMessage();
			return null;
		}
	}
	
	public static boolean isConnected() {
		try {
			return (connection != null && !connection.isClosed());
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			errorMessage = ex.getMessage();
			return false;
		}
	}
}
