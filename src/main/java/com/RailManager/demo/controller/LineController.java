package com.RailManager.demo.controller;

import com.RailManager.demo.DTO.LineDTO;
import com.RailManager.demo.DTO.LineInfoDTO;
import com.RailManager.demo.DTO.StationDTO;
import com.RailManager.demo.model.Line;
import com.RailManager.demo.service.RailwayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @RequestMapping("/line/{lineName}")
    public LineInfoDTO getLine(@PathVariable("lineName") String lineName){
        return railwayService.getLineInfoDTOByName(lineName);
    }
    @RequestMapping("/line")
    public List<LineInfoDTO> getLine(){
        return railwayService.getAllLineInfoDTO();
    }

    @RequestMapping("/line/download")
    public ResponseEntity<byte[]> getLineFile(){
        byte[] bytes = railwayService.getAllLineInfoDTO().toString().getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                 .header("Content-Disposition", "attachment; filename=XLM_lines_map.txt")
                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
                 .contentLength(bytes.length)
                 .body(bytes);
    }

    @RequestMapping("/addLine")
    public ResponseEntity<String> addLine(LineDTO dto){
        return railwayService.addLine(dto);
    }
    @RequestMapping("/addStation")
    public ResponseEntity<String> addStation(StationDTO dto){
        return railwayService.addStation(dto);
    }

    @RequestMapping("/delLine")
    public ResponseEntity<String> delLine(String lineName){
        return railwayService.deleteLine(lineName);
    }
    @RequestMapping("/delStation")
    public ResponseEntity<String> delStation(Integer stationId){
        return railwayService.deleteStation(stationId);
    }
}
