package cz.audant.mujskolak;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import org.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Rlogin implements Callable {

	private static final Logger logger = LogManager.getLogger("cz.audant.mujskolak.Rlogin");

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage msg = eventContext.getMessage();
		String payload = msg.getPayloadAsString();

		String[] arr;
		String[] pair;
		String key;
		String val;
		JSONObject json = new JSONObject();

		arr = payload.split("&");

		for (String pairs : arr) {

			logger.debug("Found (" + pairs.length() + "): " + pairs);

			if (pairs.length() > 1) {
				pair = pairs.split("=");

				key = pair[0];
				if (pair.length > 1) {
					val = pair[1];
				} else {
					val = "";
				}
				json.put(key, val);
				logger.debug("JSON add: " + key + " / " + val);
			}
		}

		return json;
	}

}
