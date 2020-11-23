import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ConfigurationClient {

    public static void main(String[] args) {

        // could request these from server too but exercise doesn't require it
        List<String> options = List.of("Griff", "Schaltung", "Lenkertyp", "Material");

        // uncomment to run tests
//        runTestsSafely(options);

        // uncomment to run program for the user
        runProgramSafely(options);

    }

    /**
     * Wrapper to catch unexpected HTTP Connection Errors
     *
     * @param options list of Options
     */
    private static void runProgramSafely(List<String> options) {
        try {
            runProgram(options);
        } catch (Exception e) {
            System.out.println("The server wasn't ready for your request.");
            System.out.println("The Configuration Process will start again.\n");
            runProgramSafely(options);
        }
    }

    private static void runProgram(List<String> options) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Starting a new Configuration Process\n\n");
            Map<String, String> chosenOptions = new HashMap<>();
            for (String option : options) {
                System.out.println("Input the type of " + option + " that you want");
                String selectedOption = printDetailedOptions(getOptions(option.toLowerCase()));
                chosenOptions.put(option, selectedOption);
                System.out.println("You chose " + selectedOption + "\n");
            }
            System.out.println("==============================================");
            System.out.println("Rechecking your configuration on the server");
            System.out.println("==============================================\n");

            try {
                int orderID = checkConfiguration(chosenOptions);
                System.out.println("Your Configuration was confirmed: ");
                System.out.println("OrderID: " + orderID);
                System.out.println("Configuration" + chosenOptions);
                System.out.println("\nIf you want to order another Bicycle press 'y' otherwise enter any key to finish:");
                System.out.print("> ");
                if (!in.nextLine().toLowerCase().equals("y")) break;
            } catch (IllegalArgumentException e) {
                System.out.println("There was an Error with your configuration:");
                System.out.println(e.getMessage());
                System.out.println("\nIf you want to start another configuration process press 'y' otherwise enter any key to finish:");
                System.out.print("> ");
                if (!in.nextLine().toLowerCase().equals("y")) break;
            } catch (Exception r) {
                System.out.println("There was an error with the Connection to the Server.");
            }
        }
    }

//    private static List<String> parseResult(String str){
//        List<String> result = new ArrayList<>();
//
//        // remove braces
//        str = str.substring(1, str.length() - 1);
//
//        //remove apostrophe
//        str = str.substring(1);
//
//        int i  = str.indexOf("\"");
//
//        result.add(str.substring(0,i));
//
//        str.repla
//
//
//
//        return result;
//    }


    private static int checkConfiguration(Map<String, String> map) throws IllegalArgumentException, IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(map);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/verify"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) return objectMapper.convertValue(response.body(), int.class);
        else if (response.statusCode() == 400) throw new IllegalArgumentException(response.body());
        else throw new IOException();
    }


    private static String printDetailedOptions(List<String> options) {
        for (String option : options) {
            System.out.println("Enter " + options.indexOf(option) + " for " + option);
        }
        System.out.print("> ");
        String result;
        try {
            result = options.get(Integer.parseInt(new Scanner(System.in).nextLine()));
        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Wrong Input please try again! ");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            return printDetailedOptions(options);
        }
        return result;
    }

    static List<String> getOptions(String option) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/" + option))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return parseStringToList(response.body());
    }

    static List<String> parseStringToList(String str) {
        str = str.substring(1, str.length() - 1);
        String[] array = str.split(",");
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].substring(1, array[i].length() - 1);
        }
        return List.of(array);
    }


    /**
     * Wrapper to catch unexpected HTTP Connection Errors
     *
     * @param options list of Options
     */
    private static void runTestsSafely(List<String> options) {
        try {
            testAllConfigs(options);
        } catch (Exception e) {
            System.out.println("The server wasn't ready for your request.");
            System.out.println(" The Configuration Process will start again.\n");
            runTestsSafely(options);
        }
    }

    /**
     * Iterates over all possible Options and validates them on the server
     */
    private static void testAllConfigs(List<String> baseOptions) throws IOException, InterruptedException {

        for (String griff : getOptions(baseOptions.get(0).toLowerCase())) {
            for (String schaltung : getOptions(baseOptions.get(1).toLowerCase())) {
                for (String lenker : getOptions(baseOptions.get(2).toLowerCase())) {
                    for (String material : getOptions(baseOptions.get(3).toLowerCase())) {
                        Map<String, String> chosenOptions = new HashMap<>();
                        chosenOptions.put("Griff", griff);
                        chosenOptions.put("Schaltung", schaltung);
                        chosenOptions.put("Lenkertyp", lenker);
                        chosenOptions.put("Material", material);

                        System.out.println("Your Configuration: " + chosenOptions.toString());

                        while (true) {
                            TimeUnit.MILLISECONDS.sleep(300);
                            try {
                                int orderID = checkConfiguration(chosenOptions);
                                System.out.println("Your Configuration" + chosenOptions + " was confirmed with orderID " + orderID + "\n");
                                System.out.println();
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("There was an Error with your configuration:");
                                System.out.println(e.getMessage());
                                System.out.println();
                                break;
                            } catch (Exception r) {
                                System.out.println("There was an error with the Connection to the Server.");

                            }
                            System.out.println("Trying again...");
                        }
                    }
                }
            }
        }
    }
}
