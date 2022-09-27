public class Manager extends Staff implements ICalculator {
    // Properties
    public static String[] roleList = {"Business Leader", "Project Leader", "Technical Leader"};
    public String role;
    // Set
    public void setRole(int index) {
        role = roleList[index];
    }
    // Get
    public String getRole() {
        return role;
    }
    public double leaderRes() {
        switch (this.getRole()) {
            case "Business Leader":
                return 8e6;
            case "Project Leader":
                return 5e6;
            case "Technical Leader":
                return 6e6;
            default:
                return 0;
        }
    }
    public void displayInfomation() {
        System.out.println(
            "\n\tID Number :\t" + this.getIdNum() +
            "\n\tName :\t\t" + this.getFullName() +
            "\n\tAge :\t\t" + this.getAge() +
            "\n\tStart date :\t" + this.getStartDate() +
            "\n\tDepartment :\t" + this.getDepartment().getName() +
            "\n\tVacation :\t" + this.getVacation() +
            "\n\tRank :\t\tManager" +
            "\n\tRole :\t\t" + this.getRole()
        );
    }
    public double calculateSalary() {
        return this.getFactor() * 3e6 + this.leaderRes();
    }
}
