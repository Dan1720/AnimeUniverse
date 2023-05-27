package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeTvResponse implements Parcelable {
    private boolean isLoading;
    @SerializedName("data")
    private List<AnimeTv> animeTvList;

    public AnimeTvResponse(){}

    public AnimeTvResponse(List<AnimeTv> animeTvList){
        this.animeTvList = animeTvList;
    }

    public AnimeTvResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeTvResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeTvList = in.createTypedArrayList(AnimeTv.CREATOR);
    }

    public static final Creator<AnimeTvResponse> CREATOR = new Creator<AnimeTvResponse>() {
        @Override
        public AnimeTvResponse createFromParcel(Parcel in) {
            return new AnimeTvResponse(in);
        }

        @Override
        public AnimeTvResponse[] newArray(int size) {
            return new AnimeTvResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeTv> getAnimeTvList() {
        return animeTvList;
    }

    public void setAnimeTvList(List<AnimeTv> animeTvList) {
        this.animeTvList = animeTvList;
    }

    @Override
    public String toString() {
        return "AnimeTvResponse{" +
                "isLoading=" + isLoading +
                ", animeTvList=" + animeTvList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeTvList);
    }
}
