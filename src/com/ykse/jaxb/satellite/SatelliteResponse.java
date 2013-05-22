package com.ykse.jaxb.satellite;

import javax.xml.bind.JAXBException;

import com.ykse.jaxb.BaseXmlObject;

public class SatelliteResponse extends BaseXmlObject<Response>{

	/**
	 * 构造函数
	 */
	public SatelliteResponse() {
		xmlObject = new Response();
	}

	/**
	 * 设置返回状态 OK|ERROR
	 * @param value
	 */
	public void setStatus(String value) {
		xmlObject.setStatus(value);
	}
	
	/**
	 * 获取返回状态
	 * @return
	 */
	public String getStatus() {
		return xmlObject.getStatus();
	}

	/**
	 * 设置执行结果 TRUE|FALSE
	 * @param value
	 */
    public void setSuccess(String value) {
    	xmlObject.setSuccess(value);
    }
    
    public void setCpluuid(String value) {
    	xmlObject.setCpluuid(value);
    }

    public void setAssetType(String value) {
    	xmlObject.setAssetType(value);
    }

    public void setSource(String value) {
    	xmlObject.setSource(value);
    }

    public void setUsername(String value) {
    	xmlObject.setUsername(value);
    }

    public void setPassword(String value) {
    	xmlObject.setPassword(value);
    }

    public void setPath(String value) {
    	xmlObject.setPath(value);
    }
    
    /**
     * 取得xml字符串
     */
    @Override
    public String toXmlString() throws JAXBException {
    	return super.toXmlString();
    }
}
