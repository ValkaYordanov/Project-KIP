package dbConnect;

import java.sql.*;

public class ConnectDB
{   //Constants used to get access to the database
	private static final String dbServer = "kraka.ucn.dk;";	//Insert your own server name here!
    private static final String dbDb = "dmaj0918_1074261;";
    private static String dbLoginUser = "dmaj0918_1074261;";		// Careful! Plain text username/PW
    private static String dbPassword = "Password1!";	// Careful! Plain text username/PW
   
    private static boolean isWindowsAuthentication = false; // Choose Windows/SQL Server Authentication here
    
    private static String connectionString;
	
	
	private static DatabaseMetaData dma;
	private static Connection con;
	// an instance of the class is generated
	private static ConnectDB  instance = null;

	// the constructor is private to ensure that only one object of this class is created
	private ConnectDB()
	{
		if (isWindowsAuthentication) {
			connectionString = "jdbc:sqlserver://" + dbServer + "databaseName=" + dbDb + "integratedSecurity=true";
		}
		else {
			connectionString = "jdbc:sqlserver://" + dbServer + "databaseName=" + dbDb + "user=" + dbLoginUser + "password=" + dbPassword;
		}

		try{
			//load af driver
			//SQL Server
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Load af class ok");

		}
		catch(Exception e){
			System.out.println("Can not find the driver");
			System.out.println(e.getMessage());
		}//end catch
		try{
			//connection to the database

			con = DriverManager.getConnection(connectionString);
			//set autocommit
			con.setAutoCommit(true);
			dma = con.getMetaData(); // get meta data
			System.out.println("Connection to " + dma.getURL());
			System.out.println("Driver " + dma.getDriverName());
			System.out.println("Database product name " + dma.getDatabaseProductName());
		}//end try
		catch(Exception e){

			System.out.println("Problems with the connection to the database");
			System.out.println(e.getMessage());
			System.out.println(connectionString);
		}//end catch
	}//end  constructor

	//closeDb: closes the connection to the database
	public static void closeConnection()
	{
		try{
			con.close();
			System.out.println("The connection is closed");
		}
		catch (Exception e){
			System.out.println("Error trying to close the database " +  e.getMessage());
		}
	}//end closeDB

	//getDBcon: Get-method, returns the connection to the database
	public  Connection getDBcon()
	{
		return con;
	}
	//this method is used to get the instance of the connection
	public static ConnectDB getInstance()
	{
		if (instance == null)
		{
			instance = new ConnectDB();
		}
		return instance;
	}
	public static void startTransaction()
	{ try{
		con.setAutoCommit(false);
	}
	catch(Exception e){
		System.out.println("fail start transaction");
		System.out.println(e.getMessage());
	}
	}
	public static void commitTransaction()
	{ try{
		con.setAutoCommit(true);
	}
	catch(Exception e){
		System.out.println("fail commit transaction");
		System.out.println(e.getMessage());
	}
	}
	public static void rollbackTransaction()
	{ try{
		con.rollback();
		con.setAutoCommit(true);
	}
	catch(Exception e){
		System.out.println("fail rollback transaction");
		System.out.println(e.getMessage());
	}
	}

	public static boolean instanceIsNull() {
		// TODO Auto-generated method stub
		return false;
	}
}
