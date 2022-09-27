import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {

    private static String[] deptList = {"Human Resources", "Sales", "Marketing", "Customer Service", "Finance", "Production"};
    private int idNum;
    private String name;
    private ArrayList<Staff> staffDeptList = new ArrayList<Staff>();

    public static String[] getDeptList() {
        return deptList;
    }
    public Department(int i) {
        name = deptList[i];
        idNum = i + 1;
    }

    public String getName() {
        return name;
    }
    public int getIdNum() {
        return idNum;
    }

    public ArrayList<Staff> getStaffDeptList() {
        return staffDeptList;
    }
    public void addStaff(Staff staff) {
        staffDeptList.add(staff);
    }
    public void removeStaff(Staff staff) {
        staffDeptList.remove(staff);
    }
    public void clearStaff() {
        staffDeptList.clear();
    }

    @Override
    public String toString() {
        return (
            "\n\tDepartment ID :\t\t" + getIdNum() +
            "\n\tDepartment name :\t" + getName() +
            "\n\tNumber of staff :\t" + getStaffDeptList().size()
        );
    }
}
