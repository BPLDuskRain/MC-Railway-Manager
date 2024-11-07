package com.RailManager.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Station {
    private Integer stationId;
    private String stationNameCN;
    private String stationNameEN;
    private String lineName;
    private Integer innerId;

    public Integer getStationId() {
        return stationId;
    }
    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

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

    public Integer getInnerId() {
        return innerId;
    }
    public void setInnerId(Integer innerId) {
        this.innerId = innerId;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationId=" + stationId +
                ", stationNameCN='" + stationNameCN + '\'' +
                ", stationNameEN='" + stationNameEN + '\'' +
                ", lineName='" + lineName + '\'' +
                ", innerId=" + innerId +
                '}';
    }
}
