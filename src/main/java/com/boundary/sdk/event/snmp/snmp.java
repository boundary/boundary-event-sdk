package com.boundary.sdk.event.snmp;

import java.util.List;
import javax.xml.bind.annotation.*;

public class snmp {
    List<entry> entries;

    @XmlElement(name="test-data")
    public List<entry> getEntries() {
        return entries;
    }
    public void setEntries(List<entry> entries) {
        this.entries = entries;
    }
}