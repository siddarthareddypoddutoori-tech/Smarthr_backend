const express = require("express");
const db = require("./db");
require("dotenv").config();

const app = express();
app.use(express.json());

db.getConnection()
  .then((connection) => {
    console.log("Successfully connected to MySQL database");
    connection.release();
  })
  .catch((err) => {
    console.error("Database connection failed:", err.message);
  });

app.get("/", (req, res) => {
  res.send("Backend API is running...");
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
