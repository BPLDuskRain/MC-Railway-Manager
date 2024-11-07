package com.RailManager.demo.controller;

import com.RailManager.demo.DTO.LineDTO;
import com.RailManager.demo.DTO.LineInfoDTO;
import com.RailManager.demo.DTO.StationDTO;
import com.RailManager.demo.service.RailwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LineController {
    @Autowired
    RailwayService railwayService;

    @RequestMapping("/Line/{lineName}")
    public LineInfoDTO getLine(@PathVariable("lineName") String lineName){
        return railwayService.getLineInfoDTOByName(lineName);
    }
    @RequestMapping("/Line")
    public List<LineInfoDTO> getLine(){
        return railwayService.getAllLineInfoDTO();
    }
    @RequestMapping("/addLine")
    public ResponseEntity<String> addLine(LineDTO dto){
        return railwayService.addLine(dto);
    }
    @RequestMapping("/delLine")
    public ResponseEntity<String> delLine(String lineName){
        return railwayService.deleteLine(lineName);
    }
    @RequestMapping("/addStation")
    public ResponseEntity<String> addStation(StationDTO dto){
        return railwayService.addStation(dto);
    }
    @RequestMapping("/delStation")
    public ResponseEntity<String> delStation(Integer stationId){
        return railwayService.deleteStation(stationId);
    }
}
