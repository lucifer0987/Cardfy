const winston = require("winston");
module.exports = function () {
  winston.add(new winston.transports.Console({}));
  winston.add(
    new winston.transports.File({
      filename: "logs.log",
    })
  );

  winston.exceptions.handle(
    new winston.transports.File({
      filename: "uncaughtException.log",
    })
  );
};
