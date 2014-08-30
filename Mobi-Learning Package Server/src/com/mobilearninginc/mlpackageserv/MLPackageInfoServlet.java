package com.mobilearninginc.mlpackageserv;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class MLPackageInfoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException 
	{
		resp.setContentType("text/plain");				
		JSONObject respStr = new JSONObject();
		try 
		{			
			Set<String> paths =this.getServletContext().getResourcePaths(MLConst.PackageDir);
			respStr.put("detailServlet", MLConst.DetailServletName);
			respStr.put("detailServletParamName", MLConst.PackageIdParamName);
			for(String packagePathStr : paths)
			{
				respStr.accumulate("packageList", packagePathStr.replace(MLConst.PackageDir+"/", "").replace("/", ""));
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"failed to read folders, or create JSON object");
			return;
		}
		resp.getWriter().println(respStr.toString());
	}
}
