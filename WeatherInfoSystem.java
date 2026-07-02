import java.util.ArrayList;
import java.util.Scanner;

public class WeatherInfoSystem {

    static class WeatherRecord {
        String city;
        double temperature;
        String condition;
        int humidity;
        double windSpeed;

        public WeatherRecord(String city, double temperature, String condition,
                             int humidity, double windSpeed) {
            this.city        = city;
            this.temperature = temperature;
            this.condition   = condition;
            this.humidity    = humidity;
            this.windSpeed   = windSpeed;
        }

        public String getFeelsLike() {
            if (temperature >= 35) return "🔥 Extremely Hot";
            if (temperature >= 30) return "☀️  Hot";
            if (temperature >= 25) return "🌤️  Warm";
            if (temperature >= 20) return "🌥️  Comfortable";
            if (temperature >= 15) return "🌬️  Cool";
            if (temperature >= 10) return "🧥  Cold";
            return "❄️  Very Cold";
        }

        public String getConditionEmoji() {
            switch (condition.toLowerCase()) {
                case "sunny":    return "☀️ ";
                case "cloudy":   return "☁️ ";
                case "rainy":    return "🌧️ ";
                case "stormy":   return "⛈️ ";
                case "snowy":    return "❄️ ";
                case "windy":    return "💨";
                case "foggy":    return "🌫️ ";
                case "partly cloudy": return "⛅";
                default:         return "🌡️ ";
            }
        }

        public String getHumidityLevel() {
            if (humidity >= 80) return "Very Humid";
            if (humidity >= 60) return "Humid";
            if (humidity >= 40) return "Moderate";
            return "Dry";
        }
    }

    static ArrayList<WeatherRecord> records = new ArrayList<>();

