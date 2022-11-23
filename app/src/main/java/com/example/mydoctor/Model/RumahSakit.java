package com.example.mydoctor.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class RumahSakit implements Parcelable {
    private String alamat;
    private String deskripsi;
    private int id;
    private String image;
    private String nama;

    public RumahSakit(String alamat, String deskripsi, int id, String image, String nama) {
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.id = id;
        this.image = image;
        this.nama = nama;
    }

    public RumahSakit() {
    }

    protected RumahSakit(Parcel in) {
        alamat = in.readString();
        deskripsi = in.readString();
        id = in.readInt();
        image = in.readString();
        nama = in.readString();
    }

    public static final Creator<RumahSakit> CREATOR = new Creator<RumahSakit>() {
        @Override
        public RumahSakit createFromParcel(Parcel in) {
            return new RumahSakit(in);
        }

        @Override
        public RumahSakit[] newArray(int size) {
            return new RumahSakit[size];
        }
    };

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(alamat);
        parcel.writeString(deskripsi);
        parcel.writeInt(id);
        parcel.writeString(image);
        parcel.writeString(nama);
    }
}
