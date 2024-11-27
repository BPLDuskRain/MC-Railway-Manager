# 这是一个后端数据处理系统
- 主体：Spring框架+SpringBoot+Spring MVC
- 持久层框架：MyBatis
- 数据库：PostgresSQL
- 数据缓冲：Redis
- 鉴权：Sa-token
## 增
### 增添线路
路由映射为`@PostMapping("/line")`

前端提交`LineDTO`
```Java
public class LineDTO {
    private String lineName;
    private String lineColor;//#FFFFFF形式
}
```
后端返回`Line`
```Java
public class Line {
    private String lineName;
    private String lineColor;
    private Integer stationNum;
}
```
- 若权限不足，返回体为字符串`CANNOT ADD`
### 增添站点
路由映射为`@PostMapping("/station")`

前端提交`StationDTO`
```Java
public class StationDTO {
    private String stationName;
    private String stationNameEN;
    private String lineName;
    private Integer preStationId;//尽量先拉取原线站点，采用下拉表单呈现站名，提交id
    private Integer nextStationId;//尽量先拉取原线站点，采用下拉表单呈现站名，提交id
}
```
后端返回`Station`
```Java
public class Station {
    private Integer stationId;
    private String stationName;
    private String stationNameEN;
    private String lineName;
    private Integer innerId;
}
```
- 若权限不足，返回体为字符串`CANNOT ADD`
- 若站点位置不合理，返回体为字符串`INVALID POSITION`
## 删
### 删除线路
删除线路会级联删除其下站点

路由映射为`@DeleteMapping("/line/{lineName}")`

前端提交`String`
```Java
String lineName;//尽量先拉取全线，采用下拉表单呈现线名提交
```
后端返回`LineInfoDTO`
```Java
public class LineInfoDTO {
    private Line line;
    private List<Station> stations;
}

    public class Line {
        private String lineName;
        private String lineColor;
        private Integer stationNum;
    }
    public class Station {
        private Integer stationId;
        private String stationName;
        private String stationNameEN;
        private String lineName;
        private Integer innerId;
    }
```
- 若权限不足，返回体为字符串`CANNOT DELETE`
### 删除站点
路由映射为`@DeleteMapping("/station/{stationId}")`

前端提交`Integer`
```Java
Integer stationId;//尽量先拉取原线站点，采用下拉表单呈现站名，提交id
```
后端返回`StationInfoDTO`
```Java
public class StationInfoDTO {
    private Station station;
}
    public class Station {
        private Integer stationId;
        private String stationName;
        private String stationNameEN;
        private String lineName;
        private Integer innerId;
    }
```
- 若权限不足，返回体为字符串`CANNOT DELETE`
## 改
### 修改线路
并入到增添中，若线路名称相同视为修改
### 修改站点
无
## 查
### 查找线路
#### 显示某条线路信息
路由映射为`@GetMapping("/line/{lineName}")`

后端返回`LineInfoDTO`
```Java
public class LineInfoDTO {
    private Line line;
    private List<Station> stations;
}

    public class Line {
        private String lineName;
        private String lineColor;
        private Integer stationNum;
    }
    public class Station {
        private Integer stationId;
        private String stationName;
        private String stationNameEN;
        private String lineName;
        private Integer innerId;
    }
```
#### 显示全部线路信息
路由映射为`@GetMapping("/line")`

后端提交`List<LineInfoDTO>`
### 查找站点
#### 显示某个站点信息
路由映射为`@GetMapping("/line/{lineName}/{stationName}")`

后端提交`StationInfoDTO`
```Java
public class StationInfoDTO {
    private Station station;
}

    public class Station {
        private Integer stationId;
        private String stationName;
        private String stationNameEN;
        private String lineName;
        private Integer innerId;
    }
```
#### 显示全部站点信息
路由映射为`@GetMapping("/station")`

后端提交`List<StationInfoDTO>`
