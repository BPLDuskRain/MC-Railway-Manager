package com.RailManager.demo.model;

import com.RailManager.demo.DTO.LineDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Line {
    private String lineName;
    private String lineColor;
    private Integer stationNum;

    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineColor() {
        return lineColor;
    }
    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public Integer getStationNum() {
        return stationNum;
    }
    public void setStationNum(Integer stationNum) {
        this.stationNum = stationNum;
    }

    public Line(){}
    public Line(LineDTO dto){
        this.lineName = dto.getLineName();
        this.lineColor = dto.getLineColor();
        this.stationNum = 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineName, stationNum, lineColor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Line line = (Line) obj;
        return lineName.equals(line.lineName) && stationNum.equals(line.stationNum) && lineColor.equals(line.lineColor);
    }
}
