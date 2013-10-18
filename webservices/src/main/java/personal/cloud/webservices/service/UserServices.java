package personal.cloud.webservices.service;

import java.util.Date;

import xdi2.core.xri3.XDI3Segment;
import clouds.client.basic.PersonalCloud;

public class UserServices {

	public static long incrementFactor;

	public static String updateProfileInfo(String userInfo) {
		return null;

	}

	public static String refreshToken(String sessionTokenId) {

		// get timestamp from the from the graph
		long xyz = new Date().getTime() + incrementFactor;
		// save timestamp in the graph
		System.out.println(xyz);
		return sessionTokenId;
		// call to graph to update token
	}

	public static boolean cleanSessionId(String sessionTokenId, String userName) {

		// get Timestamp corresponding to sessionTokenId
		long expireTimeFromGraph = new Date().getTime();
		if (new Date().getTime() > expireTimeFromGraph) {
			// delete session from the graph.
			// if(deteteSessionId(sessionTokenId))
			return false;
		}

		return true;
	}

	public static PersonalCloud validateUser(String username, String password) {
		PersonalCloud cloud = null;
		try {
			cloud = PersonalCloud.open(XDI3Segment.create(username), password, PersonalCloud.XRI_S_DEFAULT_LINKCONTRACT, "", "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloud;
	}

	public static String createSessionID(PersonalCloud cloud) {
		String sessionTokenId = cloud.getSessionId();
		return sessionTokenId;
	}

}
