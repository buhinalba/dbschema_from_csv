void createSchemaFromCSV(filePath, foreignKeys) {
	int rowCount = 0;
	new File(filePath).eachLine { line ->
		String[] columnData = line.split(',')
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
				sql.execute("ALTER TABLE \"" + tableName + "\" ADD " + columnName + " " + type + " " + primaryKey + " " + nullable + " " + unique + ";")
				println "Table ${tableName} Exists!"
			} else {
				sql.execute("CREATE TABLE \"" + tableName + "\" (" + columnName + " " + type + " " + primaryKey + " " + nullable + " " + unique + ");")
			}

			checkForeignKey(columnData, foreignKeys)
		} catch (Exception e) {
			println e.getMessage()
			println e.getStackTrace().toString()
			return
		}
	}
}


void checkForeignKey(columnData, foreignKeys) {
	if (columnData[7] == "Yes") {
		foreignKeys.add(columnData)
	}
}



void createConstraints(foreignKeys) {
	println ""
	println "FOREIGN KEYS: "
	for (foreignKey: foreignKeys) {
		println Arrays.toString(foreignKey)
		String tableName = foreignKey[0]
		String columnName = foreignKey[1]
		String referenceTable = foreignKey[9]
		String referenceColumn = foreignKey[10]
		String constraintName = "fk_${referenceTable}_${referenceColumn}"
		String query = "ALTER TABLE ONLY ${tableName} ADD CONSTRAINT ${constraintName} FOREIGN KEY (${columnName}) REFERENCES ${referenceTable}(${referenceColumn});"
		sql.execute(query)
	}
}


void main() {
	String FILE_PATH = "/home/buhinalba/Projects/data_engineering/dbschema_from_csv/movies_schema"
	def foreignKeys = []
	createSchemaFromCSV(FILE_PATH, foreignKeys)
	createConstraints(foreignKeys)
}


main()