package Simultator;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.zip.CRC32;
import java.util.UUID;

public class Processus {
	protected String name_;
	protected long size_;
	protected String id_;
	
	public Processus() {		
		this.name_ = "undefined";
		this.size_ = Utils.memory(128, Utils.BIT);
		this.id_ = null;
	}
	
	public Processus(String name, long size) throws InvalidSize {
		if(size < Utils.memory(128, Utils.BIT)) throw new InvalidSize();
		
		this.name_ = name;
		this.size_ = size;
		this.id_ = setId();
	}
	
	public void setName_(String name_) {
		this.name_ = name_;
	}

	public void setSize_(long size_) {
		this.size_ = size_;
	}

	public void setPid_(String pid_) {
		this.id_ = pid_;
	}
	
	public long getSize_() {
		return this.size_;
	}

	private String setId() {
		String suuid = UUID.randomUUID().toString();
		
		CRC32 crc = new CRC32();
		crc.update(suuid.getBytes());
		return Long.toHexString(crc.getValue());
	}
	
	public String toString() {
		return "Processus [\"" + this.name_ + "\", " + this.size_ + " bits(s), ID: " + this.id_ + "]";
	}
}
