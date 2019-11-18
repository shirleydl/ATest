package com.shirley.aTest.util;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RunScript {

	private ScriptEngineManager manager = new ScriptEngineManager();
	private ScriptEngine engine;
	private String fileName;
	private String method;
	private Object value;

	public RunScript(String fileName, String method, Object value) {
		engine = manager.getEngineByName("Nashorn");
		this.fileName = fileName;
		this.method = method;
		this.value = value;
	}

	public String start() {
		try {
			engine.eval(new FileReader(fileName));
			Invocable in = (Invocable) engine;
			return (String) in.invokeFunction(method, value);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return "找不到文件";
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			return "脚本错误";
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			return "找不到方法";
		} catch (Exception e) {
			return "执行错误";
		}

	}
}