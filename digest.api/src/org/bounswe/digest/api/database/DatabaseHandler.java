package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;

public class DatabaseHandler {
	private Connection connection;

	public DatabaseHandler() {
		this.connection = ConnectionPool.getConnection();

	}

	/**
	 * Calls the function 'functionname' in the database with arguments 'args'
	 * and returns result of it.
	 * 
	 * @param functionName
	 * @param args
	 * @return
	 * @throws ManagerException
	 */
	public ResultSet callSQLFunction(String functionName, String... args) {

		java.sql.DatabaseMetaData dbMetaData = null;
		ArrayList<String> argTypes = new ArrayList<String>();
		try {
			dbMetaData = connection.getMetaData();
			ResultSet frs = dbMetaData.getProcedureColumns(null, "sc_softdev4u", functionName, null);
			while (frs.next()) {
				if (frs.getShort("COLUMN_TYPE") == DatabaseMetaData.procedureColumnIn) {
					argTypes.add(frs.getString("TYPE_NAME"));
				}

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		int size = args.length;
		String qMarks = " (";
		for (int i = 0; i < size; ++i) {
			qMarks += "?,";
		}
		if (size > 0) {
			qMarks = qMarks.substring(0, qMarks.length() - 1);
		}
		qMarks += ")";
		CallableStatement statement;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call " + "sc_softdev4u." + functionName + qMarks + "}");
			System.out.println(argTypes);
			for (int i = 0; i < size; ++i) {
				// System.out.println("Argument : " + (i+1) );

				switch (argTypes.get(i)) {
				case "int4":
					statement.setInt(i + 1, Integer.parseInt(args[i]));
					break;
				case "int8":
					statement.setLong(i + 1, Long.parseLong(args[i]));
					break;
				case "int2":
					statement.setShort(i + 1, Short.parseShort(args[i]));
					break;
				case "text":
					statement.setString(i + 1, args[i]);
					break;
				case "timestamptz":

					break;
				case "bool":
					statement.setBoolean(i + 1, Boolean.parseBoolean(args[i]));
					break;
				default:

					System.out.println(argTypes.get(i));
					System.exit(-1);
				}

			}
			// TODO delete this
			System.out.println("STATEMENT : " + statement.toString());
			statement.execute();
			try {
				resultSet = statement.getResultSet();
			} catch (SQLException e) {
				// ("Unable to get results" + functionName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// ("Unable to do the function " + functionName);
		}

		return resultSet;
	}
}
