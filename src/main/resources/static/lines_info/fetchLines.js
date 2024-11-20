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
    axios.get(`${url}/line`)
        .then(response => {
            // 获取要更新的DOM元素
            const linesInfo_element = document.getElementById('lines_info');
            const lines_info = response.data;
            const element = document.createDocumentFragment();
                lines_info.forEach(line_info => {
                    const{line, stations} = line_info;
                    element.appendChild(createLineElement(line));
                    stations.forEach(station => {
                        element.appendChild(createStationElement(line, station));
                    });
                });
            linesInfo_element.appendChild(element);
        
            const submitAdd_button = document.getElementById('submit_addLine');
            submitAdd_button.addEventListener('click', function(){
                var confirm = window.confirm('确认添加？');
                if(confirm) submitAddLine();
            });

            const delLine_select = document.getElementById('delLine');
                lines_info.forEach(line_info => {
                    const {line} = line_info;
                    const delLine_option = document.createElement('option');
                    delLine_option.value = `${line.lineName}`;
                    delLine_option.textContent = `${line.lineName}`;
                    delLine_select.appendChild(delLine_option);
                });

            const submitDel_button = document.getElementById('submit_delLine');
            submitDel_button.addEventListener('click', function(){
                var confirm = window.confirm('确认删除本线？（下属的所有车站也会一并删除！）');
                if(confirm) submitDeleteLine();
            })
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

function submitAddLine(){
    var form = document.getElementById('add_line');
    const formData = new FormData(form);
    form.reset();
    axios.post(`${url}/line/addLine`, formData)
        .then(response => {
            const new_line = document.getElementById('new_line');
            if(response.data == 'CANNOT ADD'){
                new_line.innerHTML = '<p>请以管理员身份操作喵！</p>';
                return;
            }
            const {lineName, lineColor, stationNum} = response.data;
            new_line.innerHTML = `<p style.color=${lineColor}>${lineName}增添成功喵！</p>` 
        })
        .catch(error => {
            document.getElementById('new_line').innerHTML = '<p>Error adding line data. Please try again later.</p>';
        });
}

function submitDeleteLine(){
    const form = document.getElementById('delete_line');
    const formData = new FormData(form);
    axios.post(`${url}/line/delLine`, formData)
        .then(response => {
            const old_line = document.getElementById('old_line');
            if(response.data == 'CANNOT DELETE'){
                old_line.innerHTML = '<p>请以管理员身份操作喵！</p>';
                return;
            }
            const {line, stations} = response.data;
            const element = document.createDocumentFragment();
                const line_p = document.createElement('p');
                line_p.style.color = `${line.lineColor}`;
                line_p.textContent = `${line.lineName}删除成功喵！`;
                element.appendChild(line_p);
                stations.forEach(station => {
                    const station_p = document.createElement('p');
                    station_p.style.color = `${line.lineColor}`;
                    station_p.textContent = `${line.lineName}/${station.innerId}: ${station.stationId}/${station.stationName}(${stationNameEN})删除成功喵！`;
                    element.appendChild(station_p);
                });
            old_line.appendChild(element);
        })
        .catch(error => {
            document.getElementById('old_line').innerHTML = '<p>Error deleting line data. Please try again later.</p>';
        });
}