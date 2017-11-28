package com.parkar.model;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.json.JSONObject;
import org.springframework.ldap.core.AttributesMapper;

public class UserAttributesMapper implements AttributesMapper {

	
	@Override
	public Object mapFromAttributes(Attributes attributes) throws NamingException {
		NamingEnumeration<String> ids = attributes.getIDs();
		JSONObject jo = new JSONObject();
		while(ids.hasMore()){
			String id = ids.next();
			try{
				jo.put(id, attributes.get(id).get());
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return jo.toString();

	}
	
}
