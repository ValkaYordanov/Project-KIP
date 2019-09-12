package gui;
public enum Users {
	MANAGER(1),
	EMPLOYEE(2);
	
	private final int permission;

    private Users(int levelCode) {
        this.permission = levelCode;
    }

	public int getId() {
		return this.permission;
	}
}
