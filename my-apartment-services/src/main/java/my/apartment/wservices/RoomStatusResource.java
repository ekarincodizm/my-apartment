/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.apartment.wservices;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import my.apartment.common.CommonString;
import my.apartment.model.RoomStatus;
import my.apartment.services.RoomStatusDao;
import my.apartment.services.RoomStatusDaoImpl;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author OPECDEMO
 */
@Path("room_status")
public class RoomStatusResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoomStatusResource
     */
    public RoomStatusResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.wservices.RoomStatusResource
     * @return an instance of java.lang.String
     */
    @Path("get_all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomStatusDao roomStatusDaoImpl = new RoomStatusDaoImpl();
            
            List<RoomStatus> roomStatus = roomStatusDaoImpl.getAll();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.DATA_STRING, roomStatus);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }

    /**
     * PUT method for updating or creating an instance of RoomStatusResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}