package com.RailManager.demo.DTO;

public class StationDTO {
    private String stationNameCN;
    private String stationNameEN;
    private String lineName;
    private Integer preStationId;
    private Integer nextStationId;

    public String getStationNameCN() {
        return stationNameCN;
    }
    public void setStationNameCN(String stationNameCN) {
        this.stationNameCN = stationNameCN;
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
}
