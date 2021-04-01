"""
Query to check if table exists
	SELECT EXISTS (
		SELECT FROM information_schema.tables 
		WHERE  table_schema = 'public'
		AND    table_name   = 'test_table1');

Create Table query
	CREATE TABLE table_name (column_name datatype);

Add new column query (If Table Exists ) 
	ALTER TABLE table_name ADD column_name datatype;
"""








int rowCount = 0;
new File('/home/buhinalba/Downloads/talajdb_schema.csv').eachLine { line ->
	String[]columnData = line.split(',')
	print rowCount
	println Arrays.toString(columnData)
	if (rowCount++ == 0) return;

	try {
		String tableName = columnData[0]
		String columnName = columnData[1]
		String type = columnData[2]
		String length = columnData[3]
		String primaryKey = columnData[4] == 'Yes' ? 'PRIMARY KEY' : ''
		String nullable = columnData[5] == 'No' ? 'NOT NULL' : ''
		String unique = columnData[6] == 'Yes' ? 'UNIQUE' : ''

		def tableExists = sql.rows("SELECT EXISTS ( SELECT FROM information_schema.tables WHERE  table_schema = 'public' AND table_name = ${tableName});")
		if (tableExists[0].exists) {
			// todo : when int needs length resricition, it works differently
			sql.execute("ALTER TABLE \"" + tableName + "\" ADD " + columnName + " " + type + " " + primaryKey + " " + nullable + " " + unique +";")
			println "Table ${tableName} Exists!"
		} else {
			sql.execute("CREATE TABLE \"" + tableName + "\" (" + columnName + " " + type +" " + primaryKey + " " + nullable + " " + unique + ");")
		}
	} catch (Exception e) {
		println e.getMessage()
		println e.getStackTrace().toString()
		// todo add row index to new array with error message
	}
	rowCount++
	// println Arrays.toString(data)
}