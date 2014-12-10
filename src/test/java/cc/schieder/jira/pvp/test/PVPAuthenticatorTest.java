package cc.schieder.jira.pvp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import cc.schieder.jira.pvp.PVPAuthenticator;
import cc.schieder.jira.pvp.PVPConstants;
import cc.schieder.jira.pvp.PVPHelper;
import cc.schieder.jira.pvp.SSOnCookie;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.crowd.embedded.api.User;

public class PVPAuthenticatorTest {

	@Test
	public void testAddNewUser() {
		PVPAuthenticator auth = new PVPAuthenticator();

		FakeCrowdService crowdService = new FakeCrowdService();
		PVPHelper pvpHelper = Mockito.spy(new PVPHelper());
		Mockito.doReturn(crowdService).when(pvpHelper).getCrowdService();
		Mockito.doReturn(crowdService).when(pvpHelper).getUserUtil();
		ReflectionTestUtils
				.setField(new SSOnCookie(""), "pvpHelper", pvpHelper);

		MockHttpServletResponse resp = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		request.addHeader(PVPConstants.HTTP_HEADER_USERID,
				"user1@portal.test.org");
		request.addHeader(PVPConstants.HTTP_HEADER_MAIL, "user1@test.org");
		request.addHeader(PVPConstants.HTTP_HEADER_CN, "Test User1");
		request.addHeader(PVPConstants.HTTP_HEADER_ROLES,
				"project1-users();project1-developers");

		assertEquals("user1@test.org", auth.getUser(request, resp));

		assertEquals(1, crowdService.getUserList().size());
		User user = crowdService.getUserList().get(0);
		assertEquals("user1@portal.test.org", user.getName());
		assertEquals("user1@test.org", user.getEmailAddress());
		assertEquals("Test User1", user.getDisplayName());
		List<Group> groups = crowdService.getUser2Group().get(user);
		assertEquals(2, groups.size());
		assertEquals("project1-users", groups.get(0).getName());
		assertEquals("project1-developers", groups.get(1).getName());

	}
	
	@Test
	public void testRemoteHostSecurity() throws Exception{
		PVPAuthenticator auth = new PVPAuthenticator();
		Map<String, String> params = new LinkedHashMap<>();
		params.put(PVPHelper.INIT_PARAM_PVP_HOSTNAMES, "google.com,testhost2");
		auth.init(params, new FakeSecurityConfig());
	
		
		FakeCrowdService crowdService = new FakeCrowdService();
		PVPHelper pvpHelper = Mockito.spy(new PVPHelper());
		Mockito.doReturn(crowdService).when(pvpHelper).getCrowdService();
		Mockito.doReturn(crowdService).when(pvpHelper).getUserUtil();
		ReflectionTestUtils
				.setField(new SSOnCookie(""), "pvpHelper", pvpHelper);

		MockHttpServletResponse resp = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		request.addHeader(PVPConstants.HTTP_HEADER_USERID,
				"user1@portal.test.org");

		//TODO: implement test case (use mockito for all getCrowdService calls)
//		assertNull(auth.getUser(request, resp));	//no hostname
//		
//		request.setRemoteAddr("xxxx");
//		assertNull(auth.getUser(request, resp));	//invalid hostname
//		
		request.setRemoteAddr("google.com");
		assertNotNull(auth.getUser(request, resp));	//ok
		
	}
}
