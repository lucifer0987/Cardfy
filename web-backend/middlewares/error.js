module.exports = function (err, req, res, next) {
  if (err) return res.status(500).send("Something Failed...");
  next();
};
