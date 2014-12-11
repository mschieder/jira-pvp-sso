package cc.schieder.jira.pvp.test;

import java.util.List;

import com.atlassian.seraph.SecurityService;
import com.atlassian.seraph.auth.AuthenticationContext;
import com.atlassian.seraph.auth.Authenticator;
import com.atlassian.seraph.auth.RoleMapper;
import com.atlassian.seraph.config.RedirectPolicy;
import com.atlassian.seraph.config.SecurityConfig;
import com.atlassian.seraph.controller.SecurityController;
import com.atlassian.seraph.elevatedsecurity.ElevatedSecurityGuard;
import com.atlassian.seraph.interceptor.Interceptor;
import com.atlassian.seraph.service.rememberme.RememberMeService;

public class FakeSecurityConfig implements SecurityConfig {

	@Override
	public List<SecurityService> getServices() {
		
		return null;
	}

	@Override
	public String getLoginURL() {
		
		return null;
	}

	@Override
	public String getLoginForwardPath() {
		
		return null;
	}

	@Override
	public String getLinkLoginURL() {
		
		return null;
	}

	@Override
	public String getLogoutURL() {
		
		return null;
	}

	@Override
	public String getOriginalURLKey() {
		
		return null;
	}

	@Override
	public Authenticator getAuthenticator() {
		
		return null;
	}

	@Override
	public AuthenticationContext getAuthenticationContext() {
		
		return null;
	}

	@Override
	public SecurityController getController() {
		
		return null;
	}

	@Override
	public RoleMapper getRoleMapper() {
		
		return null;
	}

	@Override
	public ElevatedSecurityGuard getElevatedSecurityGuard() {
		
		return null;
	}

	@Override
	public RememberMeService getRememberMeService() {
		
		return null;
	}

	@Override
	public RedirectPolicy getRedirectPolicy() {
		
		return null;
	}

	@Override
	public <T extends Interceptor> List<T> getInterceptors(
			Class<T> desiredInterceptorClass) {
		
		return null;
	}

	@Override
	public void destroy() {
		
		
	}

	@Override
	public String getLoginCookiePath() {
		
		return null;
	}

	@Override
	public String getLoginCookieKey() {
		
		return null;
	}

	@Override
	public String getWebsudoRequestKey() {
		
		return null;
	}

	@Override
	public boolean isInsecureCookie() {
		
		return false;
	}

	@Override
	public int getAutoLoginCookieAge() {
		
		return 0;
	}

	@Override
	public String getCookieEncoding() {
		
		return null;
	}

	@Override
	public String getAuthType() {
		
		return null;
	}

	@Override
	public boolean isInvalidateSessionOnLogin() {
		
		return false;
	}

	@Override
	public boolean isInvalidateSessionOnWebsudo() {
		
		return false;
	}

	@Override
	public List<String> getInvalidateSessionExcludeList() {
		
		return null;
	}

	@Override
	public List<String> getInvalidateWebsudoSessionExcludeList() {

		return null;
	}

}
