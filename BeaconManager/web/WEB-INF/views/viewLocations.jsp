<%--
  Created by IntelliJ IDEA.
  User: wso2123
  Date: 11/29/16
  Time: 10:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Locations</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h2>Locations</h2>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Eddystone beacons</h3>
            </div>

            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Location ID</th>
                            <th>Name</th>
                            <th>Namespace</th>
                            <th>Instance</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${eddybeaconSet}
                    </tbody>
                </table>
            </div>
        </div>


        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">iBeacons</h3>
            </div>

            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Location ID</th>
                        <th>Name</th>
                        <th>UUID</th>
                        <th>Major</th>
                        <th>Minor</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
