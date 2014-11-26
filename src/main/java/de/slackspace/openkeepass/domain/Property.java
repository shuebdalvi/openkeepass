package de.slackspace.openkeepass.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.slackspace.openkeepass.crypto.ProtectedStringCrypto;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Property implements KeePassFileElement {

	@XmlTransient
	private KeePassFileElement parent;
	
	@XmlElement(name = "Key")
	private String key;
	
	@XmlElement(name = "Value")
	private PropertyValue propertyValue;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		if(isProtected()) {
			ProtectedStringCrypto crypto = getProtectedStringCrypto();
			if(crypto != null) {
				return crypto.decrypt(propertyValue.getValue());
			}
		}
		return propertyValue.getValue();
	}

	public boolean isProtected() {
		return propertyValue.isProtected();
	}

	public void setParent(KeePassFileElement element) {
		this.parent = element;
	}

	@Override
	public ProtectedStringCrypto getProtectedStringCrypto() {
		return parent.getProtectedStringCrypto();
	}
}