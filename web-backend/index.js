const express = require("express");
const app = express();
const users = require("./routes/users");
const auth = require("./auth/auth");
const home = require("./routes/home");
const cards = require("./routes/cards");
require("./loging/loging")();
require("./db/db")();
app.use(express.json());
app.use("/", home);
app.use("/api/users", users);
app.use("/api/auth", auth);
app.use("/api/card", cards);
const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`listning to port ${port}`));
