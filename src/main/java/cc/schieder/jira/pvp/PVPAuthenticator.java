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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.seraph.auth.AuthenticatorException;
import com.atlassian.seraph.auth.DefaultAuthenticator;
import com.atlassian.seraph.config.SecurityConfig;

/**
 * SSO Authenticator using the basuc Portalverbund 1.x http headers
 * @author Michael Schieder
 * 
 */
public class PVPAuthenticator extends DefaultAuthenticator {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(PVPAuthenticator.class);
	private String validRemoteHost = null;

	@Override
	public void init(Map<String, String> params, SecurityConfig config) {
		super.init(params, config);
		validRemoteHost = params.get(PVPHelper.INIT_PARAM_PVP_HOSTNAME);
	}

	protected String getRemoteHostname(HttpServletRequest request) {
		try {
			InetAddress addr = InetAddress.getByName(request.getRemoteAddr());
			return addr.getHostName();
		} catch (UnknownHostException e) {
			log.error("error while getting remote hostname", e);
			return "unknown";
		}
	}

	protected boolean isRequestFromValidRemoteHost(HttpServletRequest request) {
		if (validRemoteHost != null) {
			String remoteHostname = getRemoteHostname(request);
			if (!validRemoteHost.equals(remoteHostname)) {
				log.error("invalid request from host " + remoteHostname);
				return false;
			}
		}
		return true;
	}

	@Override
	public Principal getUser(HttpServletRequest request,
			HttpServletResponse response) {
		if (!isRequestFromValidRemoteHost(request)) {
			return null;
		}

		Principal user = null;
		try {
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							DefaultAuthenticator.LOGGED_IN_KEY) != null) {
				log.info("Session found; user already logged in");
				user = (Principal) request.getSession().getAttribute(
						DefaultAuthenticator.LOGGED_IN_KEY);
			} else {
				SSOnCookie ssoCookie = SSOnCookie.getSSOCookie(request,
						response);
				log.info("Got SSOnCookie " + ssoCookie);

				if (ssoCookie != null && !ssoCookie.isExpired()) {
					// Seamless login from intranet
					log.info("Trying seamless Single Sign-on...");
					String username = ssoCookie.getLoginId();
					log.info("Got username = " + username);
					if (username != null) {
						user = getUser(username);
						log.info("Logged in via SSO, with User " + user);
						request.getSession().setAttribute(
								DefaultAuthenticator.LOGGED_IN_KEY, user);
						request.getSession().setAttribute(
								DefaultAuthenticator.LOGGED_OUT_KEY, null);
					}
				} else {
					log.info("SSOCookie is null; redirecting");
					// user was not found, or not currently valid
					return null;
				}
			}
		} catch (Exception e) { // catch class cast exceptions
			log.warn("Exception: " + e, e);
		}
		return user;
	}

	@Override
	protected boolean authenticate(Principal principal, String password)
			throws AuthenticatorException {
		// trusted zone
		return true;

	}

	@Override
	protected Principal getUser(String username) {
		return ComponentAccessor.getCrowdService().getUser(username);
	}

}
