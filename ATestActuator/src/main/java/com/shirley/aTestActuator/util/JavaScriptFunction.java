package com.shirley.aTestActuator.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * js动态执行处理(函数)
 */
public class JavaScriptFunction {

	public String jsFunction(String jsStr) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("Nashorn");
		try {
			return (String) engine.eval(jsStr);
		} catch (ScriptException e) {
			return "";
		}
	}

	public String jsScriptFunction(String fileName, String method, Object value) {
		RunScript rs = new RunScript("jsScript//" + fileName, method, value);
		return rs.start();
	}

}