package com.ykse.jaxb.satellite.filminfo;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.ykse.jaxb.BaseXmlObject;

public class FilmInfoResponse extends BaseXmlObject<Response> {

	public FilmInfoResponse() {
	}

	public FilmInfoResponse(String xml) throws JAXBException, IOException {
		super(xml);
	}

}
