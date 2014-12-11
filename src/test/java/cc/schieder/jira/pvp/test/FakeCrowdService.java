package cc.schieder.jira.pvp.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.atlassian.crowd.embedded.api.CrowdService;
import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.crowd.embedded.api.GroupWithAttributes;
import com.atlassian.crowd.embedded.api.Query;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.api.UserWithAttributes;
import com.atlassian.crowd.exception.FailedAuthenticationException;
import com.atlassian.crowd.exception.InvalidCredentialException;
import com.atlassian.crowd.exception.InvalidMembershipException;
import com.atlassian.crowd.exception.InvalidUserException;
import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.crowd.exception.runtime.GroupNotFoundException;
import com.atlassian.crowd.exception.runtime.OperationFailedException;
import com.atlassian.crowd.exception.runtime.UserNotFoundException;
import com.atlassian.jira.bc.project.component.ProjectComponent;
import com.atlassian.jira.exception.AddException;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.exception.PermissionException;
import com.atlassian.jira.exception.RemoveException;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserUtil;

/**
 * Fake services (crowd and userutil) for unit testing
 * 
 * @author Michael Schieder
 * 
 */
public class FakeCrowdService implements CrowdService, UserUtil {

    private List<User> userList = new ArrayList<>();
    private Map<User, List<Group>> user2Group = new LinkedHashMap<>();

    public List<User> getUserList() {
        return userList;
    }

    public Map<User, List<Group>> getUser2Group() {
        return user2Group;
    }

   
    
	@Override
	public void addToJiraUsePermission(User arg0) throws PermissionException {

	}

	@Override
	public void addUserToGroup(Group arg0, User arg1)
			throws PermissionException, AddException {

	}

	@Override
	public void addUserToGroups(Collection<Group> arg0, User arg1)
			throws PermissionException, AddException {

	}

	@Override
	public boolean canActivateNumberOfUsers(int arg0) {

		return false;
	}

	@Override
	public boolean canActivateUsers(Collection<String> arg0) {

		return false;
	}

	@Override
	public void changePassword(User arg0, String arg1)
			throws com.atlassian.crowd.exception.UserNotFoundException,
			InvalidCredentialException, OperationNotPermittedException,
			PermissionException {

	}

	@Override
	public void clearActiveUserCount() {

	}

	@Override
	public User createUserNoNotification(String arg0, String arg1, String arg2,
			String arg3, Long arg4) throws PermissionException, CreateException {

		return null;
	}

	@Override
	public User createUserNoNotification(String arg0, String arg1, String arg2,
			String arg3) throws PermissionException, CreateException {

		return null;
	}

	@Override
	public User createUserWithNotification(String arg0, String arg1,
			String arg2, String arg3, int arg4) throws PermissionException,
			CreateException {

		return null;
	}

	@Override
	public User createUserWithNotification(String arg0, String arg1,
			String arg2, String arg3, Long arg4, int arg5)
			throws PermissionException, CreateException {

		return null;
	}

	@Override
	public PasswordResetToken generatePasswordResetToken(User arg0) {

		return null;
	}

	@Override
	public int getActiveUserCount() {

		return 0;
	}

	@Override
	public Collection<User> getAdministrators() {

		return null;
	}

	@Override
	public Collection<ApplicationUser> getAllApplicationUsers() {

		return null;
	}

	@Override
	public Set<User> getAllUsers() {

		return null;
	}

	@Override
	public SortedSet<User> getAllUsersInGroupNames(Collection<String> arg0) {

		return null;
	}

	@Override
	public Set<User> getAllUsersInGroupNamesUnsorted(Collection<String> arg0) {

		return null;
	}

	@Override
	public SortedSet<User> getAllUsersInGroups(Collection<Group> arg0) {

		return null;
	}

	@Override
	public Collection<ProjectComponent> getComponentsUserLeads(
			ApplicationUser arg0) {

		return null;
	}

	@Override
	public Collection<ProjectComponent> getComponentsUserLeads(User arg0) {

		return null;
	}

