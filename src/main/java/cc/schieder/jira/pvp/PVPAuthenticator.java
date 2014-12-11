/*
 * The MIT License
 *
 * Copyright 2014 Michael Schieder <michael@schieder.cc>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cc.schieder.jira.pvp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.atlassian.seraph.auth.AuthenticatorException;
import com.atlassian.seraph.auth.DefaultAuthenticator;
import com.atlassian.seraph.config.SecurityConfig;

/**
 * SSO Authenticator using the basic Portalverbund 1.x http headers
 * 
 * @author Michael Schieder
 */
public class PVPAuthenticator extends DefaultAuthenticator {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(PVPAuthenticator.class);

    private List<String> trustedHosts = null;

    @Override
    public void init(Map<String, String> params, SecurityConfig config) {
        super.init(params, config);
        trustedHosts = parseTrustedHosts(params.get(PVPHelper.INIT_PARAM_TRUSTED_HOSTS));
    }

    /**
     * parsed the trusted.hosts init-param
     * @param validRemoteHostString
     * @return
     */
    protected List<String> parseTrustedHosts(String validRemoteHostString) {
        List<String> hosts = null;
        if (validRemoteHostString != null) {
            hosts = new ArrayList<>();
            for (String next : validRemoteHostString.split(",")) {
                String addr = getIPAddress(next);
                if (addr != null) {
                    hosts.add(getIPAddress(next));
                }
            }
        }
        return hosts;
    }

    protected String getIPAddress(String hostname) {
        try {
            return InetAddress.getByName(hostname).getHostAddress();
        } catch (UnknownHostException e) {
            log.error("error while getting remote ip address", e);
            return null;
        }
    }

    protected boolean isTrustedHostRequest(HttpServletRequest request) {
        if (trustedHosts != null) {
            String ipAddress = getIPAddress(request.getRemoteAddr());
            return trustedHosts.contains(ipAddress);
        }
        return true;
    }

    @Override
    public Principal getUser(HttpServletRequest request, HttpServletResponse response) {
        Principal user = null;
        try {
            if (request.getSession() != null && request.getSession().getAttribute(DefaultAuthenticator.LOGGED_IN_KEY) != null) {
                log.info("Session found; user already logged in");
                user = (Principal) request.getSession().getAttribute(DefaultAuthenticator.LOGGED_IN_KEY);
            } else {
                if (!isTrustedHostRequest(request)) {
                    log.error("untrusted request from host " + request.getRemoteAddr());
                    return null;
                }
                
                SSOnCookie ssoCookie = SSOnCookie.getSSOCookie(request, response);
                log.info("Got SSOnCookie " + ssoCookie);

                if (ssoCookie != null && !ssoCookie.isExpired()) {
                    // Seamless login from intranet
                    log.info("Trying seamless Single Sign-on...");
                    String username = ssoCookie.getLoginId();
                    log.info("Got username = " + username);
                    if (username != null) {
                        user = getUser(username);
                        log.info("Logged in via SSO, with User " + user);
                        request.getSession().setAttribute(DefaultAuthenticator.LOGGED_IN_KEY, user);
                        request.getSession().setAttribute(DefaultAuthenticator.LOGGED_OUT_KEY, null);
                    }
                } else {
                    log.info("SSOCookie is null; redirecting");
                    // user was not found, or not currently valid
                    return null;
                }
            }
        } catch (Exception e) { // catch class cast exceptions
            log.error("Exception: " + e.getMessage(), e);
        }
        return user;
    }

    @Override
    protected boolean authenticate(Principal principal, String password) throws AuthenticatorException {
        // trusted zone
        return true;

    }

    @Override
    protected Principal getUser(String username) {
        return JiraServiceAccess.Singleton.getCrowdService().getUser(username);
    }

}
