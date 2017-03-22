package my.apartment.controllers;

import my.apartment.common.CommonUtils;
import my.apartment.common.ServiceDomain;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuildingController {

    @Autowired
    MessageSource messageSource;
    
    @RequestMapping(value = "/building.html", method = {RequestMethod.GET})
    public ModelAndView buildingIndex() {
        ModelAndView modelAndView = new ModelAndView("building/building_index/building_index");

        return modelAndView;
    }
    
    @RequestMapping(value = "/building_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String buildingSave(@RequestBody MultiValueMap<String,String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = CommonUtils.simpleConvertFormDataToJSONObject(formData).toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "building/building_save", entity, String.class);
            
            JSONObject resultWsJsonObject = new JSONObject(resultWs);
            
            jsonObjectReturn = resultWsJsonObject;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(jsonObjectReturn);

        return jsonObjectReturn.toString();
    }

}