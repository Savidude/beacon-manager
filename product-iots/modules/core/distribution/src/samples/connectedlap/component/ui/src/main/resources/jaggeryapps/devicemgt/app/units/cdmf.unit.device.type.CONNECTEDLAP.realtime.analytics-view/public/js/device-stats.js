/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var ws;
var typeId = 3;
var batteryId = 5;
var chargerId = 6;
var cpuId = 7;
var networkId =8;
var memoryId =9;
var harddiskId =10;

var battery;
var batteryData = [];

var charger;
var chargerData = [];

var cpu;
var cpuData = [];

var network;
var networkData = [];

var memory;
var memoryData = [];

var harddisk;
var harddiskData = [];

var palette = new Rickshaw.Color.Palette({scheme: "classic9"});

$(window).load(function () {

	battery = lineGraph("battery", batteryData);
	charger = lineGraph("charger", chargerData);        
	cpu = lineGraph("cpu", cpuData);
	network = lineGraph("network", networkData);
	memory = lineGraph("memory", memoryData);
	harddisk = lineGraph("harddisk", harddiskData);
	
	var websocketUrl = $("#div-chart").data("websocketurl");
	connect(websocketUrl)
});

$(window).unload(function () {
	disconnect();
});

function threeDlineGraph(type, xChartData, yChartData, zChartData) {
	var tNow = new Date().getTime() / 1000;
	for (var i = 0; i < 30; i++) {
		xChartData.push({
			x: tNow - (30 - i) * 15,
			y: parseFloat(0)
		});
		yChartData.push({
			x: tNow - (30 - i) * 15,
			y: parseFloat(0)
		});
		zChartData.push({
			x: tNow - (30 - i) * 15,
			y: parseFloat(0)
		});
	}

	var graph = new Rickshaw.Graph({
		element: document.getElementById("chart-" + type),
		width: $("#div-chart").width() - 50,
		height: 300,
		renderer: "line",
		padding: {top: 0.2, left: 0.0, right: 0.0, bottom: 0.2},
		xScale: d3.time.scale(),
		series: [
			{'color': palette.color(), 'data': xChartData, 'name': "x - " + type},
			{'color': palette.color(), 'data': yChartData, 'name': "y - " + type},
			{'color': palette.color(), 'data': zChartData, 'name': "z - " + type}
		]
	});

	graph.render();

	var xAxis = new Rickshaw.Graph.Axis.Time({
		graph: graph
	});

	xAxis.render();

	new Rickshaw.Graph.Legend({
		graph: graph,
		element: document.getElementById('legend-' + type)
	});

	var detail = new Rickshaw.Graph.HoverDetail({
		graph: graph
	});

	return graph;
}

function lineGraph(type, chartData) {
	var tNow = new Date().getTime() / 1000;
	for (var i = 0; i < 30; i++) {
		chartData.push({
			x: tNow - (30 - i) * 15,
			y: parseFloat(0)
		});
	}

	var graph = new Rickshaw.Graph({
		element: document.getElementById("chart-" + type),
		width: $("#div-chart").width() - 50,
		height: 300,
		renderer: "line",
		padding: {top: 0.2, left: 0.0, right: 0.0, bottom: 0.2},
		xScale: d3.time.scale(),
		series: [{
			'color': palette.color(),
			'data': chartData,
			'name': type
		}]
	});

	graph.render();

	var xAxis = new Rickshaw.Graph.Axis.Time({
		graph: graph
	});

	xAxis.render();

	new Rickshaw.Graph.Axis.Y({
		graph: graph,
		orientation: 'left',
		height: 300,
		tickFormat: Rickshaw.Fixtures.Number.formatKMBT,
		element: document.getElementById('y_axis')
	});

	new Rickshaw.Graph.Legend({
		graph: graph,
		element: document.getElementById('legend-' + type)
	});

	new Rickshaw.Graph.HoverDetail({
		graph: graph,
		formatter: function (series, x, y) {
			var date = '<span class="date">' + moment(x * 1000).format('Do MMM YYYY h:mm:ss a') + '</span>';
			var swatch = '<span class="detail_swatch" style="background-color: ' + series.color + '"></span>';
			return swatch + series.name + ": " + parseInt(y) + '<br>' + date;
		}
	});

	return graph;
}

//websocket connection
function connect(target) {
	if ('WebSocket' in window) {
		ws = new WebSocket(target);
	} else if ('MozWebSocket' in window) {
		ws = new MozWebSocket(target);
	} else {
		console.log('WebSocket is not supported by this browser.');
	}
	if (ws) {
		ws.onmessage = function (event) {
			var dataPoint = JSON.parse(event.data);
			console.log(dataPoint);
			
			if (dataPoint) {
				var time = parseInt(dataPoint[4]) / 1000;
				switch (dataPoint[typeId]) {
					case "connectedlap":
						graphUpdate(batteryData, time, dataPoint[batteryId], battery);
						graphUpdate(chargerData, time, dataPoint[chargerId], charger);						
						graphUpdate(cpuData, time, dataPoint[cpuId], cpu);
						graphUpdate(networkData, time, dataPoint[networkId], network);
						graphUpdate(memoryData, time, dataPoint[memoryId], memory);
						graphUpdate(harddiskData, time, dataPoint[harddiskId], harddisk);
						break;

					
				}
			}
		};
	}
}

function graphUpdate(chartData, xValue, yValue, graph) {
	chartData.push({
		x: parseInt(xValue),
		y: parseFloat(yValue)
	});
	chartData.shift();
	graph.update();
}

function dataUpdate(chartData, xValue, yValue) {
	chartData.push({
		x: parseInt(xValue),
		y: parseFloat(yValue)
	});
	chartData.shift();
}


function disconnect() {
	if (ws != null) {
		ws.close();
		ws = null;
	}
}

function initMap() {

}
