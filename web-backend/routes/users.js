const express = require("express");
const bcrypt = require("bcrypt");
const { User, validateUser, validateUserLogin } = require("../models/user");
const { Card } = require("../models/card");
const auth = require("../middlewares/auth");
const route = express.Router();

route.post("/", async (req, res) => {
  const { error } = validateUser(req.body);
  if (error) return res.status(400).send(error.details[0].message);
  let user_by_email = await User.findOne({ email: req.body.email });
  if (user_by_email) return res.status(401).send("User Already Exists");
  let user_by_username = await User.findOne({ username: req.body.username });
  if (user_by_username)
    return res.status(405).send("Username already taken ! ");

  const salt = await bcrypt.genSalt(10);
  const hashedPassword = bcrypt.hashSync(req.body.password, salt);
  user = new User({
    name: req.body.name,
    email: req.body.email,
    password: hashedPassword,
    username: req.body.username,
    profile_image: req.body.profile_image,
  });
  card = new Card({
    name: req.body.name,
    email: req.body.email,
    username: req.body.username,
    profile_image: req.body.profile_image,
  });

  const token = user.generateAuthToken();

  const result_user = await user.save();
  const result_card = await card.save();
  if (result_user && result_card)
    res.header("x-auth-token", token).send(result_user);
});

route.post("/login", async (req, res) => {
  const { error } = validateUserLogin(req.body);
  if (error) return res.status(400).send(error.details[0].message);
  let user_by_email = await User.findOne({ email: req.body.email });
  let user_by_username = await User.findOne({ username: req.body.email });
  if (!user_by_email && !user_by_username)
    return res.status(404).send("user email / username not registered");
  let user = user_by_email;
  if (user_by_email) user = user_by_email;
  else user = user_by_username;

  const isValid = await bcrypt.compare(req.body.password, user.password);
  if (!isValid)
    return res.status(403).send("Invalid password for the given email ");
  const token = user.generateAuthToken();

  res.status(200).send({
    token: token,
    sucess: true,
    name: user.name,
    username: user.username,
    email: user.email,
    profile_image: user.profile_image,
  });
});

route.get("/me", auth, async (req, res) => {
  const user = await User.findById(req.user._id);
  res.status(200).send(user);
});

module.exports = route;
