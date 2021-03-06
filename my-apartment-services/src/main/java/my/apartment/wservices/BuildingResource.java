package my.apartment.wservices;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import my.apartment.model.Building;
import my.apartment.services.BuildingDao;
import my.apartment.services.BuildingDaoImpl;
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.model.Room;
import my.apartment.services.RoomDao;
import my.apartment.services.RoomDaoImpl;
import org.json.JSONObject;


@Path("building")
public class BuildingResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BuildingResource
     */
    public BuildingResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.wservices.BuildingResource
     * @return an instance of java.lang.String
     */
    @Path("building_get")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getJson() {
        JSONObject jsonObjectReturn = new JSONObject();

        try {
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            List<Building> buildings = buildingDaoImpl.getAll();

            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, buildings);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("building_get_by_id/{building_id}")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    @Consumes(CommonWsUtils.MEDIA_TYPE_JSON)
    public String buildingGetById(
            @PathParam("building_id") Integer buildingId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            List<Building> buildings = buildingDaoImpl.getById(buildingId);

            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, buildings);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("building_delete_by_id")
    @POST
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String buildingDeleteById(
            @FormParam("building_id") Integer buildingId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            List<Building> buildings = buildingDaoImpl.getById(buildingId);
            
            if(buildings.isEmpty()) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.DATA_ALREADY_DELETE_STRING);
            }
            else {
                /**
                 * TODO : check building data (example: room) 
                 * if have data not allow to delete
                 */
                if(this.getRoomNumberInBuilding(buildingId) > 0) {
                    jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                            CommonString.BUILDING_HAS_ANY_DATA_STRING);
                    
                    return jsonObjectReturn.toString();
                }
                
                
                Boolean resultDelete = buildingDaoImpl.deleteById(buildingId);
                
                if(resultDelete == Boolean.TRUE) {
                    jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                            CommonString.DELETE_DATA_SUCCESS_STRING);
                }
                else {
                    jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                            CommonString.PROCESSING_FAILED_STRING);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }

    @Path("building_save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String buildingSave(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);
            
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            Building building = new Building();
            
            building.setId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("id")));
            building.setName(jsonObjectReceive.getString("name"));
            building.setAddress(jsonObjectReceive.getString("address"));
            building.setTel(jsonObjectReceive.getString("tel"));
            building.setElectricityMeterDigit(
                    CommonWsUtils.stringToInteger(jsonObjectReceive.getString("electricity_meter_digit"))
            );
            building.setElectricityChargePerUnit(
                    CommonWsUtils.stringToBigDecimal(jsonObjectReceive.getString("electricity_charge_per_unit"))
            );
            building.setMinElectricityUnit(
                    CommonWsUtils.stringToInteger(jsonObjectReceive.getString("min_electricity_unit"))
            );
            building.setMinElectricityCharge(
                    CommonWsUtils.stringToBigDecimal(jsonObjectReceive.getString("min_electricity_charge"))
            );
            
            building.setWaterMeterDigit(
                    CommonWsUtils.stringToInteger(jsonObjectReceive.getString("water_meter_digit"))
            );
            building.setWaterChargePerUnit(
                    CommonWsUtils.stringToBigDecimal(jsonObjectReceive.getString("water_charge_per_unit"))
            );
            building.setMinWaterUnit(
                    CommonWsUtils.stringToInteger(jsonObjectReceive.getString("min_water_unit"))
            );
            building.setMinWaterCharge(
                    CommonWsUtils.stringToBigDecimal(jsonObjectReceive.getString("min_water_charge"))
            );

            Building resultSave = buildingDaoImpl.save(building);
            
            if(resultSave != null) {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_SUCCESS_STRING);
                
                jsonObjectReturn.put("id", resultSave.getId());
            }
            else {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_ERROR_STRING);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    private Integer getRoomNumberInBuilding(Integer buildingId) {
        Integer resultInteger = 0;
        
        try {
            RoomDao roomDaoImpl = new RoomDaoImpl();
                
            List<Room> rooms = roomDaoImpl.getByBuildingId(buildingId);
            
            if(rooms.size() > 0) {
                resultInteger = rooms.size();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return resultInteger;
    }
}
