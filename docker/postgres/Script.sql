
CREATE USER seed PASSWORD 'seed' SUPERUSER;
CREATE DATABASE seed_db WITH OWNER seed ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE seed_db TO seed;