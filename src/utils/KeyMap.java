package utils;

public enum KeyMap {
	APIKey("RMGSkRhPZVdS67XPmyXFM3pl"),
	SecretKey("G2pFTWiYMXx3LXwnRHWukHzoZrgZ05n7");
	
	private String desc;
	private KeyMap(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return this.desc;
	}
}
