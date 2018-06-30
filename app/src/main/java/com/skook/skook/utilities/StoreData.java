package com.skook.skook.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ahmed on 4/24/2016.
 */
public class StoreData {
    String DATABASE_NAME = "sand.ubicall";
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public StoreData(Context ctx) {
        super();
        this.context = ctx;

        sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void clearData() {
        setToken("");
        editor.clear().apply();
        editor.remove("data").apply();
    }

    public void saveData(String category) {
        editor.putString("data", category);
        editor.apply();
    }

    public String getData() {
        return sharedPreferences.getString("data", "");
    }

    public void saveAuthName(String category) {
        editor.putString("AuthName", category);
        editor.apply();
    }

    public String getAuthName() {
        return sharedPreferences.getString("AuthName", "");
    }

    public void saveAuthPass(String category) {
        editor.putString("AuthPass", category);
        editor.apply();
    }

    public String getAuthPass() {
        return sharedPreferences.getString("AuthPass", "");
    }

    public void saveEmail(String category) {
        editor.putString("Email", category);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString("Email", "");
    }

    public void saveFullName(String category) {
        editor.putString("FullName", category);
        editor.apply();
    }

    public String getFullName() {
        return sharedPreferences.getString("FullName", "");
    }

    public void saveMobile(String category) {
        editor.putString("Mobile", category);
        editor.apply();
    }

    public String getMobile() {
        return sharedPreferences.getString("Mobile", "");
    }

    public void saveZone(String category) {
        editor.putString("Zone", category);
        editor.apply();
    }

    public String getZone() {
        return sharedPreferences.getString("Zone", "");
    }

    public void saveWidget(String category) {
        editor.putString("Widget", category);
        editor.apply();
    }

    public String getWidget() {
        return sharedPreferences.getString("Widget", "");
    }

    public void saveStreet(String category) {
        editor.putString("Street", category);
        editor.apply();
    }

    public String getStreet() {
        return sharedPreferences.getString("Street", "");
    }

    public void saveGada(String category) {
        editor.putString("Gada", category);
        editor.apply();
    }

    public String getGada() {
        return sharedPreferences.getString("Gada", "");
    }

    public void saveHouse(String category) {
        editor.putString("House", category);
        editor.apply();
    }

    public String getHouse() {
        return sharedPreferences.getString("House", "");
    }

    public void savLogin(String category) {
        editor.putString("login", category);
        editor.apply();
    }

    public String getLogin() {
        return sharedPreferences.getString("login", "");
    }

    public void saveCartAdded(int cart) {
        editor.putInt("cart", cart);
        editor.apply();
    }

    public int getCartAdded() {
        return sharedPreferences.getInt("cart", 0);
    }

    public void setDialogType(String type_dialog) {
        editor.putString("type_dialog", type_dialog);
        editor.apply();
    }

    public String getDialogType() {
        return sharedPreferences.getString("type_dialog", "value");
    }

    public void savePage(String category) {
        editor.putString("page", category);
        editor.apply();
    }

    public String getPage() {
        return sharedPreferences.getString("page", "");
    }

    public String getToken() {
        return sharedPreferences.getString("tokens", "");

    }

    public void setToken(String token) {
        editor.putString("tokens", token);
        editor.commit();
    }

    public String getPicture() {
        return sharedPreferences.getString("picture", "");

    }

    public void setPicture(String picture) {
        editor.putString("picture", picture);
        editor.commit();
    }

    public String getPhone() {
        return sharedPreferences.getString("phones", "");

    }

    public void setPhone(String phone) {
        editor.putString("phones", phone);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString("username", "");

    }

    public void setUserName(String username) {
        editor.putString("username", username);
        editor.commit();
    }

    public String getPassword() {
        return sharedPreferences.getString("password", "");

    }

    public void setPassword(String password) {
        editor.putString("password", password);
        editor.commit();
    }
    public int getUserId() {
        return sharedPreferences.getInt("userId", 0);

    }

    public void setUserId(int userId) {
        editor.putInt("userId", userId);
        editor.commit();
    }

    public String getAdd() {
        return sharedPreferences.getString("add", "");

    }

    public void setAdd(String add) {
        editor.putString("add", add);
        editor.commit();
    }

    public String getAdded() {
        return sharedPreferences.getString("added", "");

    }

    public void setAdded(String add) {
        editor.putString("added", add);
        editor.commit();
    }

    public String getHomeTitleProduct() {
        return sharedPreferences.getString("home_title", "");

    }

    public void setHomeTitleProduct(String add) {
        editor.putString("home_title", add);
        editor.commit();
    }

    public double getShippingCost() {
        return sharedPreferences.getFloat("shipping_cost", 0);

    }

    public void setShippingCost(float add) {
        editor.putFloat("shipping_cost", add);
        editor.commit();
    }

    public double getMinCartOrder() {
        return sharedPreferences.getFloat("min_cart_order", 0);

    }

    public void setMinCartOrder(float add) {
        editor.putFloat("min_cart_order", add);
        editor.commit();
    }

    public int getAllowOrder() {
        return sharedPreferences.getInt("allow_order", 0);

    }

    public void setAllowOrder(int add) {
        editor.putInt("allow_order", add);
        editor.commit();
    }
}
