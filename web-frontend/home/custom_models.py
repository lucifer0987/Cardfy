

class User:

    def __init__(self,name,email,username,profile_image,token):

        self.name = name
        self.email = email
        self.username = username
        self.profile_image = profile_image
        self.token = token

    
class Card:
    def __init__(self,
                    name,email,username,profile_image,rating,isVerified,
                    facebook,instagram,twitter,linkedin,whatsapp,gmail,
                    discord,slack,skype,youtube,intro,short_desc,long_desc,
                    address,city,state,country,address_slug,upi,
                    gpay,ppay,paytm,fund_desc):
                    self.name = name
                    self.email=email
                    self.username=username
                    self.profile_image=profile_image
                    self.rating=rating
                    self.isVerified = isVerified

                    self.facebook = facebook
                    self.insagram = instagram
                    self.twitter= twitter
                    self.linkedin = linkedin
                    self.whatsapp = whatsapp
                    self.gmail = gmail
                    

                    self.discord = discord
                    self.slack = slack
                    self.skype = skype
                    self.youtube = youtube
                    self.intro = intro
                    self.short_desc = short_desc
                    self.long_desc = long_desc

                    self.address = address
                    self.city = city
                    self.state=state
                    self.country = country
                    self.address_slug = address_slug
                    
                    self.upi = upi
                    self.gpay = gpay
                    self.ppay = ppay
                    self.paytm = paytm
                    self.fund_desc = fund_desc

                    