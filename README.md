# Import database schema from csv

Import Database schema from a csv file specification! The script is designed to wor with postgresql database, 
but can be used with other sql based relational database managment systems, with a few or none corrections.

### Setup
The Script has to run in a DbSchema environment, where you connect to your database.  
No further configurations will be needed there.

Your csv file can be different from the one that is used in this project, so if you intend to use it, look out for the column order and keywords.

Update the csv file path.

#### Run
If you are running this in DbSchema, just open a new SQL editor, change the interpreter to groovy script, and press 'run script'

If you are running it in another environment, you have to create the connection string in groovy.

#### Connect to Database with groovy
To connect to the database you will need:
- The database url
- Username
- Password