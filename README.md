# Smart HR Management System Backend

Spring Boot backend using MySQL locally and PostgreSQL on Render.

## Deploy on Render

1. Create the backend Web Service from this repository in the same region as your PostgreSQL database, and select **Blueprint** so Render reads `render.yaml`. The configured database hostname is Render's internal hostname.
   The service root directory must be the repository root (`.`), where `Dockerfile`, `pom.xml`, and `src/` are located. If configuring the service manually, set its Root Directory to `.` and Dockerfile Path to `./Dockerfile`.
2. When prompted, set `DB_USERNAME` and `DB_PASSWORD` to the credentials for your Render PostgreSQL database. Set `CORS_ALLOWED_ORIGINS` to your deployed frontend's exact HTTPS origin (for example, `https://your-frontend.onrender.com`, without `/api`). The JDBC URL in the blueprint targets the database; credentials are not stored in this repository.
3. Deploy the service. The blueprint generates `JWT_SECRET`, checks `/actuator/health`, and the application listens on Render's `PORT`.
4. Deploy the frontend separately using the Render Blueprint in the frontend repository. Set `VITE_API_BASE_URL` to your backend's public URL ending in `/api` (for example, `https://your-backend.onrender.com/api`).

Keep database credentials and any manually supplied JWT secret in Render's environment settings, never in source control.

## Run locally

Install Java 21 and MySQL Server. In MySQL Workbench, connect to your local server with the settings shown in your connection (typically host `localhost`, port `3306`, and user `root`), then create the schema:

```sql
CREATE DATABASE smart_hr;
```

The application defaults to `jdbc:mysql://localhost:3306/smart_hr` and username `root`. Set `DB_PASSWORD` in your environment to the MySQL password for that user; if your local MySQL root account has no password, it can be left unset. Override `DB_URL` or `DB_USERNAME` if your connection uses different settings. Keep the password out of `application.properties` and source control.

From this directory on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The API listens on `http://localhost:8080` unless `PORT` is set.
