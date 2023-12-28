//package lk.ijse.channelingCenter.controller;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.googleapis.json.GoogleJsonError;
//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.model.*;
//import javafx.event.ActionEvent;
//import javafx.scene.control.TextField;
//import org.apache.commons.codec.binary.Base64;
//
//import javax.mail.Multipart;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.file.Paths;
//import java.util.Properties;
//import java.util.Set;
//
//import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;
//import static javax.mail.Message.RecipientType.TO;
//
//public class ForgotPasswordApp {
//    private static final String EMAIL = "travelagentsrilanka1@gmail.com";
//    private final Gmail service;
//    public TextField emailField;
//
//    public ForgotPasswordApp() throws Exception {
//        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//        service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
//                .setApplicationName("Test Mailer")
//                .build();
//
//    }
//
//    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
//            throws IOException {
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(ForgotPasswordApp.class.getResourceAsStream("/api/client_secret_697004834084-cauqm6tjjbi8u2jhms133o9cu875l3mq.apps.googleusercontent.com (1).json")));
//
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
//                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
//                .setAccessType("offline")
//                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public static void setEmailCom(String email, int randNum) throws Exception {
//        new ForgotPasswordApp().sendMail("Your, Agent account reset OTP", "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "    <style>\n" +
//                "        body {\n" +
//                "            font-family: Arial, sans-serif;\n" +
//                "            background-color: #f4f4f4;\n" +
//                "            text-align: center;\n" +
//                "        }\n" +
//                "        .container {\n" +
//                "            background-color: #ffffff;\n" +
//                "            padding: 20px;\n" +
//                "            border-radius: 5px;\n" +
//                "            box-shadow: 0 0 10px rgba(0,0,0,0.2);\n" +
//                "            text-align: center;\n" +
//                "        }\n" +
//                "    </style>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <div class=\"container\">\n" +
//                "        <h1>Here's your Agent OTP code, @" + email + "!</h1>\n" +
//                "        <h2>Your One-Time Password (OTP) is: <strong>" + randNum + "</strong></h2>\n" +
//                "    </div>\n" +
//                "</body>\n" +
//                "</html>\n", email);
//    }
//
//    public void sendMail(String subject, String message, String mail) throws Exception {
//
//        System.out.println("1");
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//        MimeMessage email = new MimeMessage(session);
//        email.setFrom(new InternetAddress(EMAIL));
//        System.out.println(2);
//        email.addRecipient(TO, new InternetAddress(mail));
//        email.setSubject(subject);
////        email.setText(message);
//        System.out.println(2.1);
//        MimeBodyPart mimeBodyPart = new MimeBodyPart();
//        mimeBodyPart.setContent(message, "text/html; charset=utf-8");
//
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(mimeBodyPart);
//
//        email.setContent(multipart);
//        System.out.println(2.2);
//        // Encode and wrap the MIME message into a gmail message
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        email.writeTo(buffer);
//        byte[] rawMessageBytes = buffer.toByteArray();
//        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
//        Message msg = new Message();
//        msg.setRaw(encodedEmail);
//
//        try {
//            System.out.println(2.3);
//            msg = service.users().messages().send("me", msg).execute();
//            System.out.println("Message id: " + msg.getId());
//            System.out.println(msg.toPrettyString());
//
//        } catch (GoogleJsonResponseException e) {
//            System.out.println(2.4);
//            GoogleJsonError error = e.getDetails();
//            if (error.getCode() == 403) {
//                System.err.println("Unable to send message: " + e.getDetails());
//            } else {
//                throw e;
//            }
//        }
//        System.out.println(3);
//    }
//
//
//    public void setEmailCom(ActionEvent actionEvent) throws Exception {
//        setEmailCom(emailField.getText(),1234);
//    }
//
//
//    public void verifyOTP(ActionEvent actionEvent) {
//
//    }
//}

package lk.ijse.channelingCenter.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.channelingCenter.dto.LoginDto;
import lk.ijse.channelingCenter.DAO.Impl.LoginDAOImpl;
import org.apache.commons.codec.binary.Base64;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;
import static javax.mail.Message.RecipientType.TO;

public class ForgotPasswordApp {
    private static final String EMAIL = "nishamaheeka123@gmail.com";
    private final Gmail service;
    public TextField emailField;

    @FXML
    private TextField txtConfromPassword;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField otpTextField;
    @FXML
    private Button btnReset;

    @FXML
    private Pane changePasswordVisiblePane;

    @FXML
    private Button btnVerifiyOtp;

