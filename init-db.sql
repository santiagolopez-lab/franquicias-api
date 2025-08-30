CREATE DATABASE IF NOT EXISTS franquicias_db;

USE franquicias_db;

-- Verify database and user creation
SELECT 'Database and user setup completed' as status;

-- Create tables will be handled by Flyway migrations in the application
SELECT 'Flyway will handle table creation' as migration_info;
