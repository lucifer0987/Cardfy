const express = require("express");
const { Card, validateCard } = require("../models/card");
const auth = require("../middlewares/auth");
const route = express.Router();

route.post("/", auth, async (req, res) => {
  const { error } = validateCard(req.body);
  if (error) return res.status(400).send(error.details[0].message);
  let card = await Card.findOne({ email: req.body.email });
  if (card) return res.status(405).send("Card alredy exists");
  card = new Card({
    name: req.body.name,
    email: req.body.email,
    username: req.body.username,
    profile_image: req.body.profile_image,
    phone: req.body.phone,
    facebook: req.body.facebook,
    instagram: req.body.instagram,
    twitter: req.body.twitter,
    linkedin: req.body.linkedin,
    whatsapp: req.body.whatsapp,
    gmail: req.body.gmail,
    discord: req.body.discord,
    slack: req.body.slack,
    skype: req.body.skype,
    youtube: req.body.youtube,
    intro: req.body.intro,
    short_desc: req.body.short_desc,
    long_desc: req.body.long_desc,
    address: req.body.address,
    city: req.body.city,
    state: req.body.state,
    country: req.body.country,
    address_slug: req.body.address_slug,
    upi: req.body.upi,
    gpay: req.body.gpay,
    ppay: req.body.ppay,
    paytm: req.body.paytm,
    fund_desc: req.body.fund_desc,
  });

  const result = await card.save();
  if (result) res.status(200).send(result);
});

route.get("/me", auth, async (req, res) => {
  const card = await Card.findOne({ email: req.user.email });
  res.status(200).send(card);
});
route.put("/me", auth, async (req, res) => {
  const card = await Card.findOne({ email: req.user.email });
  if (!card) res.status(400).send("no card for this user");
  card.set({
    name: req.body.name,
    profile_image: req.body.profile_image,
    isVerified: req.body.isVerified,
    phone: req.body.phone,
    facebook: req.body.facebook,
    instagram: req.body.instagram,
    twitter: req.body.twitter,
    linkedin: req.body.linkedin,
    whatsapp: req.body.whatsapp,
    gmail: req.body.gmail,
    discord: req.body.discord,
    slack: req.body.slack,
    skype: req.body.skype,
    youtube: req.body.youtube,
    intro: req.body.intro,
    short_desc: req.body.short_desc,
    long_desc: req.body.long_desc,
    address: req.body.address,
    city: req.body.city,
    state: req.body.state,
    country: req.body.country,
    address_slug: req.body.address_slug,
    upi: req.body.upi,
    gpay: req.body.gpay,
    ppay: req.body.ppay,
    paytm: req.body.paytm,
    fund_desc: req.body.fund_desc,
  });

  const result = await card.save();
  if (result) res.status(200).send(result);
});

route.get("/:username", async (req, res) => {
  const card = await Card.findOne({ username: req.params.username });
  if (card) res.status(200).send(card);
  else res.status(400).send("No card found");
});

module.exports = route;
