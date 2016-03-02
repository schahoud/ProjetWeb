package fr.uha.miage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class FileUpload {

	    public static String uploadProcess(String destPATH, HttpServletRequest request, HttpServletResponse response, String fpName, String fName) throws IOException, ServletException {
	    	// Create path components to save the file
		    final String path = destPATH;
		    final Part filePart = request.getPart(fpName);
		    final String fileName = fName;

		    OutputStream out = null;
		    InputStream filecontent = null;
		    //final PrintWriter writer = response.getWriter();

		    try {
		        out = new FileOutputStream(new File(path + File.separator
		                + fileName));
		        filecontent = filePart.getInputStream();
				System.out.println("bonjour1");
		        int read = 0;
		        final byte[] bytes = new byte[1024];

		        while ((read = filecontent.read(bytes)) != -1) {
		            out.write(bytes, 0, read);
		        }
				System.out.println("bonjour2");

		        return "OK";
		    } catch (FileNotFoundException fne) {
		        /*writer.println("You either did not specify a file to upload or are "
		                + "trying to upload a file to a protected or nonexistent "
		                + "location.");
		        writer.println("<br/> ERROR: " + fne.getMessage());
				*/
		    	return "ERROR";
		        //LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
		            //    new Object[]{fne.getMessage()});
		    } finally {
		        if (out != null) {
		            out.close();
		        }
		        if (filecontent != null) {
		            filecontent.close();
		        }
		        //if (writer != null) {
		          //  writer.close();
		        //}
		    }
	    }
}