package no.nav.samordning.wsdl.tpsamordningvarsling;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{
	@Override
	public LocalDate unmarshal(String inputDate) {
		return LocalDate.parse(inputDate);
	}

	@Override
	public String marshal(LocalDate inputDate) {
		return inputDate.toString();
	}
}
