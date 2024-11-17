package com.RailManager.demo.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Station {
    private Integer stationId;
    private String stationName;
    private String stationNameEN;
    private String lineName;
    private Integer innerId;

    public Integer getStationId() {
        return stationId;
    }
    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNameEN() {
        return stationNameEN;
    }
    public void setStationNameEN(String stationNameEN) {
        this.stationNameEN = stationNameEN;
    }

    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Integer getInnerId() {
        return innerId;
    }
    public void setInnerId(Integer innerId) {
        this.innerId = innerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(stationId, station.stationId) && Objects.equals(stationName, station.stationName) && Objects.equals(stationNameEN, station.stationNameEN) && Objects.equals(lineName, station.lineName) && Objects.equals(innerId, station.innerId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(stationId, stationName, stationNameEN, lineName, innerId);
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationId=" + stationId +
                ", stationNameCN='" + stationName + '\'' +
                ", stationNameEN='" + stationNameEN + '\'' +
                ", lineName='" + lineName + '\'' +
                ", innerId=" + innerId +
                '}';
    }
}
