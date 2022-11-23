package com.example.mydoctor.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Kategori implements Parcelable {
    private String nama;
    private String icon;
    private  int id;
    private int gambar;

    public Kategori(String nama, String icon, int id,int gambar) {
        this.nama = nama;
        this.icon = icon;
        this.id = id;
        this.gambar = gambar;
    }

    public Kategori(){

    }

    protected Kategori(Parcel in) {
        nama = in.readString();
        icon = in.readString();
        id = in.readInt();
        gambar = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(icon);
        dest.writeInt(id);
        dest.writeInt(gambar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Kategori> CREATOR = new Creator<Kategori>() {
        @Override
        public Kategori createFromParcel(Parcel in) {
            return new Kategori(in);
        }

        @Override
        public Kategori[] newArray(int size) {
            return new Kategori[size];
        }
    };

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
