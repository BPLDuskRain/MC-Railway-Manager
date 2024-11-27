package com.RailManager.demo.redisDAO;

import com.RailManager.demo.annotation.MyDelete;
import com.RailManager.demo.annotation.MyInsert;
import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.model.Line;
import com.RailManager.demo.redisDAO.DAOinterface.LineDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class LineDAOImpl implements LineDAO {
    final static private String nameSpace = "line:";
    final static private String linesKey = nameSpace + "line";
    @Autowired
    RedisTemplate<String, Line> redisTemplate;

    @Transactional(readOnly = true)
    @MySelect
    @Override
    public Line getLineByName(String lineName) {
        return redisTemplate.boundValueOps(nameSpace + lineName).get();
    }
    @MyInsert
    @Override
    public void insertLine(Line line) {
        redisTemplate.boundValueOps(nameSpace + line.getLineName()).set(line);
    }
    @MyDelete
    @Override
    public Boolean deleteLine(String lineName) {
        return redisTemplate.delete(nameSpace + lineName);
    }

    @Transactional(readOnly = true)
    @MySelect
    @Override
    public List<Line> getAllLines() {
        return redisTemplate.boundListOps(linesKey).range(0, -1);
    }
    @MyInsert
    @Override
    public void insertLines(List<Line> list) {
        BoundListOperations<String, Line> linesOps = redisTemplate.boundListOps(linesKey);
        for(Line line : list){
            linesOps.rightPush(line);
        }
    }
    @MyDelete
    @Override
    public Boolean deleteLines() {
        return redisTemplate.delete(linesKey);
    }
}
