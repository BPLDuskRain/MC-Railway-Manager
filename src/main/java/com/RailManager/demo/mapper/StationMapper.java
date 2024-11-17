package com.RailManager.demo.mapper;

import com.RailManager.demo.annotation.MyDelete;
import com.RailManager.demo.annotation.MyInsert;
import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.annotation.MyUpdate;
import com.RailManager.demo.model.Station;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional(propagation = Propagation.REQUIRED)
public interface StationMapper {
    @Transactional(readOnly = true)
    @MySelect
    Station getStationById(Integer stationId);
    @Transactional(readOnly = true)
    @MySelect
    Station getStationByNameAndLine(String stationName, String lineName);
    @Transactional(readOnly = true)
    @MySelect
    List<Station> getAllStations();
    @Transactional(readOnly = true)
    @MySelect
    List<Station> getStationsByLine(String lineName);

    @MyInsert
    void insertStation(Station station);

    @MyUpdate
    void updateStation(Station station);
    @MyUpdate
    void updateStationId(Integer oldStationId, Integer newStationId);
    @MyUpdate
    void updateInnerId(Integer stationId, Integer innerId);

    @MyDelete
    int deleteStation(Integer stationId);
}