	@Override
	public String getDisplayableNameSafely(ApplicationUser arg0) {

		return null;
	}

	@Override
	public String getDisplayableNameSafely(User arg0) {

		return null;
	}

	@Override
	public SortedSet<String> getGroupNamesForUser(String arg0) {

		return null;
	}

	@Override
	public Group getGroupObject(String arg0) {

		return null;
	}

	@Override
	public SortedSet<Group> getGroupsForUser(String user) {
		List<Group> groups = user2Group.get(user);
		SortedSet<Group> ret = new TreeSet<>();
		if (groups != null) {
			ret.addAll(groups);
		}
		return ret;

	}

	@Override
	public Collection<User> getJiraAdministrators() {

		return null;
	}

	@Override
	public Collection<User> getJiraSystemAdministrators() {

		return null;
	}

	@Override
	public long getNumberOfAssignedIssuesIgnoreSecurity(ApplicationUser arg0,
			ApplicationUser arg1) throws SearchException {

		return 0;
	}

	@Override
	public long getNumberOfAssignedIssuesIgnoreSecurity(User arg0, User arg1)
			throws SearchException {

		return 0;
	}

	@Override
	public long getNumberOfReportedIssuesIgnoreSecurity(ApplicationUser arg0,
			ApplicationUser arg1) throws SearchException {

		return 0;
	}

	@Override
	public long getNumberOfReportedIssuesIgnoreSecurity(User arg0, User arg1)
			throws SearchException {

		return 0;
	}

	@Override
	public Collection<Project> getProjectsLeadBy(ApplicationUser arg0) {

		return null;
	}

	@Override
	public Collection<Project> getProjectsLeadBy(User arg0) {

		return null;
	}

	@Override
	public Collection<User> getSystemAdministrators() {

		return null;
	}

	@Override
	public int getTotalUserCount() {

		return 0;
	}

	@Override
	public ApplicationUser getUserByKey(String arg0) {

		return null;
	}

	@Override
	public ApplicationUser getUserByName(String arg0) {

		return null;
	}

	@Override
	public User getUserObject(String arg0) {

		return null;
	}

	@Override
	public Collection<User> getUsers() {

		return null;
	}

	@Override
	public SortedSet<User> getUsersInGroupNames(Collection<String> arg0) {

		return null;
	}

	@Override
	public SortedSet<User> getUsersInGroups(Collection<Group> arg0) {

		return null;
	}

	@Override
	public boolean hasExceededUserLimit() {

		return false;
	}

	@Override
	public boolean isNonSysAdminAttemptingToDeleteSysAdmin(
			ApplicationUser arg0, ApplicationUser arg1) {

		return false;
	}

	@Override
	public boolean isNonSysAdminAttemptingToDeleteSysAdmin(User arg0, User arg1) {

		return false;
	}

	@Override
	public void removeUser(ApplicationUser arg0, ApplicationUser arg1) {

	}

	@Override
	public void removeUser(User arg0, User arg1) {

	}

	@Override
	public void removeUserFromGroup(Group arg0, User arg1)
			throws PermissionException, RemoveException {

	}

	@Override
	public void removeUserFromGroups(Collection<Group> arg0, User arg1)
			throws PermissionException, RemoveException {

	}

	@Override
	public boolean userExists(String arg0) {

		return false;
	}

	@Override
	public PasswordResetTokenValidation validatePasswordResetToken(User arg0,
			String arg1) {

		return null;
	}


	@Override
	public Group addGroup(Group arg0) throws InvalidGroupException,
			OperationNotPermittedException, OperationFailedException {

		return null;
	}

	@Override
	public boolean addGroupToGroup(Group arg0, Group arg1)
			throws GroupNotFoundException, OperationNotPermittedException,
			InvalidMembershipException, OperationFailedException {

		return false;
	}

	@Override
	public User addUser(User user, String arg1) throws InvalidUserException,
			InvalidCredentialException, OperationNotPermittedException,
			OperationFailedException {

		userList.add(user);
		user2Group.put(user, new ArrayList<Group>());
		return user;
	}