    @FXML
    private Pane visiblePane;
    static int randNum;

    @FXML
    void btnResetOnAction(ActionEvent event) {
        String password = txtpassword.getText();
        String confirmPassword = txtConfromPassword.getText();

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password fields cannot be empty.").show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Password and Confirm Password do not match.").show();
            return;
        }

        String email = emailField.getText(); // assuming you have an email field

        try {
            LoginDAOImpl loginDAOImpl = new LoginDAOImpl();
            LoginDto loginDto = loginDAOImpl.getUserByEmail(email);

            if (loginDto != null) {
                loginDto.setPassword(password);
                loginDAOImpl.update(loginDto);

                new Alert(Alert.AlertType.CONFIRMATION, "Password has been reset.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "User not found for the given email.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset password. " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public ForgotPasswordApp() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
                .setApplicationName("Test Mailer")
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(ForgotPasswordApp.class.getResourceAsStream("/api/client_secret_697004834084-cauqm6tjjbi8u2jhms133o9cu875l3mq.apps.googleusercontent.com (1).json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

//    public static void setEmailCom(String email, int randNum) throws Exception {
//        new ForgotPasswordApp().sendMail("Your, User account reset OTP", generateHtmlContent(email, randNum), email);
//    }

    private static String generateHtmlContent(String email, int randNum) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .container {\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0,0,0,0.2);\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Here's your Forgot Password OTP code, @" + email + "!</h1>\n" +
                "        <h2>Your One-Time Password (OTP) is: <strong>" + randNum + "</strong></h2>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public void sendMail(String subject, String message, String mail) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(EMAIL));
        email.addRecipient(TO, new InternetAddress(mail));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        email.setContent(multipart);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {
            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }

    int random = 0;

    public void setEmailCom(ActionEvent actionEvent) throws Exception {
        // Generate a random 6-digit OTP
        random = generateRandomOTP();

        // Send the generated OTP via email
//        setEmailCom(emailField.getText(), randNum);
        new ForgotPasswordApp().sendMail("Your, User account reset OTP", generateHtmlContent(emailField.getText(), random), emailField.getText());

        // Display a confirmation message
        new Alert(Alert.AlertType.INFORMATION, "OTP sent to your email.").show();
        visiblePane.setVisible(true);
    }

//    public void verifyOTP(ActionEvent actionEvent) {
//        boolean isOTPValid = validateOTP();
//        String enteredOTP = generateRandomOTP(); // Get the entered OTP from the user
//        int expectedOTP = getGeneratedOTP(); // Replace with the actual OTP you generated or sent
//if (isOTPValid) {
//    if (expectedOTP == Integer.parseInt(enteredOTP)) {
//        new Alert(Alert.AlertType.INFORMATION, "OTP verified. You can reset your password now.").show();
//        changePasswordVisiblePane.setVisible(true);
//    } else {
//        new Alert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.").show();
//    }
//}else{
//
//}
//    }
//
//    private String getEnteredOTP() {
//        return "123456"; // Assuming you have a TextField named otpTextField for user input
//    }
//
//    private int getGeneratedOTP() {
//        return true ? 123456 : 0; // Assuming you have a TextField named otpTextField for user input
//    }

    @FXML
    void btnVerifiyOtpOnAction(ActionEvent event) {
        boolean isOTPValid = validateOTP();
        String enteredOTP = String.valueOf(otpTextField.getText()); // Get the entered OTP from the user
        int expectedOTP = random; // Replace with the actual OTP you generated or sent

        if (isOTPValid) {

            new Alert(Alert.AlertType.INFORMATION, "OTP verified. You can reset your password now.").show();
            changePasswordVisiblePane.setVisible(true);
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.").show();
        }
    }

    private int generateRandomOTP() {

        SecureRandom random = new SecureRandom();
        int randNum = 100000 + random.nextInt(900000);
        System.out.println(randNum);// Generates a 6-digit random number
        return randNum;
    }

    private boolean validateOTP() {

//        boolean isNumberValid = CheckOTP(otpTextField.getText());

        if (!otpTextField.getText().equals(String.valueOf(random))) {
            otpTextField.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(otpTextField).play();
            return false;
        } else {
            otpTextField.setStyle("-fx-border-color: green");
        }
        return true;
    }

//    private boolean CheckOTP(String enteredOTP) {
////        if (generateRandomOTP() == Integer.parseInt(otpTextField.getText())) {
//        if (enteredOTP.equals(random)) {
//            return true;
//        }
//        return false;
//    }
}

