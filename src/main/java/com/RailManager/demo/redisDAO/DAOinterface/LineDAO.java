package com.RailManager.demo.redisDAO.DAOinterface;

import com.RailManager.demo.model.Line;

import java.util.List;

public interface LineDAO {
    Line getLineByName(String lineName);
    List<Line> getAllLines();

    void insertLine(Line line);
    void insertLines(List<Line> list);

    Boolean deleteLine(String lineName);
    Boolean deleteLines();
}
