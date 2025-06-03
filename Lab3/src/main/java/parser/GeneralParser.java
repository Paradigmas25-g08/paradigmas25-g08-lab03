package parser;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
//Esta clase modela los atributos y metodos comunes a todos los distintos tipos de parser existentes en la aplicacion/
public abstract class GeneralParser {

    public Date stringToDate (String date) throws ParseException{
        //Thu, 15 May 2025 11:38:57 +0000
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
        Date formattedDate = formatter.parse(date.split("[+]")[0]);
        return formattedDate;
    }


    public Date stringToDateAtom (String date) throws ParseException{
        //2025-06-01T11:00:35+00:00
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
        String toFormat = date.split("[+]")[0].replace(String.valueOf("T"), " ");
        Date formattedDate = formatter.parse(toFormat);
        return formattedDate;
    }
}