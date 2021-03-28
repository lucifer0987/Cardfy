const mongoose = require("mongoose");
const Joi = require("joi");
const jwt = require("jsonwebtoken");
const userSchema = mongoose.Schema({
  name: { type: String, min: 3, max: 255, required: true },
  email: { type: String, required: true, unique: true },
  username: { type: String, required: true, unique: true, max: 50 },
  password: { type: String, max: 1024, required: true },
  profile_image: { type: String, required: true },
  isVarified: { type: Boolean, default: false, required: false },
});

userSchema.methods.generateAuthToken = function () {
  const token = jwt.sign(
    {
      _id: this.id,
    },
    "privatekey"
  );
  return token;
};

const User = new mongoose.model("users", userSchema);

function validateUser(user) {
  const schema = {
    name: Joi.string().min(3).max(255).required(),
    email: Joi.string().email().required(),
    username: Joi.string().max(50).required(),
    password: Joi.string().required().min(8),
    profile_image: Joi.string().required(),
  };
  return Joi.validate(user, schema);
}

function validateUserLogin(user) {
  const schema = {
    email: Joi.string().required(),
    password: Joi.string().required().min(8),
  };
  return Joi.validate(user, schema);
}

module.exports.User = User;
module.exports.validateUser = validateUser;
module.exports.validateUserLogin = validateUserLogin;
