jira-pvp-sso
============
* Add the PVPAuthenticator to $JIRA_HOME/atlassian-jira/WEB-INF/classes/seraph-config.xml
```
  <authenticator class="cc.schieder.jira.pvp.PVPAuthenticator">
    	 <init-param>
    	    <param-name>trusted.hosts</param-name>
    	    <param-value>server.mydomain,server2.mydomain</param-value>
  		  </init-param>
  </authenticator>
```
* copy jira-pvp-sso-x.x.x.jar to $JIRA_HOME/atlassian-jira/WEB-INF/lib

* logging
add following lines to $JIRA_HOME/atlassian-jira/WEB-INF/classes/log4j.properties
```
#####################################################
# PVP 
#####################################################

log4j.logger.cc.schieder.jira.pvp.PVPHelper = INFO, console, filelog
```
