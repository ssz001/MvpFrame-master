package com.ssz.frame.utils;

/**
 * 编码类型
 */
public enum Charset {
	/**
	 * 文件编码类型
	 */
	UTF8("UTF-8"),
	UTF16LE("UTF-16LE"),
	UTF16BE("UTF-16BE"),
	GBK("GBK");
	
	private String mName;
	public static final byte BLANK = 0x0a;

	Charset(String name) {
		mName = name;
	}
	
	public String getName() {
		return mName;
	}
}