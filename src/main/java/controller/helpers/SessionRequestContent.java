package controller.helpers;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SessionRequestContent {
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    public SessionRequestContent() {
        requestParameters = new HashMap<>();
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    public void extractValues(HttpServletRequest req) {
//        Enumeration<String> attributeNames = req.getAttributeNames();
//        while (attributeNames.hasMoreElements()) {
//            String attribute = attributeNames.nextElement();
//            requestAttributes.put(attribute, req.getAttribute(attribute));
//        }
        requestParameters = req.getParameterMap();
        System.out.println("Attributes " + requestAttributes.toString());
        Iterator iterator = requestParameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> pair = (Map.Entry<String, String[]>) iterator.next();
            System.out.println("Parameters ");
            System.out.println("Key " + pair.getKey() + "; " + Arrays.toString(pair.getValue()));
        }
    }

    public String[] getParameter(String key) {
        return requestParameters.get(key);
    }


}
