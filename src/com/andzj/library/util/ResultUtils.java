package com.andzj.library.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.googlecode.jsonplugin.JSONUtil;

public class  ResultUtils{

	public static void toJson(HttpServletResponse response, Object data)
			throws IOException {
		Gson gson = new Gson();
		String result = gson.toJson(data);
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache"); // È¡ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	public static void toJson(HttpServletResponse response, Object data,
			@SuppressWarnings("rawtypes") Map map) throws IOException {
		Gson gson = new Gson();
		String temp = gson.toJson(data) + gson.toJson(map);

		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache"); // È¡ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿?
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(temp));
		out.flush();
		out.close();
	}

}
