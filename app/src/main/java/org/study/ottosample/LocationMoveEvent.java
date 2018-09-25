package org.study.ottosample;


public class LocationMoveEvent {
    public float longitude;
    public float latitude;

    public LocationMoveEvent(float lng, float lat) {
        this.longitude = lng;
        this.latitude = lat;
    }
}
