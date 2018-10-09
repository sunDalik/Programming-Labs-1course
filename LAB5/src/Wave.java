public class Wave extends Sea {
    public int power;
    public int number;
    public String location;


    public Wave(int n, int pow, String loc){
        number = n;
        power = pow;
        location = loc;
    }

    public String toCsv(){
        return Integer.toString(power) + "," + Integer.toString(number) + "," + location;
    }

}
