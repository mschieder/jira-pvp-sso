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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atlassian.crowd.embedded.api.CrowdService;
import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.util.UserUtil;

public class PVPHelper {
	public final static String INIT_PARAM_PVP_HOSTNAME = "valid.remote.hostname";

	private Logger logger = Logger.getLogger(this.getClass());

	public String parseHttpRequest(HttpServletRequest request) {
		String username = request.getHeader(PVPConstants.HTTP_HEADER_USERID);

		if (username != null) {
			String cn = request.getHeader(PVPConstants.HTTP_HEADER_CN);
			String email = request.getHeader(PVPConstants.HTTP_HEADER_MAIL);
			String roles = request.getHeader(PVPConstants.HTTP_HEADER_ROLES);

			// map the portal roles to jira groups, ignore all portal attributes
			logger.info("pvp roles for user " + username + ": " + roles);
			List<String> pvpgroups = parsePVPRoles(roles);

			// get the crowd service
			CrowdService cs = getCrowdService();
			UserUtil userUtil = getUserUtil();

			User user = cs.getUser(username);

			try {
				Set<Group> oldGroups = null;
				if (user == null) {
					user = new UserImpl(0, username, cn, email, true);
					cs.addUser(user, "NOT_NEEDED");
					logger.info(String.format(
							"creating user %s (cn: %s, email: %s)", username,
							cn, email));
					oldGroups = new LinkedHashSet<>();
				} else {
					user = new UserImpl(0, username, cn, email, true);
					cs.updateUser(user);
					logger.info(String.format(
							"updating user %s (cn: %s, email: %s)", username,
							cn, email));
					// get the actual groups for the user
					oldGroups = new LinkedHashSet<>(
							userUtil.getGroupsForUser(username));
				}

				for (String next : pvpgroups) {
					Group g = cs.getGroup(next);
					
					if (g == null){
						logger.error("group '" + next + "' is not a valid JIRA group. ignoring group.");
						continue;
					}
					if (!cs.isUserMemberOfGroup(user, g)) {
						logger.info(String.format("adding user %s to group %s",
								username, next));
						cs.addUserToGroup(user, g);
					}
					// remove the group form the old group set
					oldGroups.remove(g);
				}

				// remove all remaining groups
				for (Group next : oldGroups) {
					logger.info(String.format("removing user %s from group %s",
							username, next.getName()));
					cs.removeUserFromGroup(user, next);
				}

			} catch (Exception e) {
				Logger.getLogger(this.getClass()).error(
						"error while writing to crowd ", e);
			}
		}
		return username;
	}

	public List<String> parsePVPRoles(String roleString) {
		//STAT.AT Fix:
		roleString = roleString.replaceAll("GROUPS\\(|\\)|NAME=", "");
		roleString = roleString.replaceAll(",", ";");
		//TODO: generisches Pattern
		
		List<String> roles = new ArrayList<>();
		if (roleString != null) {
			for (String next : roleString.split(";")) {
				if (next.indexOf("(") != -1) {
					next = next.substring(0, next.indexOf("("));
				}
				roles.add(next);
			}
		}
		return roles;
	}

	public CrowdService getCrowdService() {
		return ComponentAccessor.getCrowdService();
	}

	public UserUtil getUserUtil() {
		return ComponentAccessor.getUserUtil();
	}
}
