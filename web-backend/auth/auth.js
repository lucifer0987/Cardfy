const express = require("express");
const { User } = require("../models/user");
const Joi = require("joi");
const bcrypt = require("bcrypt");
const route = express.Router();

route.post("/", async (req, res) => {
  const { error } = validateAuth(req.body);
  if (error) return res.status(400).send(error.details[0].message);
  const user = await User.findOne({ email: req.body.email });
  if (!user) return res.status(404).send("invalid User");
  const isValid = await bcrypt.compare(req.body.password, user.password);
  if (!isValid) return res.status(404).send("wrong pass");
  const token = user.generateAuthToken();
  res.header("x-auth-token", token).send(user);
});

module.exports = route;

function validateAuth(user) {
  const schema = {
    email: Joi.string().email().required(),
    password: Joi.string().required(),
  };
  return Joi.validate(user, schema);
}
