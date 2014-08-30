package com.mobilearninginc.mlpackageserv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
public class MLFileLoaderServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String fileId = req.getParameter(MLConst.FileIdParamName);
		String packageId = req.getParameter(MLConst.PackageIdParamName);
		if(fileId==null||fileId.isEmpty()||packageId==null||packageId.isEmpty())
		{
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Missing needed param(s)");
			return;
		}
		else
		{
			String mimeType = null;
			String filePath="";
			if(fileId.contains(".mp3"))
			{				
				filePath="/"+MLConst.SoundDir+"/"+fileId;
				mimeType=getServletContext().getMimeType(filePath);
			}
			else if(fileId.contains(".png"))
			{
				filePath="/"+MLConst.ImageDir+"/"+fileId;
				mimeType=getServletContext().getMimeType(filePath);
			}
			else if(fileId.contains(".dat"))//our .dat files are plain text
			{				
				filePath="/"+MLConst.PackageDir+"/"+packageId+"/"+fileId;
				mimeType="text/plain";
			}
			else
			{
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Unknown file extention");
				return;
			}
			if(mimeType==null)
			{
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Unknown mime type");
				return;
			}
				
			InputStream inputStream = getServletContext().getResourceAsStream(filePath);
			if(inputStream!=null)
			{
				resp.setContentType(mimeType);
				OutputStream out = resp.getOutputStream();
				byte[] buf = new byte[1024];
				int count = 0;
				int counter =0;
				while ((count = inputStream.read(buf)) >= 0) 
				{
					out.write(buf, 0, count);
					counter++;
				}
				
				out.close();
				inputStream.close();
				if(counter==0)
				{
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Empty File or Reading Problems");
					return;
				}
			}
			else
			{
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Cannot find file");
				return;
			}
		}
	}
}
