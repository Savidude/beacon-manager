<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add action</title>

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
            <h3 style="margin-left: 48px">Add action</h3>
        </div>

        <div class="container">


            <form id="actionForm" method="POST" action="/add_action">
                <div class="grid">
                    <div class=row">
                        <div class="col-lg-1 col-md-2">
                            <h4>Profile</h4>
                        </div>
                        <div class="col-lg-2 col-md-2">
                            <div class="btn-group">
                                <button id="profileBtn" type="button" data-toggle="dropdown" class="btn btn-default dropdown-toggle">
                                    Select profile
                                    <span class="caret"></span></button>
                                <ul id="profileList" class="dropdown-menu">
                                    ${profileSet}
                                </ul>
                            </div>
                        </div>

                        <input type="hidden" id="profile" name="profile">
                    </div>

                    <div class=row">
                        <div class="col-lg-1 col-md-2">
                            <h4>Location</h4>
                        </div>
                        <div class="col-lg-2 col-md-2">
                            <div class="btn-group">
                                <button id="locationBtn" type="button" data-toggle="dropdown" class="btn btn-default dropdown-toggle">
                                    Select location
                                    <span class="caret"></span></button>
                                <ul id="locationList" class="dropdown-menu">
                                    ${locationSet}
                                </ul>
                            </div>
                        </div>

                        <input type="hidden" id="location" name="location">
                    </div>

                    <div class=row">
                        <div class="col-lg-1 col-md-2">
                            <h4>Action</h4>
                        </div>
                        <div class="col-lg-2 col-md-2">
                            <div class="btn-group">
                                <button id="actionBtn" data-toggle="dropdown" class="btn btn-default dropdown-toggle">
                                    Select action
                                    <span class="caret"></span></button>
                                <ul id="actionList" class="dropdown-menu">
                                    <li value="image"><a href="#">Display image</a></li>
                                    <li value="url"><a href="#">Send URL</a></li>
                                    <li value="endpoint"><a href="#">Call backend endoint</a></li>
                                </ul>
                            </div>
                        </div>

                        <input type="hidden" id="action" name="action">
                    </div>
                </div>

                <div class="clearfix"></div>
                <br>



















                <div id="urlDiv" style="display: none">
                    <input type="text" class="form-control form-control-lg" name="value" placeholder="Displayed URL"/>
                    <br>
                </div>

                <div id="endpointDiv" style="display: none">
                    <div class="row">
                        <div class="col-lg-1 col-md-1">
                            <div class="btn-group">
                                <button id="methodBtn" data-toggle="dropdown" class="btn btn-default dropdown-toggle">
                                    Method
                                    <span class="caret"></span></button>
                                <ul id="methodList" class="dropdown-menu">
                                    <li value="get"><a href="#">GET</a></li>
                                    <li value="post"><a href="#">POST</a></li>
                                    <li value="put"><a href="#">PUT</a></li>
                                    <li value="delete"><a href="#">DELETE</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-8 col-md-8">
                            <input type="text" class="form-control form-control-lg" name="requestUrl" placeholder="Enter requested URL"/>
                        </div>
                    </div>
                    <input type="hidden" id="requestType" name="requestType">
                    <div class="clearfix"></div><br>

                    <div id="dynamicInput">
                        <div class="row">
                            <div class="col-lg-2 col-md-2">
                                <input type="text" class="form-control" name="keys" placeholder="key" autocomplete="off"/>
                            </div>
                            <div class="col-lg-3 col-md-3">
                                <div class="input-group">
                                    <input type="text" id="values" name="values" placeholder="value" class="form-control">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle btn-lg" data-toggle="dropdown">
                                            <span class="caret"></span>
                                        </button>
                                        <ul id="presetValueList" class="dropdown-menu">
                                            <li><a href="#">$profile</a></li>
                                            <li><a href="#">$location</a></li>
                                            <li><a href="#">$user</a></li>
                                            <li><a href="#">$timestamp</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <input type="button" class="btn btn-default" value="+" onClick="addInput();">
                </div>

                <br/>
                <input id="submitButton" class="btn btn-primary btn-sm" type="submit" style="display: none">
            </form>

            <div id="imageDiv" style="display: block">
                <form id="uploadForm" action="/upload" method="POST" enctype="multipart/form-data">
                    <input type="file" class="btn btn-default btn-sm" name="image"> <br>
                    <input type="submit" class="btn btn-primary btn-sm" value="upload" onclick="upload()">

                    <div id="container"/>
                    <input type="hidden" name="profileHidden" id="profileHidden">
                    <input type="hidden" name="locationHidden" id="locationHidden">
                    <input type="hidden" name="typeHidden" id="typeHidden">
                </form>
            </div>
            </div>

        <script src="js/valueChange.js"></script>
    </body>
</html>
