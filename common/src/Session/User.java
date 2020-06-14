package Session;

import java.io.Serializable;

public class User implements Serializable {
	private String login;
	private String password;
	private String salt;
}
