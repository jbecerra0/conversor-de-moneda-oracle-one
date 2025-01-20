package org.example;

import org.example.ApiClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Conversor {
    public static void main(String[] args) {
        try {
            // Obtengo la api key desde el application.properties
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));
            String apiKey = properties.getProperty("api.key");


            ApiClient apiClient = new ApiClient(apiKey);
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("****************************************************");
                System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
                System.out.println("1) Dólar =>> Peso argentino");
                System.out.println("2) Peso argentino =>> Dólar");
                System.out.println("3) Dólar =>> Real brasileño");
                System.out.println("4) Real brasileño =>> Dólar");
                System.out.println("5) Dólar =>> Peso colombiano");
                System.out.println("6) Peso colombiano =>> Dólar");
                System.out.println("7) Salir");
                System.out.print("Elija una opción válida: ");
                int option = scanner.nextInt();

                if (option == 7) {
                    exit = true;
                    System.out.println("¡Gracias por usar el conversor!");
                    break;
                }

                String base = "", target = "";
                switch (option) {
                    case 1 :  base = "USD"; target = "ARS";
                        break;
                    case 2 :  base = "ARS"; target = "USD";
                        break;
                    case 3 :  base = "USD"; target = "BRL";
                        break;
                    case 4 :  base = "BRL"; target = "USD";
                        break;
                    case 5 :  base = "USD"; target = "COP";
                        break;
                    case 6 :  base = "COP"; target = "USD";
                        break;
                    default : {
                        System.out.println("Opción inválida. Intente nuevamente.");
                        continue;
                    }
                }

                System.out.print("Ingrese el valor que deseas convertir: ");
                double amount = scanner.nextDouble();

                try {
                    double rate = apiClient.getExchangeRate(base, target);
                    double result = amount * rate;
                    System.out.printf("El valor %.2f [%s] corresponde al valor final de =>>> %.2f [%s]%n",
                            amount, base, result, target);
                } catch (RuntimeException e) {
                    System.err.println("Error al obtener el tipo de cambio: " + e.getMessage());
                }

            }
        } catch (IOException e) {
            System.err.println("Error al cargar propiedades o inicializar el programa: " + e.getMessage());
        }
    }
}
