
package com.creative.trnt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("imdb_code")
    @Expose
    private String imdbCode;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_english")
    @Expose
    private String titleEnglish;
    @SerializedName("title_long")
    @Expose
    private String titleLong;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("description_full")
    @Expose
    private String descriptionFull;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("yt_trailer_code")
    @Expose
    private String ytTrailerCode;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("mpa_rating")
    @Expose
    private String mpaRating;
    @SerializedName("background_image")
    @Expose
    private String backgroundImage;
    @SerializedName("background_image_original")
    @Expose
    private String backgroundImageOriginal;
    @SerializedName("small_cover_image")
    @Expose
    private String smallCoverImage;
    @SerializedName("medium_cover_image")
    @Expose
    private String mediumCoverImage;
    @SerializedName("large_cover_image")
    @Expose
    private String largeCoverImage;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("torrents")
    @Expose
    private List<Torrent> torrents = null;
    @SerializedName("date_uploaded")
    @Expose
    private String dateUploaded;
    @SerializedName("date_uploaded_unix")
    @Expose
    private Integer dateUploadedUnix;

    protected Movie(Parcel in) {
        url = in.readString();
        imdbCode = in.readString();
        title = in.readString();
        titleEnglish = in.readString();
        titleLong = in.readString();
        slug = in.readString();
        genres = in.createStringArrayList();
        summary = in.readString();
        descriptionFull = in.readString();
        synopsis = in.readString();
        ytTrailerCode = in.readString();
        language = in.readString();
        mpaRating = in.readString();
        backgroundImage = in.readString();
        backgroundImageOriginal = in.readString();
        smallCoverImage = in.readString();
        mediumCoverImage = in.readString();
        largeCoverImage = in.readString();
        state = in.readString();
        dateUploaded = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImdbCode() {
        return imdbCode;
    }

    public void setImdbCode(String imdbCode) {
        this.imdbCode = imdbCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleLong() {
        return titleLong;
    }

    public void setTitleLong(String titleLong) {
        this.titleLong = titleLong;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescriptionFull() {
        return descriptionFull;
    }

    public void setDescriptionFull(String descriptionFull) {
        this.descriptionFull = descriptionFull;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getYtTrailerCode() {
        return ytTrailerCode;
    }

    public void setYtTrailerCode(String ytTrailerCode) {
        this.ytTrailerCode = ytTrailerCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMpaRating() {
        return mpaRating;
    }

    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImageOriginal() {
        return backgroundImageOriginal;
    }

    public void setBackgroundImageOriginal(String backgroundImageOriginal) {
        this.backgroundImageOriginal = backgroundImageOriginal;
    }

    public String getSmallCoverImage() {
        return smallCoverImage;
    }

    public void setSmallCoverImage(String smallCoverImage) {
        this.smallCoverImage = smallCoverImage;
    }

    public String getMediumCoverImage() {
        return mediumCoverImage;
    }

    public void setMediumCoverImage(String mediumCoverImage) {
        this.mediumCoverImage = mediumCoverImage;
    }

    public String getLargeCoverImage() {
        return largeCoverImage;
    }

    public void setLargeCoverImage(String largeCoverImage) {
        this.largeCoverImage = largeCoverImage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Torrent> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<Torrent> torrents) {
        this.torrents = torrents;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Integer getDateUploadedUnix() {
        return dateUploadedUnix;
    }

    public void setDateUploadedUnix(Integer dateUploadedUnix) {
        this.dateUploadedUnix = dateUploadedUnix;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(imdbCode);
        parcel.writeString(title);
        parcel.writeString(titleEnglish);
        parcel.writeString(titleLong);
        parcel.writeString(slug);
        parcel.writeStringList(genres);
        parcel.writeString(summary);
        parcel.writeString(descriptionFull);
        parcel.writeString(synopsis);
        parcel.writeString(ytTrailerCode);
        parcel.writeString(language);
        parcel.writeString(mpaRating);
        parcel.writeString(backgroundImage);
        parcel.writeString(backgroundImageOriginal);
        parcel.writeString(smallCoverImage);
        parcel.writeString(mediumCoverImage);
        parcel.writeString(largeCoverImage);
        parcel.writeString(state);
        parcel.writeString(dateUploaded);
    }
}
