package project.meetu.authentication;
import javax.mail.*;

public class MyAuthentication extends Authenticator { // 발신이메일 계정 인증
	 
    PasswordAuthentication passwordAuthentication;
 
    public MyAuthentication(String userName, String password) {
        passwordAuthentication = new PasswordAuthentication(userName, password);
    }
    
    public PasswordAuthentication getPasswordAuthentication() {
        return passwordAuthentication;
    }
}

