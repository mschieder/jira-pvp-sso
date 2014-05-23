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

/**
 * Important http header names for the Portalverbundportokoll (PVP) Spec:
 * http://reference.e-government.gv.at/uploads/media/PVP_1-9-2_20110120.pdf
 * 
 * @author Michael Schieder
 * 
 */
public class PVPConstants {

	/**
	 * common name
	 */
	public final static String HTTP_HEADER_CN = "X-AUTHENTICATE-cn";
	/**
	 * userid, usually with the syntax first.last@company.com
	 */
	public final static String HTTP_HEADER_USERID = "X-AUTHENTICATE-UserID";
	/**
	 * email address
	 */
	public final static String HTTP_HEADER_MAIL = "X-AUTHENTICATE-mail";
	/**
	 * all PVP roles
	 */
	public final static String HTTP_HEADER_ROLES = "X-AUTHORIZE-roles";

}
