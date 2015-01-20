package com.boundary.sdk.event.snmp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class snmp implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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