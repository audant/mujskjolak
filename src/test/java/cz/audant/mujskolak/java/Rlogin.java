package cz.audant.mujskolak.java;

import org.json.JSONObject;

public class Rlogin {

	public static void main(String[] args) {

		String payload = "&message=Přihlášení proběhlo úspěšně.&den=3&mesic=3&rok=2017&jmeno=Matěj&prijmeni=Basl&uzjmeno=tlu-basma&heslo=30003&trida=6&predmety=Anglický jazyk - AJ*Český jazyk - ČJ*Člověk a zdraví - ČZ*Dějepis - D*Fyzika - F*Hudební výchova - HV*Chemie - CH*Matematika - M*Německý jazyk - NJ*Občanská výchova - OV*Počítače - Po*Pracovní činnosti - PČ*Přírodopis - Př*Přírodověda - Pří*Tělesná výchova - TV*Vlastivěda - Vl*Výtvarná výchova - VV*Zěměpis - Z*&ucitele=Petra,Bémová,tlu-bepe*Lucie,Bláhová,tlu-bllu*Kateřina,Jandová,tlu-jaka*Jaroslava,Königová,tlu-koja*Jana,Kopřivová,tlu-kopja*Markéta,Kunstová,tlu-kuma*Markéta,Mašková,tlu-mama*Karel,Rada,tlu-raka*Dagmar,Radová,tlu-rada*Marta,Roudová,tlu-roma*Barbora,Šimonová,tlu-siba*Tomáš,Vávra,tlu-vato*Helena,Vítková,tlu-víhe*&pocZprav=0&idskoly=tlu&uspech=yes";

		String[] arr;
		String[] pair;
		String key;
		String val;
		JSONObject json = new JSONObject();

		arr = payload.split("&");

		for (String pairs : arr) {
			if (pairs.length() > 0) {
				pair = pairs.split("=");

				key = pair[0];
				if (pair.length > 1) {
					val = pair[1];
				} else {
					val = "";
				}
				json.put(key, val);
			}
		}
		System.out.println(json);
	}

}