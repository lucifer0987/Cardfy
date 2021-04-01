package com.example.cardfy.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {
    @SerializedName("rating")
    @Expose
    private long rating;
    @SerializedName("isVerified")
    @Expose
    private boolean isVerified;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("linkedin")
    @Expose
    private String linkedin;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("gmail")
    @Expose
    private String gmail;
    @SerializedName("discord")
    @Expose
    private String discord;
    @SerializedName("slack")
    @Expose
    private String slack;
    @SerializedName("skype")
    @Expose
    private String skype;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("intro")
    @Expose
    private String intro;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("long_desc")
    @Expose
    private String longDesc;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("address_slug")
    @Expose
    private String addressSlug;
    @SerializedName("upi")
    @Expose
    private String upi;
    @SerializedName("gpay")
    @Expose
    private String gpay;
    @SerializedName("ppay")
    @Expose
    private String ppay;
    @SerializedName("paytm")
    @Expose
    private String paytm;
    @SerializedName("fund_desc")
    @Expose
    private String fundDesc;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("__v")
    @Expose
    private long v;

    public Card() {
    }

    public Card(long rating, boolean isVerified, String facebook, String instagram, String twitter, String linkedin, String whatsapp, String gmail, String discord, String slack, String skype, String youtube, String intro, String shortDesc, String longDesc, String address, String city, String state, String country, String addressSlug, String upi, String gpay, String ppay, String paytm, String fundDesc, String id, String name, String email, String username, String profileImage, long v) {
        super();
        this.rating = rating;
        this.isVerified = isVerified;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.whatsapp = whatsapp;
        this.gmail = gmail;
        this.discord = discord;
        this.slack = slack;
        this.skype = skype;
        this.youtube = youtube;
        this.intro = intro;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.addressSlug = addressSlug;
        this.upi = upi;
        this.gpay = gpay;
        this.ppay = ppay;
        this.paytm = paytm;
        this.fundDesc = fundDesc;
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.profileImage = profileImage;
        this.v = v;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public String getSlack() {
        return slack;
    }

    public void setSlack(String slack) {
        this.slack = slack;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressSlug() {
        return addressSlug;
    }

    public void setAddressSlug(String addressSlug) {
        this.addressSlug = addressSlug;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getGpay() {
        return gpay;
    }

    public void setGpay(String gpay) {
        this.gpay = gpay;
    }

    public String getPpay() {
        return ppay;
    }

    public void setPpay(String ppay) {
        this.ppay = ppay;
    }

    public String getPaytm() {
        return paytm;
    }

    public void setPaytm(String paytm) {
        this.paytm = paytm;
    }

    public String getFundDesc() {
        return fundDesc;
    }

    public void setFundDesc(String fundDesc) {
        this.fundDesc = fundDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "Card{" +
                "rating=" + rating +
                ", isVerified=" + isVerified +
                ", facebook='" + facebook + '\'' +
                ", instagram='" + instagram + '\'' +
                ", twitter='" + twitter + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", whatsapp='" + whatsapp + '\'' +
                ", gmail='" + gmail + '\'' +
                ", discord='" + discord + '\'' +
                ", slack='" + slack + '\'' +
                ", skype='" + skype + '\'' +
                ", youtube='" + youtube + '\'' +
                ", intro='" + intro + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", addressSlug='" + addressSlug + '\'' +
                ", upi='" + upi + '\'' +
                ", gpay='" + gpay + '\'' +
                ", ppay='" + ppay + '\'' +
                ", paytm='" + paytm + '\'' +
                ", fundDesc='" + fundDesc + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", v=" + v +
                '}';
    }
}
