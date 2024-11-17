package com.RailManager.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.RailManager.demo.DTO.LineDTO;
import com.RailManager.demo.DTO.LineInfoDTO;
import com.RailManager.demo.DTO.StationDTO;
import com.RailManager.demo.DTO.StationInfoDTO;
import com.RailManager.demo.model.Line;
import com.RailManager.demo.model.Station;
import com.RailManager.demo.service.RailwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class LineController {
    @Autowired
    RailwayService railwayService;

    @Value("${crossdomain}")
    private String crossDomainValues;

    @RequestMapping("/line/{lineName}")
    public ResponseEntity<LineInfoDTO> getLine(@PathVariable("lineName") String lineName){
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.getLineInfoDTOByName(lineName));
    }
    @RequestMapping("/line/{lineName}/{stationName}")
    public ResponseEntity<StationInfoDTO> getStationByNameAndLine(
            @PathVariable("lineName") String lineName,
            @PathVariable("stationName")String stationName){
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.getStationInfoDTOByNameAndLine(stationName, lineName));
    }

    @RequestMapping("/line")
    public ResponseEntity<List<LineInfoDTO>> getLines(){
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.getAllLineInfoDTO());
    }
    @RequestMapping("/line/station")
    public ResponseEntity<List<StationInfoDTO>> getStations(){
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.getAllStationInfoDTO());
    }

    @RequestMapping("/line/download")
    public ResponseEntity<byte[]> getLineFile(){
        byte[] bytes = railwayService.getAllLineInfoDTO().toString().getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=XLM_lines_map.txt")
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(bytes.length)
                .body(bytes);
    }

    @RequestMapping("/line/addLine")
    public ResponseEntity<Line> addLine(LineDTO dto){
        if(!StpUtil.hasPermission("canAdd"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body(null);
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.addLine(dto));
    }
    @RequestMapping("/line/addStation")
    public ResponseEntity<Station> addStation(StationDTO dto){
        if(!StpUtil.hasPermission("canAdd"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body(null);
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.addStation(dto));
    }

    @RequestMapping("/line/delLine")
    public ResponseEntity<LineInfoDTO> delLine(String lineName){
        if(!StpUtil.hasPermission("canDelete"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body(null);
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.deleteLine(lineName));
    }
    @RequestMapping("/line/delStation")
    public ResponseEntity<Station> delStation(Integer stationId){
        if(!StpUtil.hasPermission("canDelete"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body(null);
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.deleteStation(stationId));
    }
}
