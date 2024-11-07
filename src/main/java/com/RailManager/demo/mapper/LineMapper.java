package com.RailManager.demo.mapper;

import com.RailManager.demo.model.Line;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LineMapper {
    Line getLineByName(String lineName);
    List<Line> getAllLines();
    void insertLine(Line line);
    void updateLine(Line line);
    void updateStationNum(String lineName, Integer stationNum);
    int deleteLine(String lineName);
}
