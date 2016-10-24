package ro.barbos.auth;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * Created by radu on 10/12/2016.
 */
public class LocalSimpleAuthentificationInfo extends SimpleAuthenticationInfo {

    private static final long serialVersionUID = -5467967895187234984L;

    private final String username;
    private final String password;

    public LocalSimpleAuthentificationInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public PrincipalCollection getPrincipals() {
        PrincipalCollection coll = new SimplePrincipalCollection(username, username);
        return coll;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

}
