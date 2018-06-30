package com.skook.skook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferData {

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("offer_type")
    @Expose
    private String offerType;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("cat")
    @Expose
    private String cat;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("edge")
    @Expose
    private String edge;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("offer_date")
    @Expose
    private String offerDate;
    @SerializedName("offer_update")
    @Expose
    private Object offerUpdate;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("mobile_other")
    @Expose
    private Object mobileOther;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("street_num")
    @Expose
    private Object streetNum;
    @SerializedName("pay")
    @Expose
    private Object pay;
    @SerializedName("map")
    @Expose
    private String map;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("show")
    @Expose
    private String show;
    @SerializedName("villa_type")
    @Expose
    private Object villaType;
    @SerializedName("ground_type")
    @Expose
    private Object groundType;
    @SerializedName("3omara_type")
    @Expose
    private Object _3omaraType;
    @SerializedName("business")
    @Expose
    private Object business;
    @SerializedName("hay")
    @Expose
    private String hay;
    @SerializedName("contact_mobile")
    @Expose
    private Object contactMobile;
    @SerializedName("contact_sms")
    @Expose
    private Object contactSms;
    @SerializedName("contact_whatsapp")
    @Expose
    private Object contactWhatsapp;
    @SerializedName("wag_details")
    @Expose
    private Object wagDetails;
    @SerializedName("depth")
    @Expose
    private Object depth;
    @SerializedName("block")
    @Expose
    private Object block;
    @SerializedName("molhaq")
    @Expose
    private Object molhaq;
    @SerializedName("molhaq_num")
    @Expose
    private Object molhaqNum;
    @SerializedName("poet")
    @Expose
    private Object poet;
    @SerializedName("kich")
    @Expose
    private Object kich;
    @SerializedName("pool")
    @Expose
    private Object pool;
    @SerializedName("car")
    @Expose
    private Object car;
    @SerializedName("garage")
    @Expose
    private Object garage;
    @SerializedName("spec")
    @Expose
    private Object spec;
    @SerializedName("villa_area")
    @Expose
    private Object villaArea;
    @SerializedName("chambers")
    @Expose
    private Object chambers;
    @SerializedName("path_no")
    @Expose
    private Object pathNo;
    @SerializedName("villa_age")
    @Expose
    private Object villaAge;
    @SerializedName("stairs")
    @Expose
    private Object stairs;
    @SerializedName("sheqa1_area")
    @Expose
    private Object sheqa1Area;
    @SerializedName("sheqa1_chambers")
    @Expose
    private Object sheqa1Chambers;
    @SerializedName("sheqa1_path_no")
    @Expose
    private Object sheqa1PathNo;
    @SerializedName("sheqa2_area")
    @Expose
    private Object sheqa2Area;
    @SerializedName("sheqa2_chambers")
    @Expose
    private Object sheqa2Chambers;
    @SerializedName("sheqa2_path_no")
    @Expose
    private Object sheqa2PathNo;
    @SerializedName("sheqa3_area")
    @Expose
    private Object sheqa3Area;
    @SerializedName("sheqa3_chambers")
    @Expose
    private Object sheqa3Chambers;
    @SerializedName("sheqa3_path_no")
    @Expose
    private Object sheqa3PathNo;
    @SerializedName("majles")
    @Expose
    private Object majles;
    @SerializedName("w_majles")
    @Expose
    private Object wMajles;
    @SerializedName("interance")
    @Expose
    private Object interance;
    @SerializedName("hole")
    @Expose
    private Object hole;
    @SerializedName("dka")
    @Expose
    private Object dka;
    @SerializedName("mostodaa")
    @Expose
    private Object mostodaa;
    @SerializedName("garden")
    @Expose
    private Object garden;
    @SerializedName("left")
    @Expose
    private Object left;
    @SerializedName("shorfa")
    @Expose
    private Object shorfa;
    @SerializedName("split")
    @Expose
    private Object split;
    @SerializedName("aircondition")
    @Expose
    private Object aircondition;
    @SerializedName("window")
    @Expose
    private Object window;
    @SerializedName("view")
    @Expose
    private Object view;
    @SerializedName("men_council")
    @Expose
    private Object menCouncil;
    @SerializedName("inner_kitchen")
    @Expose
    private Object innerKitchen;
    @SerializedName("dining_room")
    @Expose
    private Object diningRoom;
    @SerializedName("mqlt")
    @Expose
    private Object mqlt;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("city_title")
    @Expose
    private String cityTitle;
    @SerializedName("region_title")
    @Expose
    private String regionTitle;
    @SerializedName("offer_title")
    @Expose
    private String offerTitle;
    @SerializedName("url")
    @Expose
    private String url;


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public Object getOfferUpdate() {
        return offerUpdate;
    }

    public void setOfferUpdate(Object offerUpdate) {
        this.offerUpdate = offerUpdate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getMobileOther() {
        return mobileOther;
    }

    public void setMobileOther(Object mobileOther) {
        this.mobileOther = mobileOther;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Object getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(Object streetNum) {
        this.streetNum = streetNum;
    }

    public Object getPay() {
        return pay;
    }

    public void setPay(Object pay) {
        this.pay = pay;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public Object getVillaType() {
        return villaType;
    }

    public void setVillaType(Object villaType) {
        this.villaType = villaType;
    }

    public Object getGroundType() {
        return groundType;
    }

    public void setGroundType(Object groundType) {
        this.groundType = groundType;
    }

    public Object get3omaraType() {
        return _3omaraType;
    }

    public void set3omaraType(Object _3omaraType) {
        this._3omaraType = _3omaraType;
    }

    public Object getBusiness() {
        return business;
    }

    public void setBusiness(Object business) {
        this.business = business;
    }

    public String getHay() {
        return hay;
    }

    public void setHay(String hay) {
        this.hay = hay;
    }

    public Object getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(Object contactMobile) {
        this.contactMobile = contactMobile;
    }

    public Object getContactSms() {
        return contactSms;
    }

    public void setContactSms(Object contactSms) {
        this.contactSms = contactSms;
    }

    public Object getContactWhatsapp() {
        return contactWhatsapp;
    }

    public void setContactWhatsapp(Object contactWhatsapp) {
        this.contactWhatsapp = contactWhatsapp;
    }

    public Object getWagDetails() {
        return wagDetails;
    }

    public void setWagDetails(Object wagDetails) {
        this.wagDetails = wagDetails;
    }

    public Object getDepth() {
        return depth;
    }

    public void setDepth(Object depth) {
        this.depth = depth;
    }

    public Object getBlock() {
        return block;
    }

    public void setBlock(Object block) {
        this.block = block;
    }

    public Object getMolhaq() {
        return molhaq;
    }

    public void setMolhaq(Object molhaq) {
        this.molhaq = molhaq;
    }

    public Object getMolhaqNum() {
        return molhaqNum;
    }

    public void setMolhaqNum(Object molhaqNum) {
        this.molhaqNum = molhaqNum;
    }

    public Object getPoet() {
        return poet;
    }

    public void setPoet(Object poet) {
        this.poet = poet;
    }

    public Object getKich() {
        return kich;
    }

    public void setKich(Object kich) {
        this.kich = kich;
    }

    public Object getPool() {
        return pool;
    }

    public void setPool(Object pool) {
        this.pool = pool;
    }

    public Object getCar() {
        return car;
    }

    public void setCar(Object car) {
        this.car = car;
    }

    public Object getGarage() {
        return garage;
    }

    public void setGarage(Object garage) {
        this.garage = garage;
    }

    public Object getSpec() {
        return spec;
    }

    public void setSpec(Object spec) {
        this.spec = spec;
    }

    public Object getVillaArea() {
        return villaArea;
    }

    public void setVillaArea(Object villaArea) {
        this.villaArea = villaArea;
    }

    public Object getChambers() {
        return chambers;
    }

    public void setChambers(Object chambers) {
        this.chambers = chambers;
    }

    public Object getPathNo() {
        return pathNo;
    }

    public void setPathNo(Object pathNo) {
        this.pathNo = pathNo;
    }

    public Object getVillaAge() {
        return villaAge;
    }

    public void setVillaAge(Object villaAge) {
        this.villaAge = villaAge;
    }

    public Object getStairs() {
        return stairs;
    }

    public void setStairs(Object stairs) {
        this.stairs = stairs;
    }

    public Object getSheqa1Area() {
        return sheqa1Area;
    }

    public void setSheqa1Area(Object sheqa1Area) {
        this.sheqa1Area = sheqa1Area;
    }

    public Object getSheqa1Chambers() {
        return sheqa1Chambers;
    }

    public void setSheqa1Chambers(Object sheqa1Chambers) {
        this.sheqa1Chambers = sheqa1Chambers;
    }

    public Object getSheqa1PathNo() {
        return sheqa1PathNo;
    }

    public void setSheqa1PathNo(Object sheqa1PathNo) {
        this.sheqa1PathNo = sheqa1PathNo;
    }

    public Object getSheqa2Area() {
        return sheqa2Area;
    }

    public void setSheqa2Area(Object sheqa2Area) {
        this.sheqa2Area = sheqa2Area;
    }

    public Object getSheqa2Chambers() {
        return sheqa2Chambers;
    }

    public void setSheqa2Chambers(Object sheqa2Chambers) {
        this.sheqa2Chambers = sheqa2Chambers;
    }

    public Object getSheqa2PathNo() {
        return sheqa2PathNo;
    }

    public void setSheqa2PathNo(Object sheqa2PathNo) {
        this.sheqa2PathNo = sheqa2PathNo;
    }

    public Object getSheqa3Area() {
        return sheqa3Area;
    }

    public void setSheqa3Area(Object sheqa3Area) {
        this.sheqa3Area = sheqa3Area;
    }

    public Object getSheqa3Chambers() {
        return sheqa3Chambers;
    }

    public void setSheqa3Chambers(Object sheqa3Chambers) {
        this.sheqa3Chambers = sheqa3Chambers;
    }

    public Object getSheqa3PathNo() {
        return sheqa3PathNo;
    }

    public void setSheqa3PathNo(Object sheqa3PathNo) {
        this.sheqa3PathNo = sheqa3PathNo;
    }

    public Object getMajles() {
        return majles;
    }

    public void setMajles(Object majles) {
        this.majles = majles;
    }

    public Object getWMajles() {
        return wMajles;
    }

    public void setWMajles(Object wMajles) {
        this.wMajles = wMajles;
    }

    public Object getInterance() {
        return interance;
    }

    public void setInterance(Object interance) {
        this.interance = interance;
    }

    public Object getHole() {
        return hole;
    }

    public void setHole(Object hole) {
        this.hole = hole;
    }

    public Object getDka() {
        return dka;
    }

    public void setDka(Object dka) {
        this.dka = dka;
    }

    public Object getMostodaa() {
        return mostodaa;
    }

    public void setMostodaa(Object mostodaa) {
        this.mostodaa = mostodaa;
    }

    public Object getGarden() {
        return garden;
    }

    public void setGarden(Object garden) {
        this.garden = garden;
    }

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getShorfa() {
        return shorfa;
    }

    public void setShorfa(Object shorfa) {
        this.shorfa = shorfa;
    }

    public Object getSplit() {
        return split;
    }

    public void setSplit(Object split) {
        this.split = split;
    }

    public Object getAircondition() {
        return aircondition;
    }

    public void setAircondition(Object aircondition) {
        this.aircondition = aircondition;
    }

    public Object getWindow() {
        return window;
    }

    public void setWindow(Object window) {
        this.window = window;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public Object getMenCouncil() {
        return menCouncil;
    }

    public void setMenCouncil(Object menCouncil) {
        this.menCouncil = menCouncil;
    }

    public Object getInnerKitchen() {
        return innerKitchen;
    }

    public void setInnerKitchen(Object innerKitchen) {
        this.innerKitchen = innerKitchen;
    }

    public Object getDiningRoom() {
        return diningRoom;
    }

    public void setDiningRoom(Object diningRoom) {
        this.diningRoom = diningRoom;
    }

    public Object getMqlt() {
        return mqlt;
    }

    public void setMqlt(Object mqlt) {
        this.mqlt = mqlt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
