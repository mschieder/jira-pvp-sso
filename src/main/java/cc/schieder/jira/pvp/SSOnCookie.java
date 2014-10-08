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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple SSOnCookie implementation as suggested here:
 * https://docs.atlassian.com/atlassian-seraph/latest/sso.html
 * 
 * @author Michael Schieder
 * 
 */
public class SSOnCookie {

	private final static String COOKIE_NAME = "pvpJira";
	private static PVPHelper pvpHelper = new PVPHelper();
	private String loginId;

	public SSOnCookie(String loginId) {
		this.loginId = loginId;
	}

	public static SSOnCookie getSSOCookie(HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getCookies() != null) {
			for (Cookie next : request.getCookies()) {
				if (next.getName().equals(COOKIE_NAME)) {
					return new SSOnCookie(next.getValue());
				}
			}
		}

		String username = pvpHelper.parseHttpRequest(request);
		SSOnCookie ssoCookie = null;
		if (username != null) {
			response.addCookie(new Cookie(COOKIE_NAME, username));
			ssoCookie = new SSOnCookie(username);
		}

		return ssoCookie;
	}

	public boolean isExpired() {
		return false;
	}

	public String getLoginId() {
		return loginId;
	}

}
