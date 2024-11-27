package com.RailManager.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.RailManager.demo.DTO.LineDTO;
import com.RailManager.demo.DTO.LineInfoDTO;
import com.RailManager.demo.DTO.StationDTO;
import com.RailManager.demo.DTO.StationInfoDTO;
import com.RailManager.demo.service.RailwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
public class RailwayController {
    @Autowired
    RailwayService railwayService;

    @Value("${crossdomain}")
    private String crossDomainValues;

    @GetMapping("/line/{lineName}")
    public ResponseEntity<LineInfoDTO> getLine(@PathVariable("lineName") String lineName){
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.getLineInfoDTOByName(lineName));
    }
    @GetMapping("/line/{lineName}/{stationName}")
    public ResponseEntity<StationInfoDTO> getStationByNameAndLine(
            @PathVariable("lineName") String lineName,
            @PathVariable("stationName")String stationName){
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.getStationInfoDTOByNameAndLine(stationName, lineName));
    }

    @GetMapping("/line")
    public ResponseEntity<List<LineInfoDTO>> getLines(){
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.getAllLineInfoDTO());
    }
    @GetMapping("/station")
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

    @PostMapping("/line")
    public ResponseEntity<?> addLine(LineDTO dto){
        if(!StpUtil.hasPermission("canAdd"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body("CANNOT ADD");
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.addLine(dto));
    }
    @PostMapping("/station")
    public ResponseEntity<?> addStation(StationDTO dto){
        if(!StpUtil.hasPermission("canAdd"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body("CANNOT ADD");
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(Objects.requireNonNullElse(railwayService.addStation(dto), "INVALID POSITION"));
    }

    @DeleteMapping("/line/{lineName}")
    public ResponseEntity<?> delLine(@PathVariable("lineName") String lineName){
        if(!StpUtil.hasPermission("canDelete"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body("CANNOT DELETE");
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.deleteLine(lineName));
    }
    @DeleteMapping("/station/{stationId}")
    public ResponseEntity<?> delStation(@PathVariable("stationId") Integer stationId){
        if(!StpUtil.hasPermission("canDelete"))
            return ResponseEntity.ok().header("Access-Control-Allow-Origin", crossDomainValues).body("CANNOT DELETE");
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", crossDomainValues)
                .body(railwayService.deleteStation(stationId));
    }
}
