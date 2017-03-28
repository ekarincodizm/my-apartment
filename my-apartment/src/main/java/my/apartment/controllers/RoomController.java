package my.apartment.controllers;

import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonString;
import my.apartment.common.CommonUtils;
import my.apartment.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {
    
    @RequestMapping(value = "/room.html", method = {RequestMethod.GET})
    public ModelAndView roomIndex(
            @RequestParam(value = "building_id", required = false) String buildingId
    ) {
        ModelAndView modelAndView = new ModelAndView("room/room_index/room_index");
        
        JSONObject resultGetBuilding = this.getBuilding();
        JSONArray jsonArrayBuilding = new JSONArray(resultGetBuilding.get("data").toString());
        
        JSONObject resultGetRoomStatus = this.getRoomStatus();
        JSONArray jsonArrayRoomStatus = new JSONArray(resultGetRoomStatus.get("data").toString());
        /*for(Integer i = 0; i < ja.length(); i ++) {
            System.out.println(ja.getJSONObject(i));
        }*/

        modelAndView.addObject("buildingList", jsonArrayBuilding);
        modelAndView.addObject("buildingIdString", CommonUtils.nullToStringEmpty(buildingId));
        modelAndView.addObject("roomStatusList", jsonArrayRoomStatus);
            
        return modelAndView;
    }
    
    @RequestMapping(value = "/room_get_by_building_id.html", method = {RequestMethod.GET})
    @ResponseBody
    public String roomGetByBuildingId(
            @RequestParam(value = "building_id", required = true) String buildingId,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonUtils.setResponseHeader(response);
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "room/room_get_by_building_id/" + buildingId, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
            
            /*if(CommonUtils.countJsonArrayDataFromWS(jsonObjectReturn) == 0) {
                jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.DATA_NOT_FOUND_STRING);
            }*/
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    private JSONObject getRoomStatus() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "room_status/get_all", String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn;
    }
    
    private JSONObject getBuilding() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "building/building_get", String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn;
    }
    
}