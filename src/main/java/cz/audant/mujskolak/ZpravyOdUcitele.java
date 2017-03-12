package cz.audant.mujskolak;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

@SuppressWarnings("unused")
public class ZpravyOdUcitele implements Callable {

	private static final Logger logger = LogManager.getLogger("cz.audant.mujskolak.ZpravyOdUcitele");

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage msg = eventContext.getMessage();
		String payload = msg.getPayloadAsString();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Integer pocet = 0;

		String key, val;
		JSONObject json = new JSONObject();
		JSONArray zpravy = new JSONArray();

		for (String pairs : payload.split("&")) {

			if (pairs.length() > 1) {

				String[] pair = pairs.split("=");

				key = pair[0];
				if (pair.length > 1) {
					val = pair[1];
				} else {
					val = "";
				}
				if (key.equals("zpravy")) {
					for (String zpln : val.split("\\^")) {
						String[] zp = zpln.split("\\#");
						JSONObject zprava = new JSONObject();
						if (dateFormat.format(date).equals(zp[0]) && zp[3].equals("ne")) {
							pocet++;
							zprava.put("datum", zp[0]);
							zprava.put("ucitel", zp[1]);
							zprava.put("zprava", zp[2]);
							zprava.put("precteno", zp[3]);
							zprava.put("id", zp[4]);
							zpravy.put(zprava);
						}
					}
					json.put(key, zpravy);
				} else if (key.equals("pocet")) {
				} else {
					json.put(key, val);
				}
			}
			json.put("pocet", pocet);
		}

		return json;
	}

}
