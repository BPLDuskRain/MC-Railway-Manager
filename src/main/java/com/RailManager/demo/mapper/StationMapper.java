package com.RailManager.demo.mapper;

import com.RailManager.demo.annotation.MyDelete;
import com.RailManager.demo.annotation.MyInsert;
import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.annotation.MyUpdate;
import com.RailManager.demo.model.Station;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StationMapper {
    @MySelect Station getStationById(Integer stationId);
    @MySelect Station getStationByNameAndLine(String stationNameCN, String lineName);
    @MySelect List<Station> getAllStations();
    @MySelect List<Station> getStationsByLine(String lineName);

    @MyInsert void insertStation(Station station);

    @MyUpdate void updateStation(Station station);
    @MyUpdate void updateStationId(Integer oldStationId, Integer newStationId);
    @MyUpdate void updateInnerId(Integer stationId, Integer innerId);

    @MyDelete int deleteStation(Integer stationId);
}