	@Override
	public boolean addUserToGroup(User user, Group group)
			throws GroupNotFoundException, UserNotFoundException,
			OperationNotPermittedException, OperationFailedException {

		return user2Group.get(user).add(group);

	}

	@Override
	public User authenticate(String arg0, String arg1)
			throws FailedAuthenticationException, OperationFailedException {

		return null;
	}

	@Override
	public Group getGroup(final String name) {
		return new Group() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public int compareTo(Group group) {
				return name.compareTo(group.getName());
			}
		};
	}

	@Override
	public GroupWithAttributes getGroupWithAttributes(String arg0) {

		return null;
	}

	@Override
	public User getUser(String name) {

		for (User next : userList) {
            if (name.equals(next.getName())){
                return next;
            }
        }
		return null;
	}

	@Override
	public UserWithAttributes getUserWithAttributes(String arg0) {

		return null;
	}

	@Override
	public boolean isGroupDirectGroupMember(Group arg0, Group arg1)
			throws OperationFailedException {

		return false;
	}

	@Override
	public boolean isGroupMemberOfGroup(String arg0, String arg1) {

		return false;
	}

	@Override
	public boolean isGroupMemberOfGroup(Group arg0, Group arg1) {

		return false;
	}

	@Override
	public boolean isUserDirectGroupMember(User arg0, Group arg1)
			throws OperationFailedException {

		return false;
	}

	@Override
	public boolean isUserMemberOfGroup(String arg0, String arg1) {

		return false;
	}

	@Override
	public boolean isUserMemberOfGroup(User arg0, Group arg1) {

		return false;
	}

	@Override
	public void removeAllGroupAttributes(Group arg0)
			throws GroupNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public void removeAllUserAttributes(User arg0)
			throws UserNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public boolean removeGroup(Group arg0)
			throws OperationNotPermittedException, OperationFailedException {

		return false;
	}

	@Override
	public void removeGroupAttribute(Group arg0, String arg1)
			throws GroupNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public boolean removeGroupFromGroup(Group arg0, Group arg1)
			throws GroupNotFoundException, OperationNotPermittedException,
			OperationFailedException {

		return false;
	}

	@Override
	public boolean removeUser(User arg0) throws OperationNotPermittedException,
			OperationFailedException {

		return false;
	}

	@Override
	public void removeUserAttribute(User arg0, String arg1)
			throws UserNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public boolean removeUserFromGroup(User arg0, Group arg1)
			throws GroupNotFoundException, UserNotFoundException,
			OperationNotPermittedException, OperationFailedException {

		return false;
	}

	@Override
	public User renameUser(User arg0, String arg1)
			throws UserNotFoundException, InvalidUserException,
			OperationNotPermittedException, OperationFailedException {

		return null;
	}

	@Override
	public <T> Iterable<T> search(Query<T> arg0) {

		return null;
	}

	@Override
	public Iterable<User> searchUsersAllowingDuplicateNames(Query<User> arg0) {

		return null;
	}

	@Override
	public void setGroupAttribute(Group arg0, String arg1, String arg2)
			throws GroupNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public void setGroupAttribute(Group arg0, String arg1, Set<String> arg2)
			throws GroupNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public void setUserAttribute(User arg0, String arg1, String arg2)
			throws UserNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public void setUserAttribute(User arg0, String arg1, Set<String> arg2)
			throws UserNotFoundException, OperationNotPermittedException,
			OperationFailedException {

	}

	@Override
	public Group updateGroup(Group arg0) throws GroupNotFoundException,
			InvalidGroupException, OperationNotPermittedException,
			OperationFailedException {

		return null;
	}

	@Override
	public User updateUser(User arg0) throws UserNotFoundException,
			InvalidUserException, OperationNotPermittedException,
			OperationFailedException {

		return null;
	}

	@Override
	public void updateUserCredential(User arg0, String arg1)
			throws UserNotFoundException, InvalidCredentialException,
			OperationNotPermittedException, OperationFailedException {

	}

}
