package ro.barbos.auth;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import ro.barbos.gater.dao.LoginDAO;
import ro.barbos.gater.model.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by radu on 10/11/2016.
 */
public class LocalRealm extends JdbcRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        final String username = userPassToken.getUsername();

        if (username == null) {
            System.out.println("Username is null.");
            return null;
        }

        // read password hash and salt from db
        final User user = LoginDAO.login(username, new String(userPassToken.getPassword()));

        if (user == null) {
            System.out.println("No account found for user [" + username + "]");
            return null;
        }

        // return salted credentials
        //SaltedAuthenticationInfo info = new LocalSaltedAuthentificationInfo(username, new String(userPassToken.getPassword()), "");
        SimpleAuthenticationInfo info = new LocalSimpleAuthentificationInfo(username, new String(userPassToken.getPassword()));


        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        Set<String> roleNames = new HashSet<>();
        if ("admin".equals(principals.getPrimaryPrincipal())) {
            roleNames.add("administrator");
            roleNames.add("operatorstock");
        } else {
            roleNames.add("operatorstock");
        }


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        //info.setStringPermissions(permissions);
        return info;
    }
}
