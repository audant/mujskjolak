package cz.audant.mujskolak.groovy

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

class UkazZnamky {

	static main(args) {

		String payload = '{"znamkyZaka":[{"datum":"2017-03-02","vaha":"3","popis":"Výzdoba třídy","znamka":"1","predmet":"PČ","ucitel":"Kateřina Jandová"},{"datum":"2017-03-02","vaha":"3","popis":"Mluvní cvičení","znamka":"2","predmet":"ČJ","ucitel":"Kateřina Jandová"},{"datum":"2017-02-27","vaha":"2","popis":"Slovíčka","znamka":"2","predmet":"AJ","ucitel":"Markéta Kunstová"}],"uspech":"yes","pocet":"18"}'
		String rlogin = '{"pocZprav":"0","ucitele":"Petra,Bémová,tlu-bepe*Lucie,Bláhová,tlu-bllu*Kateřina,Jandová,tlu-jaka*Jaroslava,Königová,tlu-koja*Jana,Kopřivová,tlu-kopja*Markéta,Kunstová,tlu-kuma*Markéta,Mašková,tlu-mama*Karel,Rada,tlu-raka*Dagmar,Radová,tlu-rada*Marta,Roudová,tlu-roma*Barbora,Šimonová,tlu-siba*Tomáš,Vávra,tlu-vato*Helena,Vítková,tlu-víhe*","uspech":"yes","prijmeni":"Basl","predmety":"Anglický jazyk - AJ*Český jazyk - ČJ*Člověk a zdraví - ČZ*Dějepis - D*Fyzika - F*Hudební výchova - HV*Chemie - CH*Matematika - M*Německý jazyk - NJ*Občanská výchova - OV*Počítače - Po*Pracovní činnosti - PČ*Přírodopis - Př*Přírodověda - Pří*Tělesná výchova - TV*Vlastivěda - Vl*Výtvarná výchova - VV*Zěměpis - Z*","jmeno":"Matěj","message":"Přihlášení proběhlo úspěšně.","den":"6","rok":"2017","uzjmeno":"tlu-basma","heslo":"30003","trida":"6","idskoly":"tlu","mesic":"3"}'

		def znamky = new JsonSlurper().parseText(payload)
		def rlog = new JsonSlurper().parseText(rlogin)

		def writer = new StringWriter()
		def html = new MarkupBuilder(writer)

		html.div(style:'font-family: Sans-serif;') {
			h1('MůjŠkolák - ukaž známky')
			p {
				mkp.yieldUnescaped('Jméno: ' + rlog.jmeno + ' ' + rlog.prijmeni + '<br/>Třída: ' + rlog.trida + '.<br/>Datum: ' + rlog.den + '.' + rlog.mesic + '.' + rlog.rok)
			}
			table(border:'1', cellpadding:'2', cellspacing:'0') {
				thead() {
					tr() {
						th('Předmět')
						th('Popis')
						th('Známka')
						th('Váha')
					}
				}
				tbody() {
					znamky.znamkyZaka.each { znamka ->
						tr() {
							td(znamka.predmet)
							td(znamka.popis)
							td(znamka.znamka)
							td(znamka.vaha)
						}
					}
				}
			}
		}

		println writer.toString()
	}
}