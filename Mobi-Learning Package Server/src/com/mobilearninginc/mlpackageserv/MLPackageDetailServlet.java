package com.mobilearninginc.mlpackageserv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")

public class MLPackageDetailServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String packageId = req.getParameter(MLConst.DetailServletParamName);
		if(packageId==null||packageId.isEmpty())
		{
			resp.getWriter().println("No Param Given");
		}
		else
		{
			
			String itemsFileName="MP_Items.dat";
			String pairsFileName="MP_Pairs.dat";				
			String itemCatFileName="MP_Items_Categories.dat";
			String catPairsFileName="MP_CatPairs.dat";
			String catFileName="MP_Categories.dat";
			
			InputStream inputStream = getServletContext().getResourceAsStream(MLConst.PackageDir+"/"+packageId+"/"+itemsFileName);
			if(inputStream!=null)
			{
				InputStreamReader isr = new InputStreamReader(inputStream);
	            BufferedReader reader = new BufferedReader(isr);
	            String text = "";
	            JSONObject respStr = new JSONObject();
	            String jsonObjArrName="fileList";
	            try
	            {
	            respStr.accumulate(jsonObjArrName, itemsFileName);
	            respStr.accumulate(jsonObjArrName, pairsFileName);
	            respStr.accumulate(jsonObjArrName, itemCatFileName);
	            respStr.accumulate(jsonObjArrName, catPairsFileName);
	            respStr.accumulate(jsonObjArrName, catFileName);
	            }
	            catch(Exception e)
	            {
	            	resp.getWriter().println("Cannot create JSON Object");
	            	return;
	            }
	            
	            while ((text = reader.readLine()) != null) 
	            {
	            	try 
	            	{
	            		String[] splitStrArr=text.split("\\|");	            		
	            		String audioFileStr=splitStrArr[2];
	            		respStr.accumulate(jsonObjArrName, audioFileStr);
	            		String imageFileStr=splitStrArr[3];				
	            		respStr.accumulate(jsonObjArrName, imageFileStr);
					} 
	            	catch (Exception e) 
	            	{
						e.printStackTrace();
						resp.getWriter().println("Cannot read line or create JSON Object");
						return;
					}
	            }
	            resp.getWriter().println(respStr.toString());
			}
			else
			{
				resp.getWriter().println("Invalid PackageId or inner file name problem");
				return;
			}
			
		}
	}
}
