package com.RailManager.demo.DTO;

import com.RailManager.demo.model.Line;
import com.RailManager.demo.model.Station;

import java.util.List;

public class LineInfoDTO {
    private Line line;
    private List<Station> stations;

    public Line getLine() {
        return line;
    }
    public void setLine(Line line) {
        this.line = line;
    }

    public List<Station> getStations() {
        return stations;
    }
    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
