package cc.schieder.jira.pvp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import cc.schieder.jira.pvp.PVPHelper;

public class PVPHelperTest {

	@Test
	public void testParsePVPRoles(){
		String roleString = null;
		List<String> roles = null;
		//PVP Standard
		roleString = "project1-users();project1-developers";	
		roles = new PVPHelper().parsePVPRoles(roleString);
		assertEquals(2, roles.size());
		assertTrue(roles.contains("project1-users"));
		assertTrue(roles.contains("project1-developers"));
	
		//STAT.AT-Standard
		roleString = "GROUPS(NAME=jira-users,NAME=edv-users,NAME=edv-developers,NAME=edv-administrators)";
		roles = new PVPHelper().parsePVPRoles(roleString);
		assertEquals(4, roles.size());
		assertTrue(roles.contains("jira-users"));
		assertTrue(roles.contains("edv-users"));
		assertTrue(roles.contains("edv-developers"));
		assertTrue(roles.contains("edv-administrators"));
	

	}
}
