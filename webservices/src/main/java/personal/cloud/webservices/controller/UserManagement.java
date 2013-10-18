package personal.cloud.webservices.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import personal.cloud.webservices.model.UserProfileInfo;
import personal.cloud.webservices.service.UserServices;
import clouds.client.basic.PersonalCloud;
import xdi2.core.xri3.XDI3Segment;

import com.google.gson.Gson;

@Controller
@RequestMapping("/user")
public class UserManagement {

	private boolean isValidUser = false;

	@RequestMapping(value = "/sesssionid", method = RequestMethod.POST)
	@ResponseBody
	public String authenticateUser(@RequestHeader String username, @RequestHeader String password, HttpServletResponse response) {
		String sessionID = null;
		// PersonalCloud cloud = (validateUser(username, password) != null)?
		// validateUser(username, password) : null;
		PersonalCloud cloud = UserServices.validateUser(username, password);

		if (cloud != null) {
			this.isValidUser = true;
			sessionID = UserServices.createSessionID(cloud);
			sessionID = "uuid:shreymehrotra";

		}
		Cookie cookie = new Cookie("sessionId", sessionID);
		cookie.setPath("/respect.connect.ui/connect/");
		response.addCookie(cookie);
		return sessionID;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public String updateProfile(@RequestBody String userInfo, @RequestHeader String sessionId) {
		String sessionTokenFromGraph = null;

		Gson gson = new Gson();
		gson.fromJson(userInfo, UserProfileInfo.class);

		sessionTokenFromGraph = UserServices.updateProfileInfo(userInfo);

		return "uuid:shreymehrotra";
	}

	@RequestMapping(value = "/deleteSessionId/{sessionTokenId}", method = RequestMethod.GET)
	@ResponseBody
	public String deteteSessionId(@PathVariable String sessionTokenId) {
		// delete session from the graph.
		return "deleted";
	}

	// @POST @Path("{cloudnumber}/connect/request")
	@RequestMapping(value = "/connect/request/{cloudnumber}", method = RequestMethod.GET)
	@ResponseBody
	// @Produces(MediaType.TEXT_HTML)
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getLoginForm(@PathVariable String cloudnumber, @RequestParam String xdimessage, @RequestParam String discoverykey, @RequestParam String returnurl, @RequestParam String relayState
	/*
	 * @PathParam("cloudnumber") String respondingPartyCloudNumber ,
	 * 
	 * @FormParam("xdimessage") String connectRequest,
	 * 
	 * @FormParam("discoverykey") String respondingPartyCloudName,
	 * 
	 * @FormParam("returnurl") String successurl,
	 * 
	 * @FormParam("returnurl") String failureurl,
	 * 
	 * @FormParam("relayState") String relayState
	 */

	) {
		String respondingPartyCloudNumber = cloudnumber;
		String connectRequest = xdimessage;
		String respondingPartyCloudName = discoverykey;
		String successurl = returnurl;
		String failureurl = returnurl;

		// PersonalCloud pc =
		// PersonalCloud.open(XDI3Segment.create(respondingPartyCloudNumber),XDI3Segment.create("$anon"),XDI3Segment.create("$public$do"),"");
		PersonalCloud pc = PersonalCloud.open(XDI3Segment.create(respondingPartyCloudNumber), XDI3Segment.create(respondingPartyCloudNumber), XDI3Segment.create("$public$do"), "");
		return pc.showAuthenticationForm(connectRequest, respondingPartyCloudName, respondingPartyCloudNumber, successurl, failureurl, relayState);
	}

}
