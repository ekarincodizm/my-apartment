package my.apartment.common;

import java.util.List;
import org.json.JSONObject;


public class JsonObjectUtils {
    
    public static JSONObject setDataNotFound(JSONObject jsonObject) {
        jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, CommonString.DATA_NOT_FOUND_STRING);
        
        return jsonObject;
    }
    
    public static JSONObject setErrorWithMessage(JSONObject jsonObject, String message) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, message);
    }
    
    public static JSONObject setControllerError(JSONObject jsonObject) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
    }
    
    public static JSONObject setServiceError(JSONObject jsonObject) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
    }
    
    public static JSONObject setSuccessWithMessageDataList(
            JSONObject jsonObject, 
            String message,
            List dataList
    ) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.MESSAGE_STRING, message)
                    .put(CommonString.DATA_STRING, dataList);
    }
    
}
