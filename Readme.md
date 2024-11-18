# 这是一个后端数据处理系统
技术栈：SpringBoot+MyBatis+postgreSQL
鉴权：sa-token
## 增
### 增添线路API
服务器拉取`LineDTO`，路由映射为`/line/addLine`
```Java
public class LineDTO {
    private String lineName;
    private String lineColor;
}
```
`lineColor`要求#16进制形式
### 增添站点API
服务器拉取`StationDTO`，路由映射为`/line/addStation`
```Java
public class StationDTO {
    private String stationName;
    private String stationNameEN;
    private String lineName;
    private Integer preStationId;
    private Integer nextStationId;
}
```
`preStationId`和`nextStationId`尽量采用先拉取原线站点，采用下拉表单呈现站名并返回id的形式
## 删
### 删除线路API
服务器拉取线路名称，路由映射为`/line/delLine`
```Java
String lineName;
```
删除线路会级联删除其下站点

`lineName`应采用下拉表单方式选择
### 删除站点API
服务器拉取站点ID，路由映射为`/line/delStation`
```Java
Integer stationId;
```
`stationId`也应采用下拉表单方式展示站名返回id
## 改
### 修改线路API
并入到增添中，若线路名称相同视为修改
### 修改站点API
无
## 查
### 查找线路API
#### 显示某条线路信息
服务器推送`LineInfoDTO`，路由映射为`line/{lineName}`
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
服务器推送`List<LineInfoDTO>`，路由映射为`/line`
### 查找站点API
#### 显示某个站点信息
服务器推送`StationInfoDTO`，路由映射为`/line/{lineName}/{stationName}`
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
服务器推送`List<StationInfoDTO>`，路由映射为`/line/station`
