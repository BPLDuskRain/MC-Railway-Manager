package com.RailManager.demo.DTO;

import com.RailManager.demo.model.Station;

public class StationInfoDTO {
    private Station station;

    public Station getStation() {
        return station;
    }
    public void setStation(Station station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "StationInfoDTO{" +
                "station=" + station +
                '}';
    }
}
