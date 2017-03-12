package cz.audant.mujskolak;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONArray;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

@SuppressWarnings("unused")
public class UkazZnamky implements Callable {

	private static final Logger logger = LogManager.getLogger("cz.audant.mujskolak.UkazZnamky");

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage msg = eventContext.getMessage();
		String payload = msg.getPayloadAsString();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Integer pocet = 0;

		String key, val;
		JSONObject json = new JSONObject();
		JSONArray znamky = new JSONArray();

		for (String pairs : payload.split("&")) {

			if (pairs.length() > 1) {
				String[] pair = pairs.split("=");

				key = pair[0];
				if (pair.length > 1) {
					val = pair[1];
				} else {
					val = "";
				}
				if (key.equals("znamkyZaka")) {
					for (String znln : val.split("\\^")) {
						String[] zn = znln.split("\\*");
						JSONObject znamka = new JSONObject();
						if (dateFormat.format(date).equals(zn[0])) {
							pocet++;
							znamka.put("datum", zn[0]);
							znamka.put("predmet", zn[1]);
							znamka.put("popis", zn[2]);
							znamka.put("znamka", zn[3]);
							znamka.put("vaha", zn[4]);
							znamka.put("ucitel", zn[5]);
							znamky.put(znamka);
						}
					}
					json.put(key, znamky);
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
