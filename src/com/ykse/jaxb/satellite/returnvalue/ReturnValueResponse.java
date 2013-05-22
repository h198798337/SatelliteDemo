package com.ykse.jaxb.satellite.returnvalue;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.ykse.jaxb.BaseXmlObject;

public class ReturnValueResponse extends BaseXmlObject<Response>{

	public ReturnValueResponse() {
	}

	public ReturnValueResponse(String xml) throws JAXBException, IOException {
		super(xml);
	}

}
