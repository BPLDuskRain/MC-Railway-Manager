package com.RailManager.demo.redisDAO.DAOinterface;

import com.RailManager.demo.model.Station;

import java.util.List;

public interface StationDAO {
    Station getStationById(Integer stationId);
    List<Station> getAllStations();
    Station getStationByNameAndLine(String stationName, String lineName);
    List<Station> getStationsByLine(String lineName);

    void insertStation(Station station);
    void insertStations(List<Station> list);
    void insertStationByLine_hash(String lineName, Station station);
    void insertStationsByLine_list(String lineName, List<Station> list);

    Boolean deleteStation(Integer stationId);
    Boolean deleteStations();
    Long deleteStationByLine_hash(String lineName);
    void clearStations_hash();
    Boolean deleteStationsByLine_list(String lineName);
    void clearStations_list();
}
