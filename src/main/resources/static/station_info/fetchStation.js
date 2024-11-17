window.onload = fetchStationWhenUpdate;

function fetchStationWhenUpdate() {
    const pair = new URLSearchParams(window.location.search);
    const lineName = pair.get('lineName');
    const stationName = pair.get('stationName');

    const stationName_element = document.getElementById('station_name');
    axios.get(`http://47.109.64.104:8080/line/${lineName}/${stationName}`)
    .then(response => {
        const {station} = response.data;
        const{stationName, stationNameEN, innerId} = station;
        stationName_element.innerHTML = `${stationName} <i>${stationNameEN}</i> (${lineName}/${innerId})`;
    })

    fetch(`../info_txt/${lineName}/${stationName}.txt`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('station_txt').textContent = data;
        })
        .catch(error => {
            document.getElementById('station_txt').textContent = error;
        });
}