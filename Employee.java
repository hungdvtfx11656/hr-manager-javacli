public class Employee extends Staff implements ICalculator {
    // Properties
    private int overTime = 0;
    // Get
    public int getOverTime() {
        return overTime;
    }
    public void displayInfomation() {
        System.out.println(
            "\n\tID Number :\t" + this.getIdNum() +
            "\n\tName :\t\t" + this.getFullName() +
            "\n\tAge :\t\t" + this.getAge() +
            "\n\tStart date :\t" + this.getStartDate() +
            "\n\tDepartment :\t" + this.getDepartment().getName() +
            "\n\tVacation :\t" + this.getVacation() +
            "\n\tRank :\t\tEmployee"
        );
    }
    public double calculateSalary() {
        return this.getFactor() * 3e6 + this.getOverTime() * 2e5;
    }
    // Set
    public void addOverTime(int hours) {
        this.overTime += hours;
    }
}