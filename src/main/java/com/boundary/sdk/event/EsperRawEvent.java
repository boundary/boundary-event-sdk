package com.boundary.sdk.event;

import java.util.Date;

public class EsperRawEvent{

	private static final long serialVersionUID = 1300660569674106078L;
	
	private RawEvent event;

	public EsperRawEvent(RawEvent event) {
		this.event = event;
	}
	
	public String [] getTags() {
		return (String []) event.getTags().toArray();
	}
	
	public String getTag(int index) {
		return event.getFingerprintFields().get(index);
	}
	
	public void setTag(int index,String value) {
		
	}
	
	public Object getProperty(String key) {
		return event.getProperties().get(key);
	}
	
	public void setProperty(String key, Object value) {
		event.getProperties().put(key, value);
	}
	
	public Source getSource() {
		return event.getSource();
	}
	
	public Source getSender() {
		return event.getSender();
	}
	
	public Date getTimestamp() {
		return event.getCreatedAt();
	}


}
