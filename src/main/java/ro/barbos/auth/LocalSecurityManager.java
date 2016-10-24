package ro.barbos.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import ro.barbos.gater.dao.DataAccess;


/**
 * Created by radu on 10/11/2016.
 */
public class LocalSecurityManager {
    static {
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
    }

    public static final LocalSecurityManager INSTANCE = new LocalSecurityManager();

    public static boolean loginUser(String userName, String password) {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            //collect user principals and credentials in a gui specific manner
            //such as username/password html form, X509 certificate, OpenID, etc.
            //We'll use the username/password example here since it is the most common.
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            //this is all you have to do to support 'remember me' (no config - built in!):
            token.setRememberMe(true);

            try {
                currentUser.login(token);
                System.out.println("User [" + currentUser.getPrincipal().toString() + "] logged in successfully.");

                // save current username in the session, so we have access to our User model
                currentUser.getSession().setAttribute("username", userName);
                return true;
            } catch (UnknownAccountException uae) {
                System.out.println("There is no user with username of "
                        + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                System.out.println("Password for account " + token.getPrincipal()
                        + " was incorrect!");
            } catch (LockedAccountException lae) {
                System.out.println("The account for username " + token.getPrincipal()
                        + " is locked.  "
                        + "Please contact your administrator to unlock it.");
            }
        } else {
            return true; // already logged in
        }

        return false;
    }

    public static void main(String[] args) {
        DataAccess.getInstance().init();
        System.out.println("testing admin");
        LocalSecurityManager.loginUser("admin", "admin");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            System.out.println("is logged");
            System.out.println(SecurityUtils.getSubject().hasRole("administrator"));
            System.out.println(SecurityUtils.getSubject().hasRole("operatorstock"));
        }
        System.out.println("testing stoc");
        SecurityUtils.getSubject().logout();
        LocalSecurityManager.loginUser("stoc", "stoc1234");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            System.out.println("is logged");
            System.out.println(SecurityUtils.getSubject().hasRole("administrator"));
            System.out.println(SecurityUtils.getSubject().hasRole("operatorstock"));
        }
    }
}
