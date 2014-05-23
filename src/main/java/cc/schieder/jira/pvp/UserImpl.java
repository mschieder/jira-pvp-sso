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

import com.atlassian.crowd.embedded.api.User;

/**
 * simple User implementation for the embedded crowd API
 * 
 * @author Michael Schieder
 * 
 */
public class UserImpl implements User {
	private String name;
	private String displayName;
	private long directoryId;
	private String emailAddress;
	private boolean isActive;

	public UserImpl(long directoryId, String name, String displayName,
			String emailAddress, boolean isActive) {
		this.directoryId = directoryId;
		this.name = name;
		this.displayName = displayName;
		this.emailAddress = emailAddress;
		this.isActive = isActive;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(User user) {
		return name.compareTo(user.getName());
	}

	@Override
	public long getDirectoryId() {
		return directoryId;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
