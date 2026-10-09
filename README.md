# Smart HR Management System Backend

Spring Boot backend using PostgreSQL.

## Deploy on Render

1. Create a Render Web Service from this repository in the same region as your PostgreSQL database, and select **Blueprint** so Render reads `render.yaml`. The configured database hostname is Render's internal hostname.
2. When prompted, set `DB_USERNAME` and `DB_PASSWORD` to the credentials for your Render PostgreSQL database. The JDBC URL in the blueprint targets that database; credentials are not stored in this repository.
3. Deploy the service. The blueprint generates a `JWT_SECRET`, and the application listens on Render's `PORT`.

Keep database credentials and any manually supplied JWT secret in Render's environment settings, never in source control.

## Run locally

Install Java 21 and provide a reachable PostgreSQL database. The defaults expect a local database named `smart_hr`; override them with `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` as needed.

From this directory on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The API listens on `http://localhost:8080` unless `PORT` is set.
