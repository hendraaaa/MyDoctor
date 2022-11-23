package com.example.mydoctor.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Berita implements Parcelable {
    private String day;
    private String detail;
    private String image;
    private String judul;

    public Berita(String day, String detail, String image, String judul) {
        this.day = day;
        this.detail = detail;
        this.image = image;
        this.judul = judul;
    }
    public Berita(){

    }

    protected Berita(Parcel in) {
        day = in.readString();
        detail = in.readString();
        image = in.readString();
        judul = in.readString();
    }

    public static final Creator<Berita> CREATOR = new Creator<Berita>() {
        @Override
        public Berita createFromParcel(Parcel in) {
            return new Berita(in);
        }

        @Override
        public Berita[] newArray(int size) {
            return new Berita[size];
        }
    };

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(day);
        parcel.writeString(detail);
        parcel.writeString(image);
        parcel.writeString(judul);
    }
}
