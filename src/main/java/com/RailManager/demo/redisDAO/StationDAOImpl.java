package com.RailManager.demo.redisDAO;

import com.RailManager.demo.annotation.MyDelete;
import com.RailManager.demo.annotation.MyInsert;
import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.model.Station;
import com.RailManager.demo.redisDAO.DAOinterface.StationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class StationDAOImpl implements StationDAO {
    final static private String nameSpace = "station:";
    final static private String stationsKey = nameSpace + "station";
    final static private String nameSpace_hash = nameSpace + "_hash_";
    final static private String nameSpace_list = nameSpace + "_list_";
    @Autowired
    RedisTemplate<String, Station> redisTemplate;

    @Transactional(readOnly = true)
    @MySelect
    @Override
    public Station getStationById(Integer stationId) {
        return redisTemplate.boundValueOps(nameSpace + stationId).get();
    }
    @MyInsert
    @Override
    public void insertStation(Station station) {
        redisTemplate.boundValueOps(nameSpace + station.getStationId()).set(station);
    }
    @MyDelete
    @Override
    public Boolean deleteStation(Integer stationId) {
        return redisTemplate.delete(nameSpace + stationId);
    }

    @Transactional(readOnly = true)
    @MySelect
    @Override
    public List<Station> getAllStations() {
        return redisTemplate.boundListOps(stationsKey).range(0, -1);
    }
    @MyInsert
    @Override
    public void insertStations(List<Station> list) {
        for(Station station : list){
            redisTemplate.boundListOps(stationsKey).rightPush(station);
        }
    }
    @MyDelete
    @Override
    public Boolean deleteStations() {
        return redisTemplate.delete(stationsKey);
    }

    @Transactional(readOnly = true)
    @MySelect
    @Override
    public Station getStationByNameAndLine(String stationName, String lineName) {
        BoundHashOperations<String, String, Station> stationsByLineOps = redisTemplate.boundHashOps(nameSpace_hash + lineName);
        return stationsByLineOps.get(stationName);
    }
    @MyInsert
    @Override
    public void insertStationByLine_hash(String lineName, Station station){
        BoundHashOperations<String, String, Station> stationsByLineOps = redisTemplate.boundHashOps(nameSpace_hash + lineName);
        stationsByLineOps.put(station.getStationName(), station);
    }
    @MyDelete
    @Override
    public Long deleteStationByLine_hash(String lineName){
        return redisTemplate.boundHashOps(nameSpace_hash + lineName).delete(lineName);
    }
    @MyDelete
    @Override
    public void clearStations_hash(){
        Set<String> keys = redisTemplate.keys(nameSpace_hash + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @Transactional(readOnly = true)
    @MySelect
    @Override
    public List<Station> getStationsByLine(String lineName) {
        return redisTemplate.boundListOps(nameSpace_list + lineName).range(0, -1);
    }
    @MyInsert
    @Override
    public void insertStationsByLine_list(String lineName, List<Station> list){
        BoundListOperations<String, Station> stationsByLineOps = redisTemplate.boundListOps(nameSpace_list + lineName);
        for(Station station : list){
            stationsByLineOps.rightPush(station);
        }
    }
    @MyDelete
    @Override
    public Boolean deleteStationsByLine_list(String lineName){
        return redisTemplate.delete(nameSpace_list + lineName);
    }
    @MyDelete
    @Override
    public void clearStations_list(){
        Set<String> keys = redisTemplate.keys(nameSpace_list + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
