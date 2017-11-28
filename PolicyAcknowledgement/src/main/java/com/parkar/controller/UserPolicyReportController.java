package com.parkar.controller;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.parkar.model.EmployeeDirectory;
import com.parkar.model.Login;
import com.parkar.model.UserAttributesMapper;
import com.parkar.model.UserPolicyReport;
import com.parkar.service.EmployeeDirectoryService;
import com.parkar.service.LoginService;
import com.parkar.service.UserPolicyReportService;

@RestController
@RequestMapping("/getData")
public class UserPolicyReportController {
	
	
	@Autowired
	UserPolicyReportService userPolicyReportServiceImpl;
	
	@Autowired 
	LoginService loginServiceImpl;
	
	@Autowired 
	EmployeeDirectoryService employeeDirectoryServiceImpl;
	Properties properties = new Properties();
	Logger logger =  LoggerFactory.getLogger("PolicyAScknowledgement");
	

	@RequestMapping(value = "/getReport",method = RequestMethod.GET)
	public @ResponseBody List<UserPolicyReport> getReportOfUser(){
		List<UserPolicyReport> list = userPolicyReportServiceImpl.getReport();
		return list;
	}
	
	public void sendEmail(String email,String full_name){
		
		
		try{ final InputStream stream =getClass().getClassLoader().getResourceAsStream("properties/policyack.properties");

		  properties.load(stream);
		  
		 }catch (IOException ioe) {
			 
				
			 // TODO Auto-generated catch block
		ioe.printStackTrace();
		}
		
		
		String toUser=email;//change accordingly
		String toHr = properties.getProperty("toHr");
		String cc = properties.getProperty("cc");
		final String user = properties.getProperty("user");
		final String password = properties.getProperty("password");
		 
		  Session session = Session.getDefaultInstance(properties,
		   new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		   return new PasswordAuthentication(user,password);
		   }
		  });
		   
		  //2) compose message   
		  try{
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(user));
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(toUser));
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(toHr));
		    message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc));
		    message.setSubject(full_name +" NDA policy Acknowledgement");
		    
		    //3) create MimeBodyPart object and set your message content    
		    BodyPart messageBodyPart1 = new MimeBodyPart();
		    messageBodyPart1.setText("Hi ,\n\nThis is to inform I "+full_name+" has Agreed and Accepted the Parkar NDA – Non Disclosure Agreement and below mentioned Policies."
		    		+ "\n\n\t- Attendance & Shift Allowance Policy"
		    		+ "\n\t- Certification Policy"
		    		+ "\n\t- Compensation and Benefits"
		    		+ "\n\t- Dress Code Policy"
		    		+ "\n\t- Employee Referral Program"
		    		+ "\n\t- General and Personal Record"
		    		+ "\n\t- Information Security Policy"
		    		+ "\n\t- Leave Policy"
		    		+ "\n\t- On-boarding and Induction"
		    		+ "\n\t- Prevention of Workplace Harassment Policy"
		    		+ "\n\t- Reimbursement Policy"
		    		+ "\n\t- Separation"
		    		+ "\n\n\nNote :  This is an auto-generated mail. Please do not reply.");
		    		    
		    //4) create new MimeBodyPart object and set DataHandler object to this object    
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();

		    String filename = "C:\\NDA\\"+full_name+"_Nda.pdf";//change accordingly
		    DataSource source = new FileDataSource(filename);
		    messageBodyPart2.setDataHandler(new DataHandler(source));
		    messageBodyPart2.setFileName(full_name+"_Nda.pdf");
		   
		   
		    //5) create Multipart object and add MimeBodyPart objects to this object    
		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messageBodyPart1);
		    multipart.addBodyPart(messageBodyPart2);

		    //6) set the multiplart object to the message object
		    message.setContent(multipart );
		   
		    //7) send message
		    Transport.send(message);
		    
		     //logger.debug("Hello world.");
		     logger.info("Email Send Successfully");
		    		   }catch (MessagingException ex) {ex.printStackTrace();}
		 }
	
	@RequestMapping(value = "/updateStatus/{username}/{statusNda}/{statusPolicy}" ,method = RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	public String updateStatus(@PathVariable String username,@PathVariable String statusNda,@PathVariable String statusPolicy) throws  IOException, DocumentException{
		String message = "not read";
		String email=null;
		String full_name=null;
		EmployeeDirectory employee = employeeDirectoryServiceImpl.getEmployeeCodeByUserName(username);
		full_name = employee.getFull_name();
		System.out.println("status is : "+statusNda);
		System.out.println("status is : "+statusPolicy);
		int row =0;
			statusNda = "Yes";
			statusPolicy = "Yes";
			java.util.Date dt = new java.util.Date();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = formatter.format(dt);
			email = employee.getEmail_id();
			String src="C:\\NDA\\NDA.pdf";
			String dest="C:\\NDA\\"+full_name+"_Nda.pdf";
			File file = new File(dest);
			file.getParentFile().mkdirs();
				pdfCreation(src,dest,full_name);
			
			//readDocxFile(src,dest,full_name);
			sendEmail(email,full_name);
					
		row = userPolicyReportServiceImpl.updateStatusByUsername(username, statusNda, statusPolicy, currentTime);
		if(row>0){
			message = "I read policy";
		}
		return message;
	}
	
	
	
	String finalRole ;
	@RequestMapping(value = "/login" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loginUser(@RequestBody Login login,HttpServletRequest request){
		HttpSession session = request.getSession();
		String role = null;
		String finalUsername = null;
		String fullName = null;
		String username = login.getUsername();
		String password = login.getPassword();
		String role1 = login.getRole();
		
		boolean isValidUser = authenticateUser(username,password);
		System.out.println("Ladap authenticate"+isValidUser);
		
		if(!isValidUser){
			
			finalUsername = "Invalid User";	
			session.setAttribute("username", finalUsername);
		}
		else{
			UserPolicyReport user= userPolicyReportServiceImpl.getReportByUserName(username);
			EmployeeDirectory employee = employeeDirectoryServiceImpl.getEmployeeCodeByUserName(username);
			role = employee.getRole();
			
			if(role.equals(role1)){
				finalRole = role;
				
			}
			
			else if(role1.equals("user") && role.equals("admin")){
				finalRole = role1;
				
			}
			else if(role1.equals("admin") && role.equals("user")) {
				finalRole= "You are not admin...";
			}
			
		
			UserPolicyReport userReport = new UserPolicyReport();
				if(user==null){
					userReport.setUsername(username);
					userReport.setStatusNda("No");
					userReport.setStatusPolicy("No");
					userReport.setEmployee_code(employee.getEmployee_code());
					userReport.setFull_name(employee.getFull_name());
					int userId = userPolicyReportServiceImpl.save(userReport);
					finalUsername= userReport.getUsername();
					fullName = userReport.getFull_name();
					session.setAttribute("username", finalUsername);
				}
				else if(user!=null && user.getStatusNda().equals("Yes") && user.getStatusPolicy().equals("Yes") && role1.equals("user")){
					finalUsername = "You have already submitted";
				}
				
				else{
					finalUsername= user.getUsername();
					fullName = employee.getFull_name();
					session.setAttribute("username", finalUsername);
			}
		}
		JSONObject json = new JSONObject();
        
         json.put("username", finalUsername);
         json.put("full_name", fullName);
         json.put("role", finalRole);
         return json.toString();
		
		}
	@RequestMapping(value = "/logout" ,method = RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	public String logoutUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return "Sucess";
	}
	
	@RequestMapping(value = "/addEmployee" ,method = RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	public String addEmployee(@RequestBody EmployeeDirectory emp){
		String responce ="";
		String employee_code = emp.getEmployee_code();
		boolean empCodeStatus= employeeDirectoryServiceImpl.searchEmployeeCode(employee_code);
		if(empCodeStatus){
			responce="Employee Already Exist";
		}
		else{
		responce=employeeDirectoryServiceImpl.save(emp);
		}
		return responce;
	}

	@RequestMapping(value = "/fileupload" ,method = RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	public String uploadFile(@RequestBody EmployeeDirectory emp){
		String username = emp.getUsername();
		String employee_code = emp.getEmployee_code();
		String responce=employeeDirectoryServiceImpl.save(emp);
		return responce;
	}
	private boolean authenticateUser(String username, String userPass){
		boolean isvalidUser = false;
		UserPolicyReport user = new UserPolicyReport();
		try {		
					
			try{ final InputStream stream =getClass().getClassLoader().getResourceAsStream("properties/ldap.properties");
					properties.load(stream);
	 }catch (IOException ioe) {
	// TODO Auto-generated catch block
	ioe.printStackTrace();
	}
			String url=(String) properties.get("url");
			String neustar=(String) properties.get("neustar");
			String mvtl=(String) properties.get("mvtl");
			String micore=(String) properties.get("micore");
			String userDn=(String) properties.get("userDn");
			String password=(String) properties.get("password");
			LdapContextSource ctxsrc = new LdapContextSource();
			ctxsrc.setUrl(url);
			ctxsrc.setUserDn(userDn);
			ctxsrc.setPassword(password);
			ctxsrc.afterPropertiesSet();
			LdapTemplate ldapTemplate = new LdapTemplate(ctxsrc);
			
			List<String> list = new ArrayList<String>();
			
			if(ldapTemplate.authenticate(neustar, "(sAMAccountName="+username+")", userPass)){
				logger.info("Neustar User");
				list = ldapTemplate.search(query().base(neustar).where("sAMAccountName").is(username), new UserAttributesMapper());
				String jsonUser = list.get(0).toString();
				JsonElement jelement = new com.google.gson.JsonParser().parse(jsonUser);
				JsonObject  jobject = jelement.getAsJsonObject();
				user.setUsername(jobject.get("sAMAccountName").toString().replaceAll("\"", ""));
				isvalidUser = true;	
				
			}
			
			else if(ldapTemplate.authenticate(micore, "(sAMAccountName="+username+")", userPass)){
				logger.info("MICORE User");
				list = ldapTemplate.search(query().base(micore).where("sAMAccountName").is(username), new UserAttributesMapper());
				String jsonUser = list.get(0).toString();
				
				JsonElement jelement = new com.google.gson.JsonParser().parse(jsonUser);
				JsonObject  jobject = jelement.getAsJsonObject();
				
				
				user.setUsername(jobject.get("sAMAccountName").toString().replaceAll("\"", ""));
				isvalidUser = true;	
			}

			else if(ldapTemplate.authenticate(mvtl, "(sAMAccountName="+username+")", userPass)){
				logger.info("MVTL User");
				list = ldapTemplate.search(query().base(mvtl).where("sAMAccountName").is(username), new UserAttributesMapper());
				String jsonUser = list.get(0).toString();
				
				JsonElement jelement = new com.google.gson.JsonParser().parse(jsonUser);
				JsonObject  jobject = jelement.getAsJsonObject();
				
				
				user.setUsername(jobject.get("sAMAccountName").toString().replaceAll("\"", ""));
				isvalidUser = true;	
			}
			else{
				user = null;
				isvalidUser = false;
			}
			
		} catch(Exception ex){
			ex.printStackTrace();
			
		}
		return isvalidUser;
	}
	
	

		 public void pdfCreation(String SRC,String DEST,String UserName) throws IOException, DocumentException{
		 PdfReader reader = new PdfReader(SRC); // input PDF
	        PdfStamper stamper = new PdfStamper(reader,
	                new FileOutputStream(DEST)); // output PDF
	        BaseFont bf = BaseFont.createFont(
	                BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font
	        //loop on pages (1-based)
	        {
	            // get object for writing over the existing content;
	            // you can also use getUnderContent for writing in the bottom layer
	            PdfContentByte over = stamper.getOverContent(1);

	            // write text
	            over.beginText();
	            over.setFontAndSize(bf, 10);    // set font and size
	            over.setTextMatrix(290, 625);   // set x,y position (0,0 is at the bottom left)
	            over.showText(UserName);  // set text
	            over.endText();
	            
	            PdfContentByte over2 = stamper.getOverContent(4);

	            // write text
	            over2.beginText();
	            over2.setFontAndSize(bf, 10);    // set font and size
	            over2.setTextMatrix(390, 310);   // set x,y position (0,0 is at the bottom left)
	            over2.showText(UserName);  // set text
	            over2.endText();

	           
	        }
	     stamper.close();   
		 }
	
}
