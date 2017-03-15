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

package org.wso2.bleagent.api;

import io.swagger.annotations.*;
import org.wso2.carbon.apimgt.annotations.api.Scope;
import org.wso2.carbon.apimgt.annotations.api.Scopes;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@SwaggerDefinition(
        info = @Info(
                version = "1.0.0",
                title = "",
                extensions = {
                        @Extension(properties = {
                                @ExtensionProperty(name = "name", value = "bleagent"),
                                @ExtensionProperty(name = "context", value = "/bleagent"),
                        })
                }
        ),
        tags = {
                @Tag(name = "bleagent", description = "")
        }
)
@Scopes(
        scopes = {
                @Scope(
                        name = "Enroll device",
                        description = "",
                        key = "perm:bleagent:enroll",
                        permissions = {"/device-mgt/devices/enroll/bleagent"}
                )
        }
)

public interface DeviceTypeService {

    @Path("device/eddystone/connect")
    @POST
    Response eddystoneConnect(@QueryParam("namespace") String namespace,
                              @QueryParam("instance") String instance,
                              @QueryParam("profile") String profile);

    @Path("device/download")
    @GET
    @Produces("application/zip")
    Response downloadSketch(@QueryParam("deviceName") String deviceName, @QueryParam("sketchType") String sketchType);
}