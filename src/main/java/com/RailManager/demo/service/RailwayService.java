package com.RailManager.demo.service;

import com.RailManager.demo.DTO.LineDTO;
import com.RailManager.demo.DTO.LineInfoDTO;
import com.RailManager.demo.DTO.StationDTO;
import com.RailManager.demo.DTO.StationInfoDTO;
import com.RailManager.demo.annotation.MyService;
import com.RailManager.demo.model.Line;
import com.RailManager.demo.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class RailwayService {
    @Autowired
    LineService lineService;
    @Autowired
    StationService stationService;

    //获取某线DTO
    @Transactional(readOnly = true)
    @MyService
    public LineInfoDTO getLineInfoDTOByName(String lineName){
        LineInfoDTO dto = new LineInfoDTO();
        dto.setLine(lineService.getLineByName(lineName));
        dto.setStations(stationService.getStationsByLine(lineName));
        return dto;
    }
    //获取某站DTO
    @Transactional(readOnly = true)
    @MyService
    public StationInfoDTO getStationInfoDTOByNameAndLine(String stationName, String lineName){
        StationInfoDTO dto = new StationInfoDTO();
        dto.setStation(stationService.getStationByNameAndLine(stationName, lineName));
        return dto;
    }

    //获取全部线DTO
    @Transactional(readOnly = true)
    @MyService
    public List<LineInfoDTO> getAllLineInfoDTO(){
        List<LineInfoDTO> dtos = new ArrayList<>();
        List<Line> lines = lineService.getAllLines();
        for(Line line : lines) {
            dtos.add(this.getLineInfoDTOByName(line.getLineName()));
        }
        return dtos;
    }
    //获取全部站DTO
    @Transactional(readOnly = true)
    @MyService
    public List<StationInfoDTO> getAllStationInfoDTO(){
        List<StationInfoDTO> dtos = new ArrayList<>();
        List<Station> stations = stationService.getAllStations();
        for(Station station : stations){
            dtos.add(this.getStationInfoDTOByNameAndLine(station.getStationName(), station.getLineName()));
        }
        return dtos;
    }

    //插入新线信息:线名相同为更新
    @MyService
    public Line addLine(LineDTO dto){
        Line line = new Line(dto);
        Line oriLine = lineService.getLineByName(dto.getLineName());
        if(oriLine == null) {
            lineService.insertLine(line);
            return line;
        }
        else {
            if(oriLine.equals(line))
                return null;
            else {
                lineService.updateLine(line);
                return line;
            }
        }
    }
    //插入新站信息：需要获取stationId 获取和后移innerId 修改对应线的stationNum
    @MyService
    public Station addStation(StationDTO dto){
        Integer preId = dto.getPreStationId();
        Integer nextId = dto.getNextStationId();
        Station station = new Station();
        List<Station> stations = stationService.getAllStations();
        List<Station> stationsByLine = stationService.getStationsByLine(dto.getLineName());
        //若前后站不相邻则要求前端正确输入
        if(!((nextId - preId) == 1 || (nextId == 0 && preId == stationsByLine.size()))){
            return null;
        }
        //修改stationId
        if(stations.isEmpty()) station.setStationId(1);
        else station.setStationId(stations.get(stations.size() - 1).getStationId() + 1);
        //设置其他信息
        station.setStationName(dto.getStationName());
        station.setStationNameEN(dto.getStationNameEN());
        station.setLineName(dto.getLineName());
        //修改innerId
        if(nextId == 0){
            station.setInnerId(preId + 1);
        }
        else {
            for(Station st : stationsByLine){
                if(preId < st.getInnerId()){
                    stationService.updateInnerId(st.getStationId(), st.getInnerId() + 1);
                }
            }
            station.setInnerId(preId + 1);
        }
        stationService.insertStation(station);
        //修改Line.stationNum
        lineService.updateStationNum(dto.getLineName(), stationsByLine.size() + 1);

        return station;
    }
    //删除线信息 级联删除站信息
    @MyService
    public LineInfoDTO deleteLine(String lineName){
        //判断被删除线路是否存在
        Line delLine = lineService.getLineByName(lineName);
        if(delLine == null) return null;

        LineInfoDTO lineInfoDTO = getLineInfoDTOByName(lineName);
        //不存在则删除
        lineService.deleteLine(lineName);
        //级联删除站点
        List<Station> stations = stationService.getStationsByLine(lineName);
        if(!stations.isEmpty()){
            for(Station station : stations) {
                this.deleteStation(station.getStationId());
            }
        }
        return lineInfoDTO;
    }
    //删除站信息 需要前移stationId 前移innerId 修改对应线的stationNum
    @MyService
    public StationInfoDTO deleteStation(Integer stationId) {
        StationInfoDTO stationInfoDTO = new StationInfoDTO();
        //判断被删除站点是否存在
        Station delStation = stationService.getStationById(stationId);
        if(delStation == null) return null;
        //存在则作后续处理并删除
        String lineName = delStation.getLineName();
        List<Station> stationsByLine = stationService.getStationsByLine(lineName);
        stationService.deleteStation(stationId);
        //处理innerId
        Integer innerId = delStation.getInnerId();
        for (Station station : stationsByLine) {
            if (innerId < station.getInnerId()) {
                stationService.updateInnerId(station.getStationId(), station.getInnerId() - 1);
            }
        }
        //处理stationId
        List<Station> stations = stationService.getAllStations();
        for (Station station : stations) {
            if (stationId < station.getStationId()) {
                stationService.updateStationId(station.getStationId(), station.getStationId() - 1);
            }
        }
        //修改Line.stationNum
        lineService.updateStationNum(lineName, stationsByLine.size() - 1);

        stationInfoDTO.setStation(delStation);
        return stationInfoDTO;
    }
}