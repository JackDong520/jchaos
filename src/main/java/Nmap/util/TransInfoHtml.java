package Nmap.util;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

public class TransInfoHtml {
	private TransInfoHtml(){}
	
	public static String transformToHtml(String xml) throws TransformerException{
		
	        //InputStream xsl = new URL("https://svn.nmap.org/nmap/docs/nmap.xsl").openStream();
	        InputStream xsl = TransInfoHtml.class.getClassLoader().getResourceAsStream("static/Nmap.xsl");

	        StringWriter writer = new StringWriter();
	
	        Source xmlDoc =  new StreamSource(new StringReader(xml));
	        Source xslDoc =  new StreamSource(xsl);
	        Result result =  new StreamResult(writer);
	
	        TransformerFactory factory = TransformerFactory.newInstance();            
	        Transformer trans = factory.newTransformer(xslDoc);
	        trans.transform(xmlDoc, result); 
	
	        String outputString = writer.toString();
	        return outputString;
	    
	    
	}
}
