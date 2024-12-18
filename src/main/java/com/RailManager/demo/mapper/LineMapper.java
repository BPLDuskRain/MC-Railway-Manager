package com.RailManager.demo.mapper;

import com.RailManager.demo.annotation.MyDelete;
import com.RailManager.demo.annotation.MyInsert;
import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.annotation.MyUpdate;
import com.RailManager.demo.model.Line;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public interface LineMapper {
    @Transactional(readOnly = true)
    @MySelect
    Line getLineByName(String lineName);
    @Transactional(readOnly = true)
    @MySelect
    List<Line> getAllLines();

    @MyInsert
    void insertLine(Line line);

    @MyUpdate
    void updateLine(Line line);
    @MyUpdate
    void updateStationNum(String lineName, Integer stationNum);

    @MyDelete
    int deleteLine(String lineName);
}
