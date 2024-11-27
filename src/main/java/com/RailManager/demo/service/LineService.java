package com.RailManager.demo.service;

import com.RailManager.demo.annotation.*;
import com.RailManager.demo.mapper.LineMapper;
import com.RailManager.demo.model.Line;
import com.RailManager.demo.redisDAO.LineDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class LineService {
    @Autowired
    LineMapper lineMapper;
    @Autowired
    LineDAOImpl lineDAO;

    @Transactional(readOnly = true)
    @MyService
    Line getLineByName(String lineName){
        Line line = lineDAO.getLineByName(lineName);
        if(line == null){
            line = lineMapper.getLineByName(lineName);
            if(line == null) return line;
            lineDAO.insertLine(line);
        }
        return line;
    }

    @Transactional(readOnly = true)
    @MyService
    List<Line> getAllLines(){
        List<Line> list = lineDAO.getAllLines();
        if(list.isEmpty()){
            list = lineMapper.getAllLines();
            if(list.isEmpty()) return list;
            lineDAO.insertLines(list);
        }
        return list;
    }

    @MyService
    void insertLine(Line line){
        lineMapper.insertLine(line);
        lineDAO.insertLine(line);
        lineDAO.deleteLines();
    }

    @MyService
    void updateLine(Line line){
        lineMapper.updateLine(line);
        lineDAO.insertLine(line);
        lineDAO.deleteLines();
    }

    @MyService
    void updateStationNum(String lineName, Integer stationNum){
        lineMapper.updateStationNum(lineName, stationNum);
        lineDAO.deleteLine(lineName);
        lineDAO.deleteLines();
    }

    @MyService
    int deleteLine(String lineName){
        int flag = lineMapper.deleteLine(lineName);
        lineDAO.deleteLine(lineName);
        lineDAO.deleteLines();
        return flag;
    }
}
