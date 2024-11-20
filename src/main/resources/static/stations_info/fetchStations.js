window.onload = fetchStationsWhenUpdate;

function fetchStationsWhenUpdate(){
    axios.get(`${url}/station`)
        .then(response => {
            const stationsInfo_element = document.getElementById('stations_info');
            const element = document.createDocumentFragment();
            response.data.forEach(stationInfo => {
                const {station} = stationInfo;
                element.appendChild(createStationElement(station));
            });
            stationsInfo_element.appendChild(element);
        })
        .catch(error => {
            document.getElementById('stations_info').innerHTML = '<p>Error loading stations data. Please try again later.</p>';
        });
}

function createStationElement(station){
    const{stationId, lineName, innerId, stationName} = station;

    const station_element = document.createElement('p');
        const station_span_element = document.createElement('span');
            station_span_element.textContent = `第${stationId}站：${lineName}/${innerId}：`;
        station_element.appendChild(station_span_element);

        const station_a_element = document.createElement('a');
            station_a_element.href = `../station_info/station_info.html?lineName=${lineName}&stationName=${stationName}`;
            station_a_element.textContent = `${stationName}`;
        station_element.appendChild(station_a_element);
    return station_element;
}