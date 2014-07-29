package com.boundary.sdk.event.snmp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class snmp {

    @XmlElements({@XmlElement(name="entry",type=entry.class)})
    private List<entry> entry = new ArrayList<entry>();

    public List<entry> getEntries() {
        return entry;
    }
    public void setEntries(List<entry> entries) {
        this.entry = entries;
    }
	@Override
	public String toString() {
		return "snmp [entry=" + entry + "]";
	}
}