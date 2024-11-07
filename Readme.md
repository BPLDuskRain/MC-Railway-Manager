# 这是一个后端数据处理系统
技术栈：SpringBoot+MyBatis+postgreSQL
## 增
### 增添线路API
服务器拉取`LineDTO`，路由映射为`/addLine`
```Java
public class LineDTO {
    private String lineName;
    private String lineColor;
}
```
### 增添站点API
服务器拉取`StationDTO`，路由映射为`/addStation`
```Java
public class StationDTO {
    private String stationNameCN;
    private String stationNameEN;
    private String lineName;
    private Integer preStationId;
    private Integer nextStationId;
}
```
## 删
### 删除线路API
服务器拉取线路名称，路由映射为`/delLine`
```Java
String lineName;
```
### 删除站点API
服务器拉取站点ID，路由映射为`/delStation`
```Java
Integer stationId;
```
## 改
### 修改线路API
并入到增添中，若线路名称相同视为修改
### 修改站点API
无
## 查
### 查找线路API
#### 显示某条线路信息
服务器推送`LineInfoDTO`，路由映射为`Line/{lineName}`
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
        private String stationNameCN;
        private String stationNameEN;
        private String lineName;
        private Integer innerId;
    }
```
#### 显示全部线路信息
服务器推送`List<LineInfoDTO>`，路由映射为`/Line`
### 查找站点API
无