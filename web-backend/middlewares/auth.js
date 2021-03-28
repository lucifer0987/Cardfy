const jwt = require("jsonwebtoken");
const winston = require("winston");

module.exports = function (req, res, next) {
  const token = req.header("x-auth-token");
  if (!token) return res.status(401).send("Acess Denied ...");
  try {
    const decoded = jwt.verify(token, "privatekey");
    req.user = decoded;
    next();
  } catch (ex) {
    winston.error("something failed");
  }
};
