package com.RailManager.demo.controller;

import com.RailManager.demo.DTO.LineDTO;
import com.RailManager.demo.DTO.LineInfoDTO;
import com.RailManager.demo.DTO.StationDTO;
import com.RailManager.demo.model.Line;
import com.RailManager.demo.model.Station;
import com.RailManager.demo.service.RailwayService;
import org.springframework.beans.factory.annotation.Autowired;
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

    final private ResponseEntity<String> crossDomain = ResponseEntity.ok().header("Access-Control-Allow-Origin" ,"localhost:8080").body("");

    @RequestMapping("/line/{lineName}")
    public ResponseEntity<LineInfoDTO> getLine(@PathVariable("lineName") String lineName){
        return ResponseEntity.ok()
                .headers(crossDomain.getHeaders())
                .body(railwayService.getLineInfoDTOByName(lineName));
    }
    @RequestMapping("/line")
    public ResponseEntity<List<LineInfoDTO>> getLine(){
        return ResponseEntity.ok()
                .headers(crossDomain.getHeaders())
                .body(railwayService.getAllLineInfoDTO());
    }

    @RequestMapping("/line/download")
    public ResponseEntity<byte[]> getLineFile(){
        byte[] bytes = railwayService.getAllLineInfoDTO().toString().getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=XLM_lines_map.txt")
                .headers(crossDomain.getHeaders())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(bytes.length)
                .body(bytes);
    }

    @RequestMapping("/addLine")
    public ResponseEntity<Line> addLine(LineDTO dto){
            return ResponseEntity.ok()
                    .headers(crossDomain.getHeaders())
                    .body(railwayService.addLine(dto));
    }
    @RequestMapping("/addStation")
    public ResponseEntity<Station> addStation(StationDTO dto){
        return ResponseEntity.ok()
                .headers(crossDomain.getHeaders())
                .body(railwayService.addStation(dto));
    }

    @RequestMapping("/delLine")
    public ResponseEntity<LineInfoDTO> delLine(String lineName){
        return ResponseEntity.ok()
                .headers(crossDomain.getHeaders())
                .body(railwayService.deleteLine(lineName));
    }
    @RequestMapping("/delStation")
    public ResponseEntity<Station> delStation(Integer stationId){
        return ResponseEntity.ok()
                .headers(crossDomain.getHeaders())
                .body(railwayService.deleteStation(stationId));
    }
}
