package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ServiceBasicPerformHelper {

    protected Logger logger = LoggerFactory.getLogger(ServiceBasicPerformHelper.class);

    public List<RecordPointTime>  recordPointTime(String name, List<RecordPointTime> recordPointTimeList){
        // clean current session's attr
        if(recordPointTimeList == null){
            recordPointTimeList = new ArrayList<>();
        }
        recordPointTimeList.add(new RecordPointTime(System.currentTimeMillis(), name));
        return recordPointTimeList;
    }

    public void printRecordTimes(List<RecordPointTime> recordPointTimeList){
        if(ServiceCollectionsHelper.checkNullList(recordPointTimeList)){
            return;
        }
        if(recordPointTimeList.size() == 1){
            return;
        }
        // Sort by ascending
        recordPointTimeList.sort(Comparator.comparing(RecordPointTime::getCurrentTimeMillis));
        for(int i = 1; i < recordPointTimeList.size(); i++){
            RecordPointTime endTime = recordPointTimeList.get(i);
            RecordPointTime startTime = recordPointTimeList.get(i -1);
            double duration = endTime.getCurrentTimeMillis() - startTime.getCurrentTimeMillis();
            String messageLine = endTime.getName() + " - " + startTime.getName() + " = " + duration + " ms";
            System.out.println(messageLine);
            logger.debug(messageLine);
        }
    }

    public static class RecordPointTime{

        private double currentTimeMillis;

        private String name;

        public RecordPointTime() {
        }

        public RecordPointTime(double currentTimeMillis, String name) {
            this.currentTimeMillis = currentTimeMillis;
            this.name = name;
        }

        public double getCurrentTimeMillis() {
            return currentTimeMillis;
        }

        public void setCurrentTimeMillis(double currentTimeMillis) {
            this.currentTimeMillis = currentTimeMillis;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
