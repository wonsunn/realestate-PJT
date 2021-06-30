package com.ssafy.happyhouse.model;

public class MemberDto {

	private int useridx;
	private String userid;
	private String username;
	private String userpwd;
	private String useremail;
	private int userauth;
	
	public int getUserauth() {
		return userauth;
	}

	public void setUserauth(int userauth) {
		this.userauth = userauth;
	}

	public MemberDto(int useridx, String userid, String username, String userpwd, String useremail) {
		super();
		this.useridx = useridx;
		this.userid = userid;
		this.username = username;
		this.userpwd = userpwd;
		this.useremail = useremail;
	}
	
	public int getUseridx() {
		return useridx;
	}
	public void setUseridx(int useridx) {
		this.useridx = useridx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	
	
	

	
	
	
}
