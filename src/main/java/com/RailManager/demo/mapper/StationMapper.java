package com.RailManager.demo.mapper;

import com.RailManager.demo.model.Station;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StationMapper {
    Station getStationById(Integer stationId);
    List<Station> getAllStations();
    List<Station> getStationsByLine(String lineName);
    void insertStation(Station station);
    void updateStation(Station station);
    void updateStationId(Integer oldStationId, Integer newStationId);
    void updateInnerId(Integer stationId, Integer innerId);
    int deleteStation(Integer stationId);
}
