package net.balmeyer.qno.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.balmeyer.qno.Qno;

/**
 * 
 * @author vovau
 *
 */
@SuppressWarnings("serial")
public class QnoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		String whatToDo = req.getRequestURI();
		
		String text = null;
		
		resp.getWriter().println("command : " + whatToDo + "\r\n");

		if (whatToDo != null && whatToDo.equals("/qno/tumblr")) {
			text =  "+MAIL+\r\n" + sendToBlog();
		}
		
		//
		if (text == null) text = displaySomeQno();
		
		//not found ? Display some qno stuff
		resp.getWriter().println(text);
	}
	
	/**
	 * Generate some sample qno
	 * @return
	 * @throws IOException
	 */
	private String displaySomeQno() throws IOException{
		//generate text
		Qno qno = new Qno();
		qno.load("patterns.txt");
		
		String text = qno.execute();
		return text;
	}
	
	/**
	 * Generate qno and send to blog
	 * @return
	 * @throws IOException
	 */
	private String sendToBlog() throws IOException{
		
		//generate text
		Qno qno = new Qno();
		qno.load("patterns.txt");
		
		String text = qno.execute();
		
		String title = Calendar.getInstance().getTime().toString();
		
		this.sendEmail(title ,text);

		return text;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getBlogEmail() throws IOException{
		URL url = QnoServlet.class.getClassLoader().getResource("mail.txt");
		
		java.io.InputStream stream = url.openStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		String result = reader.readLine();
		
		reader.close();
		stream.close();
		
		return result;
		
		
	}
	
	/**
	 * 
	 * @param title
	 * @param text
	 */
	private void sendEmail(String title, String text){
		   Properties props = new Properties();
	       Session session = Session.getDefaultInstance(props, null);

	        try  {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("balmeyer@gmail.com", "Qno Bot"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(getBlogEmail(), "Blog"));
	            msg.setSubject(title);
	            msg.setHeader("Content-Type", "text/plain; charset=utf-8");
	            msg.setText(text);
	            Transport.send(msg);
	            System.out.println("Message sent");
	        } catch (AddressException e) {
	            // ...
	        	e.printStackTrace();
	        } catch (MessagingException e) {
	        	e.printStackTrace();
	            // ...
	        } catch(UnsupportedEncodingException e){
	        	e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	}
	
}
