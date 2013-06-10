package broker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import securerest.SOCJob;

import com.google.gson.Gson;

public class MetadataStoreConnection {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public  MetadataStoreConnection(String url) throws Exception {

		// TODO Auto-generated constructor stub
		try {

			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			connect = DriverManager
					.getConnection(url);
		}
		catch (Exception e) {
			throw e;
		} finally {
			//close();
		}


	}


	public boolean addSOCResource(BrokerSOCResource resource)

	{
		Gson gson = new Gson();

		String resource_json = gson.toJson(resource);

		PreparedStatement statement;
		try {
			String query= "INSERT INTO resources VALUES ('"+resource.getResource_id()+"','"+resource_json+"')";
			System.out.println(query);
			statement = connect.prepareStatement(query);
			return  statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}


	public String getSOCResource(String resourceID)

	{
		PreparedStatement statement;
		try {
			String query  ="SELECT * FROM resources WHERE RESOURCE_ID='"+resourceID+"'";
			statement = connect.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String resource_description = resultSet.getString("resource");
				System.out.println("Resource: " + resource_description);
				return resource_description;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean addSOCJob(SOCJob job)
	{
		Gson gson = new Gson();

		String job_json = gson.toJson(job);

		PreparedStatement statement;
		try {
			String query= "INSERT INTO jobs VALUES ('"+job.getJob_Id()+"','"+job_json+"')";

			System.out.println(query);
			statement = connect.prepareStatement(query);
			return  statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public String getSOCJob(String jobID)

	{
		PreparedStatement statement;
		try {
			String query  ="SELECT * FROM jobs WHERE JOB_ID='"+jobID+"'";
			statement = connect.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String job_description = resultSet.getString("job");
				System.out.println("JOB: " + job_description);
				return job_description;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}