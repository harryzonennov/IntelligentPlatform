package com.company.IntelligentPlatform.common.service;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.common.service.ServiceJSONDataException;

/**
 * JSON parser helper class to process Service Chart compound model
 *
 * @author Zhang, Hang
 */
public class ChartModelJSONHelper {

    /**
     * Generate JSON data by all value fields from service entity node
     *
     * @param seNode
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String genJSONUnit(ServiceComChartUIModel charModel)
            throws ServiceJSONDataException {
        JSONObject json = JSONObject.fromObject(charModel);
        return json.toString();
    }

}
