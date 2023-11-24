public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String firstNameU;
    private String lastNameU;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters for the User properties

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getFirstName() {return firstNameU;}
	public String getLastName() {return lastNameU;}
	public void setFirstName(String firstNameU) {this.firstNameU = firstNameU;}
	public void setLastName(String lastNameU) {this.lastNameU = lastNameU;}
    
    @Override
    // toString reps user as String
    public String toString() {return "User [id=" + id + ", "
    		+ "username=" + username + ", email=" + email + "]";}

    
	// END OF USER CLASS
}
