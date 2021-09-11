package com.ma.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileNameUtil {
	
	
	private static void copyFileUsingApacheCommonsIO(String source, String dest)
	         throws IOException {
		
//		List<File> fileList = (List<File>) FileUtils.listFiles(new File(source, dest, true));
//		for ( File f : fileList) {
//			FileUtils.copyFile(source, dest);	
//		}
	     
	}
	
	
	private boolean deal(String path, String source, String newstr, String allflag)
		    throws Exception
		  {
		    String sourceStr = source;
		    boolean isend = false;
		    String newStr = newstr;
		    File file = new File(path);
		    File[] files = file.listFiles();
		    
		    String flag = allflag;
		    if (!"Y".equals(flag))
		    {
		      if (file.isDirectory())
		      {
		        for (int i = 0; i < files.length; i++)
		        {
		          String oldname = files[i].getName();
		          String newname = "";
		          if (("".equals(sourceStr)) || (sourceStr == null)) {
		            newname = oldname.replace(oldname, newStr + oldname);
		          } else {
		            newname = oldname.replace(sourceStr, newStr);
		          }
		          if (!oldname.equals(newname))
		          {
		            File newFile = new File(file.getAbsoluteFile() + "/" + 
		              newname);
		            
		            files[i].renameTo(newFile);
		          }
		        }
		        isend = true;
		      }
		    }
		    else
		    {
		      renameAll(file, sourceStr, newStr);
		      isend = true;
		    }
		    return isend;
		  }
		  
		  private void renameAll(File file, String sourceStr, String newStr)
		    throws Exception
		  {
		    try
		    {
		      File[] files = file.listFiles();
		      for (int i = 0; i < files.length; i++) {
		        if (!files[i].isDirectory())
		        {
		          String oldname = files[i].getName();
		          String newname = "";
		          if (("".equals(sourceStr)) || (sourceStr == null)) {
		            newname = oldname.replace(oldname, newStr + oldname);
		          } else {
		            newname = oldname.replace(sourceStr, newStr);
		          }
		          if (!oldname.equals(newname))
		          {
		            File newFile = new File(file.getAbsoluteFile() + "/" + 
		              newname);
		            
		            files[i].renameTo(newFile);
		          }
		        }
		        else
		        {
		          renameAll(files[i], sourceStr, newStr);
		        }
		      }
		    }
		    catch (Exception e)
		    {
		      throw new Exception("error");
		    }
		  }
}
