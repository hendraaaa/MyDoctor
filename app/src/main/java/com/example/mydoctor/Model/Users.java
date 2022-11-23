package com.example.mydoctor.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    private String bidang;
    private String email;
    private String id;
    private String imageURL;
    private String kategori;
    private String password;
    private String username;
    private String status;


    public Users(String bidang, String email, String id, String imageURL, String kategori, String password, String username,String status) {
        this.bidang = bidang;
        this.email = email;
        this.id = id;
        this.imageURL = imageURL;
        this.kategori = kategori;
        this.password = password;
        this.username = username;
        this.status = status;
    }

    public Users(){

    }

    protected Users(Parcel in) {
        id = in.readString();
        email = in.readString();
        username = in.readString();
        imageURL = in.readString();
        kategori = in.readString();
        bidang = in.readString();
        status = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(imageURL);
        dest.writeString(kategori);
        dest.writeString(bidang);
        dest.writeString(status);
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getBidang() {
        return bidang;
    }

    public void setBidang(String bidang) {
        this.bidang = bidang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
