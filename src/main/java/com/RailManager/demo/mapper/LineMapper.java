package com.RailManager.demo.mapper;

import com.RailManager.demo.annotation.MyDelete;
import com.RailManager.demo.annotation.MyInsert;
import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.annotation.MyUpdate;
import com.RailManager.demo.model.Line;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LineMapper {
    @MySelect Line getLineByName(String lineName);
    @MySelect List<Line> getAllLines();

    @MyInsert void insertLine(Line line);

    @MyUpdate void updateLine(Line line);
    @MyUpdate void updateStationNum(String lineName, Integer stationNum);

    @MyDelete int deleteLine(String lineName);
}
