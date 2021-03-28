const mongoose = require("mongoose");
const winston = require("winston");
module.exports = async function () {
  try {
    await mongoose.connect(
      "mongodb+srv://Ishaan:qwertyforcardfy@cluster0.jwxjl.mongodb.net/test",
      {
        useNewUrlParser: true,
        useUnifiedTopology: true,
      }
    );
    winston.info("Connected to MongoDb");
  } catch (ex) {
    winston.error(ex);
  }
};
