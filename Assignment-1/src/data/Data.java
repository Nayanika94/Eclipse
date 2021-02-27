package data;

/**
 * @author spramanik
 */
public class Data {

    String date;
    String city;
    int cases;
    int deaths;
    int recovered;

    public Data(String date, String city, int cases, int deaths, int recovered) {
        this.date = date;
        this.city = city;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public Data(String line){
        String[] tokens = line.split(",");
        this.date = tokens[0];
        this.city = tokens[1];
        this.cases = Integer.parseInt(tokens[2]);
        this.deaths = Integer.parseInt(tokens[3]);
        this.recovered = Integer.parseInt(tokens[4]);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public String toString() {
        return this.date + "," + this.city + "," + this.cases + "," + this.deaths + "," + this.recovered;
    }
}
