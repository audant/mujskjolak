package cz.audant.mujskolak.java;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;

public class ZpravyOdUcitele {

	public static void main(String[] args) throws IOException {

		String payload = FileUtils.readFileToString(new File("src/test/resources/zproducitele_success_response.txt"), "UTF-8");

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

		System.out.println(json);
	}

}
