# delete all tables in schema public on linux
# variables might differ, if used in different environments (password/user/database)


deleteAll() {
	PGPASSWORD=$3 psql -h localhost -U $1 -d $2 -c 'DROP SCHEMA public CASCADE;'
	PGPASSWORD=$3 psql -h localhost -U $1 -d $2 -c 'CREATE SCHEMA public;'
	PGPASSWORD=$3 psql -h localhost -U $1 -d $2 -c 'GRANT ALL ON SCHEMA public TO postgres;'
	PGPASSWORD=$3 psql -h localhost -U $1 -d $2 -c 'GRANT ALL ON SCHEMA public TO public;'
}

# MODIFY THIS TO FIT YOUR CONFIGURATIONS
password="buhinalba"
user="buhinalba"
database="project_dbshema"

deleteAll $user $database $password
