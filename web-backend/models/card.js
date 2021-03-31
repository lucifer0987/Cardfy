const mongoose = require("mongoose");
const Joi = require("joi");
const jwt = require("jsonwebtoken");

const cardSchema = mongoose.Schema({
  name: { type: String, min: 3, max: 255, required: true },
  email: { type: String, required: true, unique: true },
  rating: { type: Number, min: 0, max: 5, default: 0 },
  username: { type: String, required: true, unique: true, max: 50 },
  profile_image: { type: String, required: true },
  isVerified: { type: Boolean, default: false, required: false },
  phone: { type: String, min: 10, max: 10 },
  facebook: { type: String, default: "" },
  instagram: { type: String, default: "" },
  twitter: { type: String, default: "" },
  linkedin: { type: String, default: "" },
  whatsapp: { type: String, default: "" },
  gmail: { type: String, default: "" },
  discord: { type: String, default: "" },
  slack: { type: String, default: "" },
  skype: { type: String, default: "" },
  youtube: { type: String, default: "" },
  intro: { type: String, default: "" },
  short_desc: { type: String, default: "" },
  long_desc: { type: String, default: "" },
  address: { type: String, default: "" },
  city: { type: String, default: "" },
  state: { type: String, default: "" },
  country: { type: String, default: "" },
  address_slug: { type: String, default: "" },
  upi: { type: String, default: "" },
  gpay: { type: String, default: "" },
  ppay: { type: String, default: "" },
  paytm: { type: String, default: "" },
  fund_desc: { type: String, default: "" },
});
const Card = new mongoose.model("cards", cardSchema);

function validateCard(card) {
  const schema = {
    name: Joi.string().min(3).max(255).required(),
    email: Joi.string().email().required(),
    username: Joi.string().max(50).required(),
    profile_image: Joi.string().required(),
    rating: Joi.number().min(0).max(5),
    isVerified: Joi.boolean().default(false),
    phone: Joi.string().min(10).max(10),
    facebook: Joi.string(),
    instagram: Joi.string(),
    twitter: Joi.string(),
    linkedin: Joi.string(),
    whatsapp: Joi.string(),
    gmail: Joi.string(),
    discord: Joi.string(),
    slack: Joi.string(),
    skype: Joi.string(),
    youtube: Joi.string(),
    intro: Joi.string(),
    short_desc: Joi.string(),
    long_desc: Joi.string(),
    address: Joi.string(),
    city: Joi.string(),
    state: Joi.string(),
    country: Joi.string(),
    address_slug: Joi.string(),
    upi: Joi.string(),
    gpay: Joi.string(),
    ppay: Joi.string(),
    paytm: Joi.string(),
    fund_desc: Joi.string(),
  };
  return Joi.validate(card, schema);
}

module.exports.Card = Card;
module.exports.validateCard = validateCard;
