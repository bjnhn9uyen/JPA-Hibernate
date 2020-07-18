package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.Student;

public class StudentManagement {

	private static StudentDAO studentDAO = new StudentDAO();
	private static Scanner input;

	public static void main(String[] args) {
		byte choice = 0;
		do {
			System.out.println("----------MENU----------");
			System.out.println("1. Create");
			System.out.println("2. Read (all)");
			System.out.println("3. Read (by id)");
			System.out.println("4. Update");
			System.out.println("5. Delete");
			System.out.println("6. Exit");
			System.out.print("your choice: ");
			try {
				input = new Scanner(System.in);
				choice = input.nextByte();
			} catch (InputMismatchException error) {
				PressAnyKeyToContinue("only from 1 to 6. Press enter to continue.");
			}
			switch (choice) {
			case 1:
				case1();
				break;
			case 2:
				case2();
				break;
			case 3:
				case3();
				break;
			case 4:
				case4();
				break;
			case 5:
				case5();
				break;
			case 6:
				System.exit(0);
				break;
			default:
				PressAnyKeyToContinue("only from 1 to 6. Press enter to continue !");
			}
		} while (true);
	}

	private static void PressAnyKeyToContinue(String str) {
		input = new Scanner(System.in);
		System.out.println(str);
		input.nextLine();
	}

	private static void case1() {
		System.out.println("enter student's information:");
		input = new Scanner(System.in);
		System.out.print("first name: ");
		String firstName = input.nextLine();
		System.out.print("last name: ");
		String lastName = input.nextLine();
		System.out.print("email: ");
		String email = input.nextLine();
		Student stud = new Student(firstName, lastName, email);
		studentDAO.createStudent(stud);
		PressAnyKeyToContinue("create new student successful. Press enter to continue !");
	}

	private static void case2() {
		studentDAO.readAllStudent();
		PressAnyKeyToContinue("Press enter to continue !");
	}

	private static void case3() {
		input = new Scanner(System.in);
		System.out.print("enter student's ID to see student's information: ");
		Long id = input.nextLong();
		studentDAO.readStudentById(id);
		PressAnyKeyToContinue("Press enter to continue !");
	}

	private static void case4() {
		input = new Scanner(System.in);
		System.out.print("enter student's ID to update: ");
		Long id = input.nextLong();
		input = new Scanner(System.in);
		System.out.print("first name: ");
		String firstName = input.nextLine();
		System.out.print("last name: ");
		String lastName = input.nextLine();
		System.out.print("email: ");
		String email = input.nextLine();
		Student stud = new Student(id, firstName, lastName, email);
		studentDAO.updateStudent(stud);
		PressAnyKeyToContinue("update student's information successful. Press any key to continue !");
	}

	private static void case5() {
		input = new Scanner(System.in);
		System.out.print("enter student's ID to delete student: ");
		Long id = input.nextLong();
		Student stud = new Student(id);
		studentDAO.deleteStudent(stud);
		PressAnyKeyToContinue("delete student successful. Press enter to continue !");
	}

}
