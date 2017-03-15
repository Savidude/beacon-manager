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

droneRender.init(config.configDronePlaceholder, $("#objectHolder").width(), $("#objectHolder").width() / 1.5);
droneRender.animate();
var ws;
var graph;
var chartData = [];
var flightDynamics = new flightDynamics();

$("#windowSize").slider({
    range: "min",
    value: 37,
    min: 10,
    max: 300,
    slide: function (event, ui) {
        $("#windowSizeCurrentValue").html($("#windowSize").slider("value"));

    }
});
$("#windowUpdate").slider({
    range: "min",
    value: 234,
    min: 100,
    max: 1000,
    slide: function (event, ui) {
        $("#windowUpdateValue").html($("#windowUpdate").slider("value"));
    }
});
$("#replotting").click(function () {
    plotting.finishPlotting(function (status) {
        if (status) {
            plotting.initPlotting(function (status) {
                d3.select("#realtimeChart").select("svg").remove();
                plotting.realtimePlotting("#realtimeChart", "#rangeMin", "#rangeMax", "#windowUpdateValue",
                    600, $("#realtimeChart").height(), "#windowSizeCurrentValue",
                    '#plottingAttribute');
            });
        } else {
            $("#realtimeChart").html("There is no data to plot");
        }
    });
});
$('.btn-minimize').click(function (e) {
    e.preventDefault();
    var $target = $(this).parent().parent().next('.box-content');
    if ($target.is(':visible')) {
        if ($(this).parent().attr('id') === "RealtimePlotting") {
            plotting.forceToRedraw(function (status) {
                d3.select("#realtimeChart").select("svg").remove();
                plotting.realtimePlotting("#realtimeChart", "#rangeMin", "#rangeMax", "#windowUpdateValue",
                    600, $("#realtimeChart").height(), "#windowSizeCurrentValue",
                    '#plottingAttribute');
            });
        }
    } else {
    }
});
$('.btn-minimize').parent().parent().next('.box-content').hide();

config.realtimePlottingDataWindow["attitude"] = new Queue();
var currentStatus = {};
$(window).load(function () {
    var websocketUrl = $("#div-chart").data("websocketurl");
    connect(websocketUrl);
});

$(window).unload(function () {
    disconnect();
});

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
            var sender_message = event.data;
            sender_message = isJSON(sender_message);
            if (sender_message != null) {
                var droneStats = mapDroneStats(sender_message);
                flightDynamics.processingMessage(droneStats);
            } else {
                console.log("Message has been corrupted.");
            }
        };
    }
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
}

function mapDroneStats(sender_message) {
    var responseMessage = {
        "quatanium_val": [sender_message[5], sender_message[6], sender_message[7], sender_message[8]],
        "basicParam": {
            "velocity": [sender_message[9], sender_message[10], sender_message[11]],
            "global_location": [sender_message[12], sender_message[13], sender_message[14]],
            "battery_level": sender_message[15],
            "device_type": "drone",
            "battery_voltage": sender_message[16]
        }
    };
    return responseMessage;
}


