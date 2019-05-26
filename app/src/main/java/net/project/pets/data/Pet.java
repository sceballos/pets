package net.project.pets.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {

    private String name;
    private String imageUrl;
    private String contentUrl;
    private String dateAdded;

    // Constructor
    public Pet(String name, String imageUrl, String contentUrl, String dateAdded){
        this.name = name;
        this.imageUrl = imageUrl;
        this.contentUrl = contentUrl;
        this.dateAdded = dateAdded;
    }
    // Getter and setter methods

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }


    // Parcelling part
    public Pet(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.name = data[0];
        this.imageUrl = data[1];
        this.contentUrl = data[2];
        this.dateAdded = data[3];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name,
                this.imageUrl,
                this.contentUrl,
                this.dateAdded});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };
}