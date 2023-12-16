// CurrencyConverter

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the base currency code (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter the target currency code (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        ExchangeRateService exchangeRateService = new ExchangeRateService();

        // Fetch the exchange rate
        double exchangeRate = exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);

        if (exchangeRate != -1) {
            System.out.print("\nEnter the amount to convert: ");
            double amountToConvert = scanner.nextDouble();

            // Calculate the converted amount
            double convertedAmount = amountToConvert * exchangeRate;

            // Display the result
            System.out.printf("%.2f %s is equal to %.2f %s%n",
                    amountToConvert, baseCurrency, convertedAmount, targetCurrency);
        } else {
            System.out.println("Failed to fetch exchange rate. Please check your input and try again.");
        }

        scanner.close();
    }
}

class ExchangeRateService {

    // Method to fetch the exchange rate from the API
    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            // Api Urls
            String apiKey = "cur_live_wI7lsu11FP6xVAuinWm6H6EWJWgjuYwc5ZFG0SV0";
            String apiUrl = "https://api.currencyapi.com/v3/latest?apikey=" + apiKey;

            // Create a URI object instead of a URL object
            URI uri = new URI(apiUrl);

            // Open a connection using URI
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            // Read the API response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Close the reader and disconnect the connection
            reader.close();
            connection.disconnect();

            // Parse the JSON response and extract the exchange rate
            String jsonResponse = response.toString();
            return parseExchangeRateFromJson(jsonResponse, targetCurrency);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Method to parse the exchange rate from the JSON response
    private double parseExchangeRateFromJson(String jsonResponse, String targetCurrency) {
        try {
            // Find the index of the target currency in the JSON response
            int targetIndex = jsonResponse.indexOf("\"" + targetCurrency + "\":");

            if (targetIndex != -1) {
                // Extract the substring starting from the target currency index
                String subString = jsonResponse.substring(targetIndex);

                // Find the index of the value field in the substring
                int valueIndex = subString.indexOf("\"value\":");

                if (valueIndex != -1) {
                    // Extract the substring starting from the value index
                    String valueSubstring = subString.substring(valueIndex);

                    // Find the end index of the value
                    int endIndex = valueSubstring.indexOf("}");

                    // Extract the value substring and convert it to double
                    String value = valueSubstring.substring(8, endIndex).trim();
                    return Double.parseDouble(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
