// const btn = document.querySelector('#btn');
// const dataArea = document.querySelector('#data-area');

// btn.addEventListener('click', () => {
//     // const p = new Promise((resolve,reject)=>{
//     //     const xhr = new XMLHttpRequest();
//     //     xhr.open('GET','http://localhost:8080/line');

//     //     xhr.send();

//     //     xhr.onreadystatechange = function(){
//     //         if(xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
//     //             resolve(JSON.parse(xhr.response));
//     //         }
//     //     }
//     // });

//     // p.then((res)=>{
//     //     console.log(res);
//     //     res.forEach(element => {
//     //         const {line, stations} = element;
//     //         console.log(line, stations);

//     //     });
//     //     dataArea.innerHTML = JSON.stringify(res);
//     //     return res;
//     // })

//     const xhr = new XMLHttpRequest();
//     xhr.open('GET', 'http://localhost:8080/line');

//     xhr.send();

//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
//             const res = JSON.parse(xhr.response);
//             res.forEach(element => {
//                 const { line, stations } = element;
//                 console.log(line, stations);

//             });
//             dataArea.innerHTML = JSON.stringify(res);

//         }
//     }

// })

// 页面加载时自动获取数据
window.onload = fetchLinesWhenUpdate;

function fetchLinesWhenUpdate() {
    axios.get('http://47.109.64.104:8080/line')
        .then(response => {
            // 获取要更新的DOM元素
            const linesInfo_element = document.getElementById('lines_info');
            const element = document.createDocumentFragment();
                response.data.forEach(lineInfo => {
                    const{line, stations} = lineInfo;
                    element.appendChild(createLineElement(line));
                    stations.forEach(station => {
                        element.appendChild(createStationElement(line, station));
                    });
                });
            linesInfo_element.appendChild(element);
        })
        .catch(error => {
            // 处理错误
            document.getElementById('lines_info').innerHTML = '<p>Error loading lines data. Please try again later.</p>';
        });
}

function createLineElement(line){
    const{lineName, lineColor, stationNum} = line;

    const line_element = document.createElement('p');
        line_element.style.color = `${lineColor}`;
        const line_a_element = document.createElement('a');
            line_a_element.href = `../line_info/line_info.html?lineName=${lineName}`;
            line_a_element.style.color = `${lineColor}`;
            line_a_element.textContent = `${lineName}`;
        line_element.appendChild(line_a_element);
        const line_span_element = document.createElement('span');
            line_span_element.textContent = `：现有站点${stationNum}个`;
        line_element.appendChild(line_span_element);
    return line_element;
}

function createStationElement(line, station){
    const{lineName, lineColor} = line;
    const{innerId, stationName} = station;

    const station_element = document.createElement('p');
        station_element.style.color = `${lineColor}`;

        const station_span_element = document.createElement('span');
            station_span_element.textContent = `${lineName}/${innerId}：`;
        station_element.appendChild(station_span_element);

        const station_a_element = document.createElement('a');
            station_a_element.href = `../station_info/station_info.html?lineName=${lineName}&stationName=${stationName}`;
            station_a_element.style.color = `${lineColor}`;
            station_a_element.textContent = `${stationName}`;
        station_element.appendChild(station_a_element);
    return station_element;
}