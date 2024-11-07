package com.RailManager.demo.service;

import com.RailManager.demo.DTO.LineDTO;
import com.RailManager.demo.DTO.LineInfoDTO;
import com.RailManager.demo.DTO.StationDTO;
import com.RailManager.demo.mapper.LineMapper;
import com.RailManager.demo.mapper.StationMapper;
import com.RailManager.demo.model.Line;
import com.RailManager.demo.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RailwayService {
    @Autowired
    LineMapper lineMapper;
    @Autowired
    StationMapper stationMapper;
    //按名字获取Line
    private Line getLineByName(String lineName){
        return lineMapper.getLineByName(lineName);
    }
    //获取所有Line
    private List<Line> getAllLines(){
        return lineMapper.getAllLines();
    }
    //按ID获取站点
    private Station getStationById(Integer stationId){
        return stationMapper.getStationById(stationId);
    }
    //获取全部站点
    private List<Station> getAllStations(){
        return stationMapper.getAllStations();
    }
    //按线名获取Station
    private List<Station> getStationsByLine(String lineName){
        return stationMapper.getStationsByLine(lineName);
    }
    //获取某线DTO
    public LineInfoDTO getLineInfoDTOByName(String lineName){
        LineInfoDTO dto = new LineInfoDTO();
        dto.setLine(getLineByName(lineName));
        dto.setStations(getStationsByLine(lineName));
        return dto;
    }
    //获取全部线DTO
    public List<LineInfoDTO> getAllLineInfoDTO(){
        List<LineInfoDTO> dtos = new ArrayList<>();
        List<Line> lines = getAllLines();
        for(Line line : lines) {
            dtos.add(getLineInfoDTOByName(line.getLineName()));
        }
        return dtos;
    }
    //插入新线信息:线名相同为更新
    public ResponseEntity<String> addLine(LineDTO dto){
        Line line = new Line(dto);
        Line oriLine = lineMapper.getLineByName(dto.getLineName());
        if(oriLine == null) {
            lineMapper.insertLine(line);
            return ResponseEntity.ok("Line inserted.");
        }
        else {
            if(oriLine.equals(line))
                return ResponseEntity.ok("No need to update.");
            else {
                lineMapper.updateLine(line);
                return ResponseEntity.ok("Line updated.");
            }
        }
    }
    //插入新站信息：需要获取stationId 获取和后移innerId 修改对应线的stationNum
    public ResponseEntity<String> addStation(StationDTO dto){
        Station station = new Station();
        //修改stationId
        List<Station> stations = stationMapper.getAllStations();
        if(stations.isEmpty()) station.setStationId(1);
        else station.setStationId(stations.get(stations.size() - 1).getStationId() + 1);
        //设置其他信息
        station.setStationNameCN(dto.getStationNameCN());
        station.setStationNameEN(dto.getStationNameEN());
        station.setLineName(dto.getLineName());
        //修改innerId
        Integer preId = dto.getPreStationId();
        Integer nextId = dto.getNextStationId();
        if(nextId == 0){
            station.setInnerId(preId + 1);
        }
        else {
            List<Station> stationsByLine = getStationsByLine(dto.getLineName());
            for(Station st : stationsByLine){
                if(preId < st.getInnerId()){
                    stationMapper.updateInnerId(st.getStationId(), st.getInnerId() + 1);
                }
            }
            station.setInnerId(preId + 1);
        }
        stationMapper.insertStation(station);
        //修改Line.stationNum
        lineMapper.updateStationNum(dto.getLineName(), getStationsByLine(dto.getLineName()).size());

        return ResponseEntity.ok("Station inserted.");
    }
    //删除线信息 级联删除站信息
    public ResponseEntity<String> deleteLine(String lineName){
        List<Station> stations = stationMapper.getStationsByLine(lineName);
        if(!stations.isEmpty()){
            for(Station st : stations) {
                deleteStation(st.getStationId());
            }
        }
        if(lineMapper.deleteLine(lineName) != 0) {
            return ResponseEntity.ok("Line deleted.");
        }
        else return ResponseEntity.ok("No need to delete.");
    }
    //删除站信息 需要前移stationId 前移innerId 修改对应线的stationNum
    public ResponseEntity<String> deleteStation(Integer stationId) {
        String lineName = getStationById(stationId).getLineName();
        if (stationMapper.deleteStation(stationId) != 0) {
            List<Station> stations = stationMapper.getAllStations();
            //处理stationId
            for (Station station : stations) {
                if (stationId < station.getStationId()) {
                    stationMapper.updateStationId(station.getStationId(), station.getStationId() - 1);
                }
            }
            //处理innerId
            List<Station> stationsByLine = stationMapper.getStationsByLine(lineName);
            for (Station st : stationsByLine) {
                if (getStationById(stationId).getInnerId() < st.getInnerId()) {
                    stationMapper.updateInnerId(st.getStationId(), st.getInnerId() - 1);
                }
            }
            //修改Line.stationNum
            lineMapper.updateStationNum(lineName, getStationsByLine(lineName).size());

            return ResponseEntity.ok("Station deleted.");
        }
        else return ResponseEntity.ok("No need to delete.");
    }
}
