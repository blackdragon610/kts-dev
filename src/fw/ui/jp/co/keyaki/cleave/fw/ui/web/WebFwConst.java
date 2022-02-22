package jp.co.keyaki.cleave.fw.ui.web;

public class WebFwConst {

	public static final String SESSION_KEY_PREFIX = WebFwConst.class.getPackage().getName().concat(".");

	public static final String SESSION_KEY_LOGIN_USER = SESSION_KEY_PREFIX.concat("LOGIN_USER");

	public static final String SESSION_KEY_OPERATION_USER = SESSION_KEY_PREFIX.concat("OPERATION_USER");

}
