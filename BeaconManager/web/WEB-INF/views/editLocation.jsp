<%--
  Created by IntelliJ IDEA.
  User: wso2123
  Date: 11/29/16
  Time: 11:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit location</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
    <div class="page-header">
        <h3 style="margin-left: 48px">Edit location data</h3>
    </div>

    <div class="container">

        <form method="POST", action="/location_edit">
            <div class="grid">
                <div class="row">
                    <div class="col-lg-2 col-md-2">
                        <h4>Location ID</h4>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <input type="text" class="form-control form-control-lg" name="id" value="${id}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-2 col-md-2">
                        <h4>Location Name</h4>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <input type="text" class="form-control form-control-lg" name="name" value="${name}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-2 col-md-2">
                        <h4>Namespace</h4>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <input type="text" class="form-control form-control-lg" name="namespace" value="${namespace}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-2 col-md-2">
                        <h4>Instance</h4>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <input type="text" class="form-control form-control-lg" name="instance" value="${instance}">
                    </div>
                </div>
            </div>

            <input type="hidden" name="oldId" value="${oldId}">

            <input class="btn btn-primary btn-sm" type="submit" value="Edit">
        </form>
    </div>
</body>
</html>
