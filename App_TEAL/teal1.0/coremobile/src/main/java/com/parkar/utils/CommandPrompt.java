package com.parkar.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class CommandPrompt {
	
	final static Logger LOGGER = Logger.getLogger(CommandPrompt.class);
	
	public boolean checkSelendroidMode(String deviceName) {
		if(StringUtils.isNotBlank(deviceName)){
		try {
			String command = "adb -s  " + deviceName + " shell getprop ro.build.version.sdk";
			String apiLevel = StringUtils.removeEnd(getAPILevel(command), "\n");
			if(StringUtils.isNotBlank(apiLevel) && Integer.parseInt(apiLevel)<17){
			 return true;	
			}
		} catch (Exception e) {
			LOGGER.error("Check Selendroid mode failed", e);
		}}
		return false;
	}
	
    private String getAPILevel(String command) throws InterruptedException, IOException {
        Process p = Runtime.getRuntime().exec(command);
        // get std output
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        String apiLevel = "";
        while ((line = r.readLine()) != null) {
            apiLevel = line;
            break;
        }
        return apiLevel;

    }
}
