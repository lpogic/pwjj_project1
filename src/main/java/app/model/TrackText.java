package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class TrackText {

    private int id;
    private StringProperty name;
    private StringProperty track;
    private IntegerProperty rate;
    private Timestamp lastModificationTime;

    public TrackText() {
        name = new SimpleStringProperty("");
        track = new SimpleStringProperty("");
        rate = new SimpleIntegerProperty(3);
        id = 0;
    }

    public TrackText(int id, String name, String track, int rate, Timestamp lastModificationTime) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.track = new SimpleStringProperty(track);
        this.rate = new SimpleIntegerProperty(rate);
        this.lastModificationTime = lastModificationTime;
    }

    public void set(TrackText that){
        id = that.id;
        setName(that.getName());
        setTrack(that.getTrack());
        setRate(that.getRate());
        setLastModificationTime(that.getLastModificationTime());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTrack() {
        return track.get();
    }

    public void setTrack(String track) {
        this.track.set(track);
    }

    public int getRate() {
        return rate.get();
    }

    public void setRate(int rate) {
        this.rate.set(rate);
    }

    public StringProperty getNameProperty(){
        return name;
    }

    public StringProperty getTrackProperty(){
        return track;
    }

    public IntegerProperty getRateProperty(){
        return rate;
    }

    public Timestamp getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Timestamp lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    @Override
    public String toString() {
        return "\"" + name.get() +
                "\" [" + track.get() +
                "](speed: " + rate.get() +
                ") lastModificationTime: " + lastModificationTime;
    }
}
