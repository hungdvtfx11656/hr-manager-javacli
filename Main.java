/**
 * HumanResources
 * Assignment 3 - PRO192x
 * Hungdvt - FX11656
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner console = new Scanner(System.in);
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static ArrayList<Department> deptList = new ArrayList<Department>();
    static ArrayList<Staff> staffList = new ArrayList<Staff>();
    public static void main(String[] args) {
        constructDeptList();
        constructStaffList();
        mainMenu();
    }

    // MAIN FUNCTIONS
    // Used each time as command from main menu

    /**
     * Display staff information
     */
    private static void displayStaff() {
        System.out.print("\nSTAFFS INFORMATION -------------------------------\n");
        System.out.print("Select display (1-All staff, 2-By department)? ");
        int rank = inputInt(console, 1, 2);
        if (rank == 1) {
            for (int i = 0; i < staffList.size(); i++) {
                staffBlock(i);
            }
        }
        if (rank == 2) {
            Department department = selectDepartment();
            for (int i = 0; i < staffList.size(); i++) {
                if (staffList.get(i).getDepartment().equals(department)) {
                    staffBlock(i);
                }
            }
        }
        endFunction();
    }

    /**
     * Display all departments information
     */
    private static void displayDept() {
        System.out.print("\nDEPARTMENTS INFORMATION --------------------------\n");
        for (int i = 0; i < deptList.size(); i++) {
            System.out.println(deptList.get(i));
        }
        endFunction();
    }

    /**
     * Add staff to staff list
     */
    private static void addStaff() {
        System.out.print("\nADD NEW STAFF ------------------------------------\n");
        System.out.print("Set rank for new staff (1-Employee, 2-Manager)? ");
        int rank = inputInt(console, 1, 2);
        if (rank == 1) {
            Employee newStaff = new Employee();
            System.out.print("Set information for new Employee: \n");
            setStaffInfo(newStaff);
            staffList.add(newStaff);
            newStaff.getDepartment().addStaff(newStaff);
        }
        if (rank == 2) {
            Manager newStaff = new Manager();
            System.out.println("Set information for new Manager: ");
            System.out.print("Select role :\n");
            for (int i = 0; i < Manager.roleList.length; i++) {
                System.out.println("\t" + (i + 1) + " - " + Manager.roleList[i]);
            }
            System.out.print("Enter your choice: ");
            int select = inputInt(console, 1, Manager.roleList.length);
            newStaff.setRole(select - 1);
            setStaffInfo(newStaff);
            staffList.add(newStaff);
            newStaff.getDepartment().addStaff(newStaff);
        }
        endFunction();
    }

    /**
     * Search for staff by Name or ID Number
     */
    public static void search() {
        System.out.print("\nSEARCH STAFF -------------------------------------\n");
        System.out.print("Search staff by (1-Name, 2-ID Number)? ");
        int rank = inputInt(console, 1, 2);
        if (rank == 1) {
            console.nextLine();
            System.out.print("Enter keywords: ");
            String keywords = (console.nextLine()).toLowerCase();
            int results = 0;
            String resultList = "\n\t" + "ID" + "\t" + "NAME" + "\t\t\t" + "DEPARTMENT";
            for (int i = 0; i < staffList.size(); i++) {
                if (staffList.get(i).getFullName().toLowerCase().contains(keywords)) {
                    Staff staff = staffList.get(i);
                    resultList += "\n\t" + (staff.getIdNum()) + "\t" + staff.getFullName() + "\t\t" + staff.getDepartment().getName();
                    results ++;
                }
            }
            if (results == 0) System.out.println("No results found for keywords \"" + keywords + "\"");
            else {
                System.out.println("Total " + results + " results found for \"" + keywords + "\": ");
                System.out.println(resultList);
            }
        }
        if (rank == 2) {
            console.nextLine();
            System.out.print("Enter ID Number: ");
            int idNum = staffIdSearch(console);
            if (idNum > 0) {
                System.out.print("Search result for staff with ID Number " + idNum + ": \n");
                staffBlock(idNum - 1);
            }
        }
        endFunction();
    }

    /**
     * Add and remove vacations, overtime hours
     */
    public static void workingOnStaff() {
        System.out.print("\nWORKING ON STAFF ---------------------------------\n");
        System.out.print("Enter ID Number: ");
        int idNum = inputInt(console, 1, staffList.size());
        Staff staff = staffList.get(idNum - 1);
        System.out.println("\t" + idNum + " - " + staff.getFullName());
        switch (staff.getClass().getSimpleName()) {
            case "Employee":
                System.out.print(
                    "Select your preference:\n" +
                    "\t1 - Remove vacation\n" +
                    "\t2 - Add over time\n" +
                    "\t3 - Delete from list\n" +
                    "Enter your choice: "
                );
                int eValue = inputInt(console, 1, 3);
                switch (eValue) {
                    case 1:
                        System.out.print("Enter days off: ");
                        int dayyoff = inputInt(console, 1, 7);
                        ((Employee) staff).removeVacation(dayyoff);
                        break;
                    case 2:
                        System.out.print("Enter over time hours: ");
                        int overtime = inputInt(console, 1, 30);
                        ((Employee) staff).addOverTime(overtime);
                        break;
                    case 3:
                        staffList.remove(idNum - 1);
                        deptUpdate();
                        break;
                }
            break;
            case "Manager":
                System.out.print(
                    "Select your preference:\n" +
                    "\t1 - Remove vacation\n" +
                    "\t2 - Delete from list\n" +
                    "Enter your choice: "
                );
                int mValue = inputInt(console, 1, 2);
                switch (mValue) {
                    case 1:
                        System.out.print("Enter days off: ");
                        int dayyoff = inputInt(console, 1, 7);
                        ((Manager) staff).removeVacation(dayyoff);
                        break;
                    case 2:
                        staffList.remove(idNum - 1);
                        deptUpdate();
                        break;
                }
            break;
        }
        endFunction();
    }

    /**
     * Display sorted salary
     */
    public static void displaySalary() {
        System.out.print("\nDISPLAY SALARY TABLE -----------------------------\n");
        System.out.print(
            "Select your preference:\n" +
            "\t1 - Sorted by ID\n" +
            "\t2 - Sorted by ascending salary\n" +
            "\t3 - Sorted by descending salary\n" +
            "Enter your choice: "
        );
        ArrayList<Staff> clone = new ArrayList<Staff>();
        for (int i = 0; i < staffList.size(); i++) {
            clone.add(staffList.get(i));
        }
        int rank = inputInt(console, 1, 3);
        switch (rank) {
            case 1:
                break;
            case 2:
                Collections.sort(clone, (Staff o1, Staff o2) -> Double.compare(getSalary(o1), getSalary(o2)));
                break;
            case 3:
                Collections.sort(clone, (Staff o1, Staff o2) -> Double.compare(getSalary(o2), getSalary(o1)));
                break;
            default:
                break;
        }
        System.out.println("\n\t" + "ID" + "\t" + "NAME" + "\t\t\t" + "SALARY");
        for (int i = 0; i < clone.size(); i++) {
            Staff staff = clone.get(i);
            System.out.println("\t" + (staff.getIdNum()) + "\t" + staff.getFullName() + "\t\t" + currency(getSalary(staff)));
        }
        endFunction();
    }

    /**
     * Saving and loading staff list file
     */
    public static void fileHandling() {
        System.out.print("\nFILE HANDLING ------------------------------------\n");
        System.out.print("Staff list (1-Save, 2-Load)? ");
        int rank = inputInt(console, 1, 2);
        if (rank == 1) {
            saveStaffFile();
        }
        if (rank == 2) {
            readStaffFile();
        }
        endFunction();
    }

    // SUB FUNCTION
    // Used many times in main functions

    /**
     * Convert double number to currency for displaying purpose
     * @param value double number
     * @return converted string
     */
    public static String currency(double value) {
        return NumberFormat.getCurrencyInstance().format(value);
    }

    /**
     * Display staff information as block
     * @param i index of staff object in staff array list
     */
    public static void staffBlock(int i) {
        // System.out.println("\n\tStaff ID Number " + (i + 1));
        staffList.get(i).displayInfomation();
        System.out.println("\tSalary :\t" + currency(getSalary(staffList.get(i))));
    }

    /**
     * Get salary from object staff
     * @param staff staff object
     * @return salary in double
     */
    public static double getSalary(Staff staff) {
        return ((ICalculator) staff).calculateSalary();
    }

    /**
     * Select department from department array list
     * @return Department object from department array list
     */
    public static Department selectDepartment() {
        System.out.print("Select department: \n");
        for (int i = 0; i < deptList.size(); i++) {
            System.out.println("\t" + (i + 1) + " - " + deptList.get(i).getName());
        }
        System.out.print("Enter your choice: ");
        int value = inputInt(console, 1, deptList.size());
        return deptList.get(value - 1);
    }

    /**
     * Set information for new staff in adding process
     */
    public static void setStaffInfo(Staff newStaff) {
        Staff last = staffList.get(staffList.size()-1);
        System.out.print("\tID Number : " + (last.getIdNum() + 1) + "\n");
        newStaff.setIdNum(last.getIdNum() + 1);
        console.nextLine();
        System.out.print("\tFirst name : ");
        newStaff.setFirstName(console.nextLine());
        System.out.print("\tLast name : ");
        newStaff.setLastName(console.nextLine());
        System.out.print("\tBirthdate (yyyy-MM-dd) : ");
        newStaff.setBirthDate(dateInput(console));
        System.out.print("\tStart date (yyyy-MM-dd) : ");
        newStaff.setStartDate(dateInput(console));
        Department department = selectDepartment();
        newStaff.setDepartment(department);
    }

    // VALIDATING AND INPUT FUNCTION
    // Used many times for input and vadilation processes

    /**
     * Validate and parse string input to LocalDate object
     * @param console user input
     * @return LocalDate object
     */
    public static LocalDate dateInput(Scanner console) {
        LocalDate date = null;
        try {
            String value = console.next();
            date = LocalDate.parse(value, dateFormat);
        } catch (DateTimeParseException ex) {
            System.out.print("\tError: wrong date format. Try again: ");
            dateInput(console);
            // ex.printStackTrace();
        }
        return date;
    }

    /**
     * Validate integer input in specific range
     * @param console user input
     * @param min minimum input range
     * @param max maximum input range
     * @return integer in range
     */
    public static int inputInt(Scanner console, int min, int max) {
        int value;
        try {
            value = console.nextInt();
            if (value < min || value > max) {
                System.out.print("Error: value must between " + min + " and " + max + ". Try again: ");
                return inputInt(console, min, max);
            } else {
                return value;
            }
        } catch (InputMismatchException ex) {
            console.next();
            System.out.print("Error: wrong input. Try again: ");
        }
        return inputInt(console, min, max);
    }

    /**
     * Search for staff by Id
     * @param console user input
     * @return integer in range
     */
    public static int staffIdSearch(Scanner console) {
        int value;
        try {
            value = console.nextInt();
            if (value != 0) {
                if (value > 0 && value <= staffList.size()) {
                    return value;
                } else if (value > staffList.size()) {
                    System.out.println("No results found for staff with ID number " + value);
                    return 0;
                }
            }
        } catch (InputMismatchException ex) {
            console.next();
        }
        System.out.print("Error: wrong input. Try again: ");
        return staffIdSearch(console);
    }

    // HANDLING FUNCTION
    // For initializing, navigating and file handling

    /**
     * Application's main menu
     */
    public static void mainMenu() {
        System.out.print(
            "\nHUMAN RESOURSES ----------------------------------\n" +
            "Choose a function listed below:\n" +
            "\t1 - Display staff information as block\n" +
            "\t2 - Display departments information\n" +
            "\t3 - Add new staff\n" +
            "\t4 - Search for staff\n" +
            "\t5 - Working on staff\n" +
            "\t6 - Display salary as table\n" +
            "\t7 - File handling\n" +
            "\t0 - Exit\n" +
            "Enter your choice: "
        );
        switch (inputInt(console, 0, 7)) {
            case 1:
                displayStaff();
                break;
            case 2:
                displayDept();
                break;
            case 3:
                addStaff();
                break;
            case 4:
                search();
                break;
            case 5:
                workingOnStaff();
                break;
            case 6:
                displaySalary();
                break;
            case 7:
                fileHandling();
                break;
            case 0:
                System.exit(0);
                break;
        }
        mainMenu();
    }

    /**
     * Construct and add departments to array list on startup
     */
    public static void constructDeptList() {
        String[] dl = Department.getDeptList();
        for (int i = 0; i < dl.length; i++) {
            Department newDept = new Department(i);
            deptList.add(newDept);
        }
    }

    /**
     * Construct and add staff to array list on startup
     */
    public static void constructStaffList() {

        Manager staff1 = new Manager();
        staff1.setIdNum(1);
        staff1.setFirstName("Trang");
        staff1.setLastName("Phạm Linh");
        staff1.setBirthDate(LocalDate.parse("1995-12-06", dateFormat));
        staff1.setStartDate(LocalDate.parse("2018-02-03", dateFormat));
        staff1.setDepartment(deptList.get(0));
        staff1.setRole(0);
        // staff1.getDepartment().addStaff(staff1);
        staffList.add(staff1);

        Employee staff2 = new Employee();
        staff2.setIdNum(2);
        staff2.setFirstName("Hùng");
        staff2.setLastName("Nguyễn Đức");
        staff2.setBirthDate(LocalDate.parse("1998-11-01", dateFormat));
        staff2.setStartDate(LocalDate.parse("2019-01-30", dateFormat));
        staff2.setDepartment(deptList.get(5));
        // staff2.getDepartment().addStaff(staff2);
        staffList.add(staff2);

        Employee staff3 = new Employee();
        staff3.setIdNum(3);
        staff3.setFirstName("Phương");
        staff3.setLastName("Lê Minh");
        staff3.setBirthDate(LocalDate.parse("1992-05-13", dateFormat));
        staff3.setStartDate(LocalDate.parse("2018-12-12", dateFormat));
        staff3.setDepartment(deptList.get(1));
        // staff3.getDepartment().addStaff(staff3);
        staffList.add(staff3);

        Manager staff4 = new Manager();
        staff4.setIdNum(4);
        staff4.setFirstName("Hoa");
        staff4.setLastName("Phùng Thúy");
        staff4.setBirthDate(LocalDate.parse("1991-10-09", dateFormat));
        staff4.setStartDate(LocalDate.parse("2019-11-22", dateFormat));
        staff4.setDepartment(deptList.get(1));
        staff4.setRole(2);
        // staff4.getDepartment().addStaff(staff4);
        staffList.add(staff4);

        Employee staff5 = new Employee();
        staff5.setIdNum(5);
        staff5.setFirstName("Minh");
        staff5.setLastName("Giang Văn");
        staff5.setBirthDate(LocalDate.parse("1994-08-20", dateFormat));
        staff5.setStartDate(LocalDate.parse("2020-04-21", dateFormat));
        staff5.setDepartment(deptList.get(1));
        // staff5.getDepartment().addStaff(staff5);
        staffList.add(staff5);

        Employee staff6 = new Employee();
        staff6.setIdNum(6);
        staff6.setFirstName("Anh");
        staff6.setLastName("Hà Hoàng");
        staff6.setBirthDate(LocalDate.parse("1997-08-09", dateFormat));
        staff6.setStartDate(LocalDate.parse("2019-08-18", dateFormat));
        staff6.setDepartment(deptList.get(2));
        // staff6.getDepartment().addStaff(staff6);
        staffList.add(staff6);

        Manager staff7 = new Manager();
        staff7.setIdNum(7);
        staff7.setFirstName("Đạt");
        staff7.setLastName("Mai Tiến");
        staff7.setBirthDate(LocalDate.parse("1998-07-29", dateFormat));
        staff7.setStartDate(LocalDate.parse("2020-11-18", dateFormat));
        staff7.setDepartment(deptList.get(2));
        staff7.setRole(0);
        // staff7.getDepartment().addStaff(staff7);
        staffList.add(staff7);

        Employee staff8 = new Employee();
        staff8.setIdNum(8);
        staff8.setFirstName("Ngân");
        staff8.setLastName("Vũ Bảo");
        staff8.setBirthDate(LocalDate.parse("1993-07-02", dateFormat));
        staff8.setStartDate(LocalDate.parse("2020-06-01", dateFormat));
        staff8.setDepartment(deptList.get(3));
        // staff8.getDepartment().addStaff(staff8);
        staffList.add(staff8);

        Employee staff9 = new Employee();
        staff9.setIdNum(9);
        staff9.setFirstName("Hằng");
        staff9.setLastName("Lê Thúy");
        staff9.setBirthDate(LocalDate.parse("1990-04-30", dateFormat));
        staff9.setStartDate(LocalDate.parse("2019-05-29", dateFormat));
        staff9.setDepartment(deptList.get(4));
        // staff9.getDepartment().addStaff(staff9);
        staffList.add(staff9);

        deptUpdate();
    }

    /**
     * Load staff list from file on startup
     * Load staff list from template construction if loading from file is false
     */
    public static void readStaffFile() {
        try {
            FileInputStream fis = new FileInputStream("stafflist.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            staffList = (ArrayList<Staff>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Loading stafflist.ser successful!");
            deptUpdate();
        } catch (IOException | ClassNotFoundException ex) {
            // ex.printStackTrace();
            System.out.println("Reading stafflist.ser is not success!");
            System.out.println("Load stafflist from template.");
            staffList.clear();
            constructStaffList();
        }
    }

    /**
     * Save staff list to file after modifying
     */
    public static void saveStaffFile() {
        try {
            FileOutputStream fos = new FileOutputStream("stafflist.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(staffList);
            oos.close();
            fos.close();
            System.out.println("Saving stafflist.ser successful!");
        } catch (Exception ex) {
            // ex.printStackTrace();
            System.out.println("Saving stafflist.ser is not success!");
        }
    }

    /**
     * Prompt after a main function is closed successful
     * Choose to return to main menu or exit application
     */
    public static void endFunction() {
        System.out.print("\nAll done! Select (1-Menu, 2-Exit)? ");
        if (inputInt(console, 1, 2) == 1) {
            return;
        }
        System.out.println("Good bye!");
        System.exit(0);
    }

    /**
     * Update each deparment staff list
     */
    public static void deptUpdate() {
        for (int i = 0; i < deptList.size(); i++) {
            deptList.get(i).clearStaff();
        }
        for (int i = 0; i < staffList.size(); i++) {
            Staff staff = staffList.get(i);
            Department dept1 = staff.getDepartment();
            String deptName1 = dept1.getName();
            for (int j = 0; j < deptList.size(); j++) {
                Department dept2 = deptList.get(j);
                String deptName2 = dept2.getName();
                if (deptName1.equals(deptName2)) {
                    staff.setDepartment(dept2);
                    dept2.addStaff(staff);
                }
            }
        }
        // for (int i = 0; i < staffList.size(); i++) {
        //     staffList.get(i).getDepartment().addStaff(staffList.get(i));
        // }
    }

}
