package login;
import java.io.Console;

import common.Utility;

public class UserInput {

    static Console console;
    User user;

    public UserInput(){
        console = System.console();
    }

    public User getUser(){
        return this.user;
    }

    public String input_for_login_registration(){
        return Utility.enter_in_console("Choose from following options (1/2):"
                                    +"\n1. Login"
                                    +"\n2. Register", console);
    }

    public boolean login(){
        System.out.println("\n\n\t\t\t*********** Login ************\t\t\t\n"); 

        String username = Utility.enter_in_console("Enter username:", console);
        String password = Utility.enter_in_console("Enter password:", console);
        UserProfileIO userProfileIO = new UserProfileIO();
        user = userProfileIO.check_credentials(username, password);
        if(user != null){
            int count = 1;
            while(count<=3){
                String input_answer = Utility.enter_in_console("Security Question : "+user.getSecurity_question(), console);
                if(input_answer.equalsIgnoreCase(user.getSecurity_answer())){
                    return true;
                }
                else{
                    int remaining_attempts = 3- count;
                    System.out.println("Incorrect answer. "+ remaining_attempts+" attempts left.");
                }
                count++;
            }
        }
        else{
            System.out.println("Incorrect username/password. Please try again");
        }
        return false;

    }

    public boolean register(){

        System.out.println("\n\n\t\t\t*********** Register ************\t\t\t\n"); 
        LoginRegisterStatus result = LoginRegisterStatus.USER_ALREADY_EXISTS;

        while(result == LoginRegisterStatus.USER_ALREADY_EXISTS){
            String username = Utility.enter_in_console("Enter username:", console);
            String password = Utility.enter_in_console("Enter password:", console);
            String security_question = Utility.enter_in_console("Enter a security question:", console);
            String security_question_answer = Utility.enter_in_console("Answer of the security question:", console);
            user = new User(null, username, password, security_question, security_question_answer);
            UserProfileIO userProfileIO = new UserProfileIO();
            
            //System.out.println(user.toString());
            result= userProfileIO.add_user(user);    
            if(result == LoginRegisterStatus.SUCCESS){
                System.out.println("Registered successfully!");
                System.out.println("Redirecting to login...");
                Utility.sleep(3);
                return true;
            }    
            else if(result == LoginRegisterStatus.USER_ALREADY_EXISTS){
                System.out.println("Username already exists. Please try again.");
                Utility.sleep(3);
                continue;
            }
            else{
                Utility.system_abort();
            }
        }
        return false;
        
    }

    public String select_dbms_operation(){
        return Utility.enter_in_console("Choose from following options (1/2/3/4): "
                                    +"\n1. Write Queries "
                                    +"\n2. Export "
                                    +"\n3. Data Model "
                                    +"\n4. Analytics", console);
    }

}
