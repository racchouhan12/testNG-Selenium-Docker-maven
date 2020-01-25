package com.main.utilities;


import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.main.utilities.helpers.KEYS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(Email.class);
    private static com.main.utilities.OneInstance oneInstance = OneInstance.getInstance();

    private Email() {

    }

    public static void triggerEmail(String strBody, String strSubject,
                                    String strTOList, String strFrom) throws IOException {
        PropertyFileReader pr = new PropertyFileReader(oneInstance.getAsString(KEYS.PROPERTY_FILE_PATH.name())
                + "/email.properties");
        String host = pr.getProperty("host").trim();

        pr.tearDown();

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, null);
        // session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(strFrom));

            // Set To: header field of the header.
            String[] emailList = strTOList.split(",");
            InternetAddress[] toAddress = new InternetAddress[emailList.length];
            for (int i = 0; i < emailList.length; i++) {
                toAddress[i] = new InternetAddress(emailList[i]);
            }

            for (InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            // Set Subject: header field

            message.setSubject(strSubject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setContent(strBody, "text/html");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part

            // Send the complete message parts
            message.setContent(multipart);
            // addAttachment(multipart, CommonUtilities.appPath()
            // + "\\target\\surefire-reports\\emailable-report.html",
            // "emailable-report.html");
            addAttachment(
                    multipart,
                    oneInstance.getAsString(KEYS.REPORT_PATH.name() + "\\ExtentReportResults.html"),
                    "ExtentReport.html");

            // Send message
            Transport.send(message);
            LOGGER.info("Sent message successfully.... to " + strTOList);

        } catch (Exception mex) {
            LOGGER.error("Exception Occured while Sending Email", mex);

        }
    }


    public static void triggerEmail(String attachmentPath,
                                    String attachmentName, String strBody, String strSubject,
                                    String strTOList, String strFrom) throws IOException {
        try {
            PropertyFileReader pr = new PropertyFileReader(oneInstance.getAsString(KEYS.PROPERTY_FILE_PATH.name())
                    + "/email.properties");
            String host = pr.getProperty("hostName").trim();


            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", true);

            // Get the default Session object.
            Session session = Session.getInstance(properties,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("rakeshsingh.chouhan@ness.com", "C00l@rac22");
                        }
                    });
            // session.setDebug(true);

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(strFrom));

            // Set To: header field of the header.
            String[] emailList = strTOList.split(",");
            InternetAddress[] toAddress = new InternetAddress[emailList.length];
            for (int i = 0; i < emailList.length; i++) {
                toAddress[i] = new InternetAddress(emailList[i]);
            }

            for (InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            // Set Subject: header field

            message.setSubject(strSubject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setContent(strBody, "text/html");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part

            // Send the complete message parts
            message.setContent(multipart);
            // addAttachment(multipart, CommonUtilities.appPath()
            // + "\\target\\surefire-reports\\emailable-report.html",
            // "emailable-report.html");
            addAttachment(multipart, attachmentPath, attachmentName);

            // Send message
            Transport.send(message);
            LOGGER.info("Sent message successfully.... to " + strTOList);

        } catch (Exception mex) {
            LOGGER.error("Exception Occured while Sending Email", mex.getMessage());
            mex.printStackTrace();

        }
    }

    private static void addAttachment(Multipart multipart, String FilePath,
                                      String filename) {
        try {
            DataSource source = new FileDataSource(FilePath);
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setDataHandler(new DataHandler(source));

            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
        } catch (MessagingException e) {
            LOGGER.error("Exception Occured while Attaching File", e);
        }
    }
}

