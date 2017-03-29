package my.apartment.controllers;

import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonString;
import my.apartment.common.CommonUtils;
import my.apartment.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @RequestMapping(value = "/room_get_by_id.html", method = {RequestMethod.GET})
    @ResponseBody
    public String roomGetById(
            @RequestParam(value = "id", required = true) String id,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonUtils.setResponseHeader(response);
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "room/room_get_by_id/" + id, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
            
            if(CommonUtils.countJsonArrayDataFromWS(jsonObjectReturn) == 0) {
                jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.DATA_NOT_FOUND_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/room_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String roomSave(@RequestBody MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            String[] keyToCleanValue = {
                "price_per_month"
            };
            
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = CommonUtils.simpleConvertFormDataToJSONObject(formData,keyToCleanValue).toString();
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonUtils.jsonMediaType();
            headers.setContentType(mediaType);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/room_save", entity, String.class, CommonString.UTF8_STRING);
            
            JSONObject resultWsJsonObject = new JSONObject(resultWs);
            
            jsonObjectReturn = resultWsJsonObject;            
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