    public static void addWeatherRecord(Scanner scanner) {
        System.out.println("\n  ── Add Weather Record ──");
        System.out.print("  City Name: ");
        String city = scanner.nextLine().trim();
        if (city.isEmpty()) { System.out.println("  City name cannot be empty."); return; }

        System.out.print("  Temperature (°C): ");
        double temp;
        try { temp = Double.parseDouble(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("  Invalid temperature."); return; }

        System.out.println("  Condition (Sunny/Cloudy/Rainy/Stormy/Snowy/Windy/Foggy/Partly Cloudy): ");
        System.out.print("  → ");
        String condition = scanner.nextLine().trim();
        if (condition.isEmpty()) condition = "Unknown";

        System.out.print("  Humidity (%): ");
        int humidity;
        try {
            humidity = Integer.parseInt(scanner.nextLine().trim());
            if (humidity < 0 || humidity > 100) { System.out.println("  Humidity must be 0-100."); return; }
        } catch (NumberFormatException e) { System.out.println("  Invalid humidity."); return; }

        System.out.print("  Wind Speed (km/h): ");
        double wind;
        try { wind = Double.parseDouble(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("  Invalid wind speed."); return; }

        records.add(new WeatherRecord(city, temp, condition, humidity, wind));
        System.out.printf("%n  ✅ Weather record for '%s' added!%n", city);
    }

    public static void displaySummary() {
        if (records.isEmpty()) { System.out.println("\n  No weather records yet."); return; }

        System.out.println("\n  ╔══════════════════════════════════════════════════════════════╗");
        System.out.println("  ║                   🌍 WEATHER SUMMARY                        ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════════╣");

        for (WeatherRecord r : records) {
            System.out.printf("  ║  %s %-10s  Condition : %-15s%8s║%n",
                    r.getConditionEmoji(), r.city, r.condition, "");
            System.out.printf("  ║     🌡️  Temp    : %5.1f°C   Feels Like: %-18s║%n",
                    r.temperature, r.getFeelsLike());
            System.out.printf("  ║     💧 Humidity: %3d%%        Level     : %-18s║%n",
                    r.humidity, r.getHumidityLevel());
            System.out.printf("  ║     💨 Wind    : %5.1f km/h%28s║%n", r.windSpeed, "");
            System.out.println("  ╠══════════════════════════════════════════════════════════════╣");
        }

        // Statistics
        double max = records.get(0).temperature;
        double min = records.get(0).temperature;
        double sum = 0;
        String hottest = records.get(0).city;
        String coldest = records.get(0).city;

        for (WeatherRecord r : records) {
            sum += r.temperature;
            if (r.temperature > max) { max = r.temperature; hottest = r.city; }
            if (r.temperature < min) { min = r.temperature; coldest = r.city; }
        }
        double avg = sum / records.size();

        System.out.println("  ║                  📊 STATISTICS                              ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════════╣");
        System.out.printf("  ║  🔥 Hottest  : %-10s  %5.1f°C%22s║%n", hottest, max, "");
        System.out.printf("  ║  ❄️  Coldest  : %-10s  %5.1f°C%22s║%n", coldest, min, "");
        System.out.printf("  ║  📈 Average  : %5.1f°C%38s║%n", avg, "");
        System.out.printf("  ║  📍 Records  : %d city/cities logged%29s║%n", records.size(), "");
        System.out.println("  ╚══════════════════════════════════════════════════════════════╝");
    }

    public static void compareTemperatures(Scanner scanner) {
        if (records.size() < 2) {
            System.out.println("\n  Need at least 2 records to compare.");
            return;
        }
        System.out.println("\n  Available cities:");
        for (int i = 0; i < records.size(); i++) {
            System.out.printf("  %d. %s (%.1f°C)%n", i + 1, records.get(i).city, records.get(i).temperature);
        }
        System.out.print("  Select first city (number): ");
        int a; System.out.print("  Select second city (number): ");
        try {
            // Re-read both
            System.out.println();
            System.out.print("  Enter city 1 number: ");
            a = Integer.parseInt(scanner.nextLine().trim()) - 1;
            System.out.print("  Enter city 2 number: ");
            int b = Integer.parseInt(scanner.nextLine().trim()) - 1;

            if (a < 0 || b < 0 || a >= records.size() || b >= records.size()) {
                System.out.println("  Invalid selection."); return;
            }
            WeatherRecord r1 = records.get(a);
            WeatherRecord r2 = records.get(b);
            double diff = Math.abs(r1.temperature - r2.temperature);

            System.out.println("\n  ── Temperature Comparison ──");
            System.out.printf("  %s: %.1f°C  %s%n", r1.city, r1.temperature, r1.getConditionEmoji());
            System.out.printf("  %s: %.1f°C  %s%n", r2.city, r2.temperature, r2.getConditionEmoji());
            System.out.printf("  Difference: %.1f°C%n", diff);
            if (r1.temperature > r2.temperature)
                System.out.printf("  %s is warmer by %.1f°C%n", r1.city, diff);
            else if (r2.temperature > r1.temperature)
                System.out.printf("  %s is warmer by %.1f°C%n", r2.city, diff);
            else
                System.out.println("  Both cities have the same temperature!");
        } catch (NumberFormatException e) {
            System.out.println("  Invalid input.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║      🌤️  BASIC WEATHER INFO SYSTEM        ║");
        System.out.println("╚══════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            System.out.println("\n  ┌──────────────────────────────┐");
            System.out.println("  │           MENU               │");
            System.out.println("  ├──────────────────────────────┤");
            System.out.println("  │  1. Add Weather Record       │");
            System.out.println("  │  2. View Weather Summary     │");
            System.out.println("  │  3. Compare Temperatures     │");
            System.out.println("  │  4. Exit                     │");
            System.out.println("  └──────────────────────────────┘");
            System.out.print("  Choice: ");
            switch (scanner.nextLine().trim()) {
                case "1": addWeatherRecord(scanner); break;
                case "2": displaySummary(); break;
                case "3": compareTemperatures(scanner); break;
                case "4": running = false; System.out.println("\n  Stay weather-aware! 🌈"); break;
                default:  System.out.println("  Invalid choice.");
            }
        }
        scanner.close();
    }
}
