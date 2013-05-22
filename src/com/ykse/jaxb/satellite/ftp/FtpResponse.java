package com.ykse.jaxb.satellite.ftp;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.ykse.jaxb.BaseXmlObject;

public class FtpResponse extends BaseXmlObject<Response>{

	public FtpResponse() {
	}

	public FtpResponse(String xml) throws JAXBException, IOException {
		super(xml);
	}
}
