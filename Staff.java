import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public abstract class Staff implements Serializable {
    static private double factor = 5.0;

    // Properties
    private int idNum;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate startDate;
    private Department department;
    private int vacation = 12;

    // Set
    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    // Get
    public int getIdNum() {
        return idNum;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public Department getDepartment() {
        return department;
    }
    public int getVacation() {
        return vacation;
    }
    public double getFactor() {
        return factor;
    }

    // Methods
    public abstract void displayInfomation();
    public String getFullName() {
        return getLastName() + " " + getFirstName();
    }
    public int getAge() {
        if (this.getBirthDate() != null) {
            return Period.between(this.getBirthDate(), LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
    public void removeVacation(int i) {
        vacation -= i;
    }

}
