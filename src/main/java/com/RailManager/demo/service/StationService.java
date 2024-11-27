package com.RailManager.demo.service;

import com.RailManager.demo.annotation.*;
import com.RailManager.demo.mapper.StationMapper;
import com.RailManager.demo.model.Station;
import com.RailManager.demo.redisDAO.StationDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class StationService {
    @Autowired
    StationMapper stationMapper;
    @Autowired
    StationDAOImpl stationDAO;

    @Transactional(readOnly = true)
    @MyService
    Station getStationById(Integer stationId){
        Station station = stationDAO.getStationById(stationId);
        if(station == null){
            station = stationMapper.getStationById(stationId);
            if(station == null) return station;
            stationDAO.insertStation(station);
        }
        return station;
    }

    @Transactional(readOnly = true)
    @MyService
    Station getStationByNameAndLine(String stationName, String lineName){
        Station station =  stationDAO.getStationByNameAndLine(stationName, lineName);
        if(station == null){
            station = stationMapper.getStationByNameAndLine(stationName, lineName);
            if(station == null) return station;
            stationDAO.insertStationByLine_hash(lineName, station);
        }
        return station;
    }

    @Transactional(readOnly = true)
    @MyService
    List<Station> getAllStations(){
        List<Station> list = stationDAO.getAllStations();
        if(list.isEmpty()){
            list = stationMapper.getAllStations();
            if(list.isEmpty()) return list;
            stationDAO.insertStations(list);
        }
        return list;
    }

    @Transactional(readOnly = true)
    @MyService
    List<Station> getStationsByLine(String lineName){
        List<Station> list = stationDAO.getStationsByLine(lineName);
        if(list.isEmpty()){
            list = stationMapper.getStationsByLine(lineName);
            if(list.isEmpty()) return list;
            stationDAO.insertStationsByLine_list(lineName, list);
        }
        return list;
    }

    @MyService
    void insertStation(Station station){
        stationMapper.insertStation(station);
        stationDAO.insertStation(station);
        stationDAO.deleteStations();
        stationDAO.insertStationByLine_hash(station.getLineName(), station);
        stationDAO.deleteStationsByLine_list(station.getLineName());
    }

    @MyService
    void updateStation(Station station){
        stationMapper.updateStation(station);
        stationDAO.insertStation(station);
        stationDAO.deleteStations();
        stationDAO.insertStationByLine_hash(station.getLineName(), station);
        stationDAO.deleteStationsByLine_list(station.getLineName());
    }
    @MyService
    void updateStationId(Integer oldStationId, Integer newStationId){
        stationMapper.updateStationId(oldStationId, newStationId);
        stationDAO.deleteStation(oldStationId);
        stationDAO.deleteStations();
        stationDAO.clearStations_hash();
        stationDAO.clearStations_list();
    }
    @MyService
    void updateInnerId(Integer stationId, Integer innerId){
        stationMapper.updateInnerId(stationId, innerId);
        stationDAO.deleteStation(stationId);
        stationDAO.deleteStations();
        stationDAO.clearStations_hash();
        stationDAO.clearStations_list();
    }

    @MyService
    int deleteStation(Integer stationId){
        int flag = stationMapper.deleteStation(stationId);
        stationDAO.deleteStation(stationId);
        stationDAO.deleteStations();
        stationDAO.clearStations_hash();
        stationDAO.clearStations_list();
        return flag;
    }
}
