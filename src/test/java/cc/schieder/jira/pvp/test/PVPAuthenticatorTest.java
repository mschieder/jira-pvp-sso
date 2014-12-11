package cc.schieder.jira.pvp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import cc.schieder.jira.pvp.JiraServiceAccess;
import cc.schieder.jira.pvp.PVPAuthenticator;
import cc.schieder.jira.pvp.PVPConstants;
import cc.schieder.jira.pvp.PVPHelper;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.crowd.embedded.api.User;

public class PVPAuthenticatorTest {

    private PVPAuthenticator auth = null;

    private MockHttpServletResponse resp = null;

    private MockHttpServletRequest request = null;

    private FakeCrowdService crowdService = null;

    @Before
    public void setUp() {
        auth = new PVPAuthenticator();

        // inject a service spy
        JiraServiceAccess jiraService = Mockito.spy(new JiraServiceAccess());
        crowdService = new FakeCrowdService();
        Mockito.doReturn(crowdService).when(jiraService).getCrowdService();
        Mockito.doReturn(crowdService).when(jiraService).getUserUtil();
        ReflectionTestUtils.setField(JiraServiceAccess.Singleton, "Singleton", jiraService);

        resp = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
    }

    @Test
    public void testAddNewUser() {
        request.addHeader(PVPConstants.HTTP_HEADER_USERID, "user1@portal.test.org");
        request.addHeader(PVPConstants.HTTP_HEADER_MAIL, "user1@test.org");
        request.addHeader(PVPConstants.HTTP_HEADER_CN, "Test User1");
        request.addHeader(PVPConstants.HTTP_HEADER_ROLES, "project1-users();project1-developers");

        Principal p = auth.getUser(request, resp);
        assertNotNull(p);
        assertEquals("user1@portal.test.org", p.getName());

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
    public void testRemoteHostSecurity() throws Exception {
        // init the authenticator with a set of trusted hosts
        Map<String, String> params = new LinkedHashMap<>();
        params.put(PVPHelper.INIT_PARAM_TRUSTED_HOSTS, "schieder.cc,testhost2");
        auth.init(params, new FakeSecurityConfig());

        request.addHeader(PVPConstants.HTTP_HEADER_USERID, "user2@portal.test.org");

        assertNull(auth.getUser(request, resp)); // no trusted hostname (null)
                                                 // -> no cookie

        request.setRemoteAddr("xxxx");
        assertNull(auth.getUser(request, resp)); // no trusted hostname -> no
                                                 // cookie

        request.setRemoteAddr("schieder.cc");
        assertNotNull(auth.getUser(request, resp)); // trusted hostname => OK

    }
}
