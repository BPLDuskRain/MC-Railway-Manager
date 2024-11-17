window.onload = fetchLineWhenUpdate;

function fetchLineWhenUpdate() {
    const lineName = new URLSearchParams(window.location.search).get('lineName');
    
    const lineName_element = document.getElementById('line_name');
    lineName_element.textContent = `${lineName}`;

    fetch(`../info_txt/${lineName}/${lineName}.txt`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('line_txt').textContent = data;
        })
        .catch(error => {
            document.getElementById('line_txt').textContent = error;
        });

    
    axios.get(`http://47.109.64.104:8080/line/${lineName}`)
        .then(response => {
            const{line, stations} = response.data;

            lineName_element.style.color = `${line.lineColor}`;

            const stations_info_element = document.getElementById('stations_info');
            const element = document.createDocumentFragment();
                stations.forEach(station => {
                    const{stationName, stationNameEN, innerId} = station;
                    
                    const stationName_element = document.createElement('h3');
                        const stationName_a_element = document.createElement('a');
                            stationName_a_element.href = `../station_info/station_info.html?lineName=${lineName}&stationName=${stationName}`;
                            stationName_a_element.textContent = `${stationName}`;
                        stationName_element.appendChild(stationName_a_element);
                        const stationName_i_element = document.createElement('i');
                            stationName_i_element.textContent = `${stationNameEN}(${lineName}/${innerId})`;
                        stationName_element.appendChild(stationName_i_element);
                    element.appendChild(stationName_element);
                });
            stations_info_element.appendChild(element);
            return {line, stations};
        })
        .then(add_station)
        .then(delete_station);
}

function add_station({line, stations}){
    const addStation_form = document.getElementById('add_station');
    const element = document.createDocumentFragment();
        const stationName_label = document.createElement('label');
        const stationName_input = document.createElement('input');
        const stationNameEN_label = document.createElement('label');
        const stationNameEN_input = document.createElement('input');
        const lineName_input = document.createElement('input');
        const preStation_label = document.createElement('label');
        const preStation_select = document.createElement('select');
        const nextStation_label = document.createElement('label');
        const nextStation_select = document.createElement('select');
        const submit_button = document.createElement('button');

        stationName_label.for = 'stationName';
        stationName_label.textContent = '车站名';
        stationName_input.id = 'stationName';
        stationName_input.name = 'stationName';
        stationName_input.type = 'text';

        stationNameEN_label.for = 'stationNameEN';
        stationNameEN_label.textContent = '车站英文名';
        stationNameEN_input.id = 'stationNameEN';
        stationNameEN_input.name = 'stationNameEN';
        stationNameEN_input.type = 'text';

        lineName_input.name = 'lineName';
        lineName_input.type = 'hidden';
        lineName_input.value = `${line.lineName}`;

        preStation_label.for = 'preStation';
        preStation_label.textContent = '上一站';
        preStation_select.id = 'preStation';
        preStation_select.name = 'preStationId';
        const preStation_option = document.createElement('option');
            preStation_option.value = '0';
            preStation_option.textContent = '无';
        preStation_select.appendChild(preStation_option);
        stations.forEach(station => {
            const {innerId, stationName} = station;
            const preStation_option = document.createElement('option');
                preStation_option.value = `${innerId}`;
                preStation_option.textContent = `${stationName}`;
            preStation_select.appendChild(preStation_option);
        });

        nextStation_label.for = 'nextStation';
        nextStation_label.textContent = '下一站';
        nextStation_select.id = 'nextStation';
        nextStation_select.name = 'nextStationId';
        const nextStation_option = document.createElement('option');
            nextStation_option.value = '0';
            nextStation_option.textContent = '无';
        nextStation_select.appendChild(nextStation_option);
        stations.forEach(station => {
            const {innerId, stationName} = station;
            const nextstation_option = document.createElement('option');
                nextstation_option.value = `${innerId}`;
                nextstation_option.textContent = `${stationName}`;
            nextStation_select.appendChild(nextstation_option);
        });

        submit_button.type = 'button';
        submit_button.textContent = '添加';
        submit_button.addEventListener('click', function(){
            submitAdd();
        });

        element.appendChild(stationName_label);
        element.appendChild(stationName_input);
        element.appendChild(stationNameEN_label);
        element.appendChild(stationNameEN_input);
        element.appendChild(lineName_input);
        element.appendChild(preStation_label);
        element.appendChild(preStation_select);
        element.appendChild(nextStation_label);
        element.appendChild(nextStation_select);
        element.appendChild(submit_button);
    addStation_form.appendChild(element);
    return stations;
}

function submitAdd(){
    var form = document.getElementById('add_station');
    const formData = new FormData(form);
    form.reset();
    axios.post('http://localhost:8080/line/addStation', formData)
        .then(response =>{
            const new_station = document.getElementById('new_station');
            const station = response.data;
            if(station == ''){
                new_station.innerHTML = '<p>请以管理员身份操作喵！</p>';
                return;
            }
            if(station.stationId == null){
                new_station.innerHTML = '<p>请选择有效站点位置喵！</p>';
                return;
            }
            const {stationId, stationName, stationNameEN, lineName, innerId} = station;
            new_station.innerHTML = `${lineName}/${innerId}: ${stationId}/${stationName}(${stationNameEN})增添成功喵！`;
        });
}

function delete_station(stations){
    const deleteStation_form = document.getElementById('delete_station');
    const element = document.createDocumentFragment();
        const delStation_label = document.createElement('label');
        const delStation_select = document.createElement('select');
        const submit_button = document.createElement('button');

        delStation_label.for = 'delStation';
        delStation_label.textContent = '删除站点';
        delStation_select.id = 'delStation';
        delStation_select.name = 'stationId';
        stations.forEach(station => {
            const delStation_option = document.createElement('option');
                delStation_option.value = `${station.stationId}`;
                delStation_option.textContent = `${station.stationName}`
            delStation_select.appendChild(delStation_option);
        })

        submit_button.type = 'button';
        submit_button.textContent = '删除';
        submit_button.addEventListener('click', function(){
            var comfirm = window.confirm('确定删除本站？');
            if(comfirm) submitDelete();
        });

        element.appendChild(delStation_label);
        element.appendChild(delStation_select);
        element.appendChild(submit_button);
    deleteStation_form.appendChild(element);
}

function submitDelete(){
    var form = document.getElementById('delete_station');
    const formData = new FormData(form);
    axios.post('http://localhost:8080/line/delStation', formData)
        .then(response =>{
            const old_station = document.getElementById('old_station');
            const station = response.data;
            if(station == ''){
                old_station.innerHTML = '<p>请以管理员身份操作喵！</p>';
                return;
            }
            const {stationId, stationName, stationNameEN, lineName, innerId} = station;
            old_station.innerHTML = `${lineName}/${innerId}: ${stationId}/${stationName}(${stationNameEN})删除成功喵！`;
        });
}