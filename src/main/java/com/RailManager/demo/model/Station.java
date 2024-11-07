package com.RailManager.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Station {
    private Integer StationId;
    private String StationNameCN;
    private String StationNameEN;
    private String lineName;
    private Integer innerId;

    public Integer getStationId() {
        return StationId;
    }
    public void setStationId(Integer stationId) {
        StationId = stationId;
    }

    public String getStationNameCN() {
        return StationNameCN;
    }
    public void setStationNameCN(String stationNameCN) {
        StationNameCN = stationNameCN;
    }

    public String getStationNameEN() {
        return StationNameEN;
    }
    public void setStationNameEN(String stationNameEN) {
        StationNameEN = stationNameEN;
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
}
