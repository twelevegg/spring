Amazon RDS Connection Guide
To connect this application to Amazon RDS (e.g., Aurora or RDS for MySQL) instead of the local Docker container:

1. Prerequisite: Create RDS Instance
   Create a MySQL-compatible RDS instance.
   Ensure the Security Group attached to the RDS instance allows inbound traffic on port 3306 from your application's IP address (or EC2 security group).
   Create the initial databases mydatabase and cdr_db in RDS (or allow the application/init script to do it if permissions allow, though typically done manually or via Terraform for RDS).
2. Update Configuration
   The application is configured to read database credentials from environment variables, with local defaults in
   application.yaml
   .

Recommended Method: Environment Variables Set the following environment variables in your deployment environment (e.g., EC2, ECS, or local IDE run config):

# For "mydatabase" (Call, Customer)
export DATABASE_MYSQL_URL=jdbc:mysql://<RDS_ENDPOINT>:3306/mydatabase?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
export DATABASE_MYSQL_USERNAME=<RDS_MASTER_USERNAME>
export DATABASE_MYSQL_PASSWORD=<RDS_MASTER_PASSWORD>
# For "cdr_db" (Cdr)
export DATABASE_CDR_URL=jdbc:mysql://<RDS_ENDPOINT>:3306/cdr_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
export DATABASE_CDR_USERNAME=<RDS_MASTER_USERNAME>
export DATABASE_CDR_PASSWORD=<RDS_MASTER_PASSWORD>
3. Verification
   After setting the environment variables, restart the application. It will automatically connect to the RDS instance instead of localhost. can verify by checking the startup logs for the connected JDBC URL.