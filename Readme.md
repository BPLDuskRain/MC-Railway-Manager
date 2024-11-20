# 这是一个后端数据处理系统
技术栈：SpringBoot+MyBatis+PostgreSQL
鉴权：sa-token
## 增
### 增添线路API
路由映射为`@PostMapping("/line/addLine")`

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
### 增添站点API
路由映射为`@PostMapping("/line/addStation")`

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
### 删除线路API
删除线路会级联删除其下站点

路由映射为`@PostMapping("/line/delLine")`

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
### 删除站点API
路由映射为`@PostMapping("/line/delStation")`

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
### 修改线路API
并入到增添中，若线路名称相同视为修改
### 修改站点API
无
## 查
### 查找线路API
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
### 查找站点API
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
