package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entitites.Employee;

public class Program {

	public static void main(String[] args){
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		
		//Ler os dados de funcionários de um determinado arquivo e armazená-las em uma lista
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Employee> employees = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(","); 
				employees.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
				line = br.readLine();	
			}
			
			// Mostrar o email somente dos funcionários que possuem um salário maior que o informado
			
			System.out.print("Enter wanted target salary: ");
			double targetSalary = sc.nextDouble();
			
			List<Employee> emp = employees.stream()
					.filter(e -> e.getSalary() > targetSalary)
					.sorted((s1,s2) -> s1.getEmail().compareTo(s2.getEmail()))
					.collect(Collectors.toList());
			
			System.out.println("\nEmail of people whose salary is more than " + targetSalary + ":");
			emp.forEach(System.out::println);
			
			// Somar o salário de todos os funcionários que começam com a letra 'M'
			
			double sumSalary = employees.stream()
					.filter(e -> e.getName().charAt(0) == 'M')
					.map(e -> e.getSalary())
					.reduce(0.0, (e1, e2) -> e1 + e2);
			
			System.out.println("\nSum of people's salary whose name starts with 'M': R$ " + String.format("%.2f", sumSalary));
		
		} catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}
