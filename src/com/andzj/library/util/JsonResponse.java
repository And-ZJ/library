package com.andzj.library.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.andzj.library.bean.UserAccount;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.util.finder.Test;

public class JsonResponse 
{
	private HttpServletResponse response;
	private PrintWriter printWriter;
	private String jsonStr = "";
	Map<String, Object> map = new HashMap<String,Object>();
	Gson gson = new Gson();
	
	public JsonResponse(HttpServletResponse response) throws IOException
	{
		this.response = response;
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		printWriter = response.getWriter();
	}
	
	public JsonResponse put(String key,Object value)
	{
		map.put(key, value);
		return this;
	}
	
	public JsonResponse put(String key,Map<String, Object> map)
	{
		this.map.put(key, map);
		return this;
	}
	
	public void commitAndClose()
	{
		if (map.size() > 0)
		{
			
			jsonStr = gson.toJson(map);
			System.out.println("json数据:" + jsonStr);
			//testJson(jsonStr);
			printWriter.print(jsonStr);
			//String jString = com.alibaba.fastjson.JSON.toJSONString(map);
			//System.out.println("fastJson结果:" + jString);
		}
		printWriter.flush();
		printWriter.close();
	}
	
	private  void testJson(String jsonstr)
	{
		JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonstr);
		String info = jsonObject.getString("info");
		UserAccount userAccount = (UserAccount) jsonObject.getObject("data", UserAccount.class);
		
		System.out.println("fastJson解析:\ninfo:" + info + "\naccountName:" + userAccount.getAccountName());
	}

}
