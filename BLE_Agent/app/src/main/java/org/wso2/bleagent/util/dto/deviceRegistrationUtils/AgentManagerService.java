package org.wso2.bleagent.util.dto.deviceRegistrationUtils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface AgentManagerService {
    @Path("device/eddystone/connect")
    @POST
    Action eddystoneConnect(@QueryParam("namespace") String namespace,
                            @QueryParam("instance") String instance,
                            @QueryParam("profile") String profile);
}
