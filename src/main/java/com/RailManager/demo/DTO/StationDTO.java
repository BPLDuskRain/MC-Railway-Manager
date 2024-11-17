package com.RailManager.demo.DTO;

public class StationDTO {
    private String stationName;
    private String stationNameEN;
    private String lineName;
    private Integer preStationId;
    private Integer nextStationId;

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

    public Integer getPreStationId() {
        return preStationId;
    }
    public void setPreStationId(Integer preStationId) {
        this.preStationId = preStationId;
    }

    public Integer getNextStationId() {
        return nextStationId;
    }
    public void setNextStationId(Integer nextStationId) {
        this.nextStationId = nextStationId;
    }

    @Override
    public String toString() {
        return "StationDTO{" +
                "stationName='" + stationName + '\'' +
                ", stationNameEN='" + stationNameEN + '\'' +
                ", lineName='" + lineName + '\'' +
                ", preStationId=" + preStationId +
                ", nextStationId=" + nextStationId +
                '}';
    }
}
