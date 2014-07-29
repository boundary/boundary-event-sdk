package com.boundary.sdk.event.snmp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entry", propOrder = {"oid","value"})
public class entry {
	@XmlElement(name="oid")
    private String oid;
	@XmlElement(name="value")
    private String value;

	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "entry [oid=" + oid + ", value=" + value + "]";
	}
}
