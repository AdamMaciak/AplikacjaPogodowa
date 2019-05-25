package com.example.aplikacjapogodowa.parser;

public class Data_converter {

    public static String degreetoname(String str)
    {
        double degree=Double.parseDouble(str);

        if(degree>=0 && degree<30) {
             return "N";
        }
        else if(degree>=30 && degree<60) {
            return "NE";
        }
        else if(degree>=60 && degree<120) {
            return "E";
        }
        else if(degree>=120 && degree<150) {
            return "SE";
        }
        else if(degree>=150 && degree<210) {
            return "S";
        }
        else if(degree>=210 && degree<240) {
            return "SW";
        }
        else if(degree>=240 && degree<300) {
            return "W";
        }
        else if(degree>=300 && degree<330) {
            return "NW";
        }
        else{
            return "N";
        }
    }

    public static String round_numbers(String str)
    {

        double tempdouble=Double.parseDouble(str);
        int castint=(int)Math.round(tempdouble);
        String rounded_number=Integer.toString(castint);
        return rounded_number;
    }
}
