package cz.audant.mujskolak.groovy

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

class ZpravyOdUcitele {

	static main(args) {

		String payload = '{"zpravy":[{"datum":"2017-03-06","ucitel":"Markéta Kunstová","zprava":"Za vzdělávací pořad Filipíny vybírám 90,- (60,- vstupné + 30,- doprava). Děkuji.","precteno":"ano"},{"datum":"2017-03-06","ucitel":"Markéta Kunstová","zprava":"Dobrý den,\r\nz AJ jsme dodělali 1. lekci a napsali jsme opakovací test. Začali jsme 2. lekci (učebnice str. 20+21 a PS str. do str. 14).\r\nOstatní učitelé slibili, že Vám odepíší individuálně za jednotlivé předměty.\r\nPozdravujte Matěje a ať je brzy fit.","precteno":"ano"}],"uspech":"yes","pocet":2}'
		String rlogin = '{"pocZprav":"0","ucitele":"Petra,Bémová,tlu-bepe*Lucie,Bláhová,tlu-bllu*Kateřina,Jandová,tlu-jaka*Jaroslava,Königová,tlu-koja*Jana,Kopřivová,tlu-kopja*Markéta,Kunstová,tlu-kuma*Markéta,Mašková,tlu-mama*Karel,Rada,tlu-raka*Dagmar,Radová,tlu-rada*Marta,Roudová,tlu-roma*Barbora,Šimonová,tlu-siba*Tomáš,Vávra,tlu-vato*Helena,Vítková,tlu-víhe*","uspech":"yes","prijmeni":"Basl","predmety":"Anglický jazyk - AJ*Český jazyk - ČJ*Člověk a zdraví - ČZ*Dějepis - D*Fyzika - F*Hudební výchova - HV*Chemie - CH*Matematika - M*Německý jazyk - NJ*Občanská výchova - OV*Počítače - Po*Pracovní činnosti - PČ*Přírodopis - Př*Přírodověda - Pří*Tělesná výchova - TV*Vlastivěda - Vl*Výtvarná výchova - VV*Zěměpis - Z*","jmeno":"Matěj","message":"Přihlášení proběhlo úspěšně.","den":"6","rok":"2017","uzjmeno":"tlu-basma","heslo":"30003","trida":"6","idskoly":"tlu","mesic":"3"}'

		def zpravy = new JsonSlurper().parseText(payload)
		def rlog = new JsonSlurper().parseText(rlogin)

		def writer = new StringWriter()
		def html = new MarkupBuilder(writer)

		html.div(style:'font-family: Sans-serif;') {
			h1('MůjŠkolák - zprávy od učitele')
			p {
				mkp.yieldUnescaped('Jméno: ' + rlog.jmeno + ' ' + rlog.prijmeni + '<br/>Třída: ' + rlog.trida + '.<br/>Datum: ' + rlog.den + '.' + rlog.mesic + '.' + rlog.rok)
			}
			p { mkp.yieldUnescaped('&nbsp;') }
			zpravy.zpravy.each { zprava ->
				h2(zprava.ucitel)
				p {
					mkp.yieldUnescaped(zprava.zprava)
				}
				p { mkp.yieldUnescaped('&nbsp;') }
			}
		}

		println writer.toString()
	}
}
