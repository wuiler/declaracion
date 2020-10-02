package com.decla.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wuiler
 */
public final class Fecha {
	
	private static final String SPANISHDATE = "dd/MM/yyyy";
	private static final String SPANISHDATEFULL = "dd/MM/yyyy HH:mm:ss";
	
    private Fecha() {
    	//for Compliant
	}
    
    //using now java 8 implementations
    public static int minusDays(int cantidadDias) {
    	
    	LocalDate now = LocalDate.now();
    	LocalDate minus = now.minusDays(cantidadDias);
    	
    	Period period = Period.between(now, minus);
    	int diff = period.getDays();
    	
    	return diff;
    	
    }

    public static String comoToken() {

    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");    
		return df.format(hoy());
	
    } 
    
    public static int anioActual() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
    }
    
	public static Date hoy() {
        return new Date();
    }

    public static String hoyTexto() {
        SimpleDateFormat df = new SimpleDateFormat(SPANISHDATE);
        return df.format(hoy());
    }

    public static String formatAbreviado(Date f) {
        SimpleDateFormat df = new SimpleDateFormat("ddMMyy");
        return df.format(f);
    }

    public static String format(Date f) {
        SimpleDateFormat df = new SimpleDateFormat(SPANISHDATE);
        return df.format(f);
    }

    public static String formatLog(Date f) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(f);
    }

    public static String formatDateTime(Date f) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(f);
    }

    public static String formatDateTimeFull(Date f) {
        SimpleDateFormat df = new SimpleDateFormat(SPANISHDATEFULL);
        return df.format(f);
    }

    public static Date parse(String fecha) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(SPANISHDATE);
            return df.parse(fecha);
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static Date parse(String fecha, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(fecha);
        } catch (ParseException ex) {
            return null;
        }
    }    
    
    public static String parseToSpanish(String fecha) {

		DateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		DateFormat dfOutout = new SimpleDateFormat(SPANISHDATE);

		Date date;
		try {
			date = dfInput.parse(fecha);
			return dfOutout.format(date);
						
		} catch (ParseException e) {
			return null;
        }
    }       

    public static String parseToSpanishWithHour(String fecha) {

		DateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dfOutout = new SimpleDateFormat(SPANISHDATEFULL);

		Date date;
		try {
			date = dfInput.parse(fecha);
			return dfOutout.format(date);
						
		} catch (ParseException e) {
			return null;
        }
    }       

    public static Date restarFecha(Date fecha, long dias) {
        long d = 1000L * 60 * 60 * 24;
        return new Date(fecha.getTime() - d * dias);
    }

    public static String hSQLFormatDate(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(d);
    }

    public static String diaDeLaSemana(int dia) {
        int elDia = dia;
    	if (elDia > 7) {
    		elDia  -= 7;
        }
        switch (elDia) {
            case GregorianCalendar.SUNDAY:
                return "Domingo";
            case GregorianCalendar.MONDAY:
                return "Lunes";
            case GregorianCalendar.TUESDAY:
                return "Martes";
            case GregorianCalendar.WEDNESDAY:
                return "Miércoles";
            case GregorianCalendar.THURSDAY:
                return "Jueves";
            case GregorianCalendar.FRIDAY:
                return "Viernes";
            case GregorianCalendar.SATURDAY:
                return "Sábado";
            default:
                return null;
        }
    }

    public static String codigoDia(String dia) {
        if (dia.equals("Miércoles")) {
            return "X";
        } else {
            return Character.toString(dia.charAt(0));
        }
    }

    public static String diaDeLaSemana(Date fecha) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fecha);
        int diaDeLaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
        return diaDeLaSemana(diaDeLaSemana);
    }

    public static Date sumarDias(Date d, int offset) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        gc.add(GregorianCalendar.DATE, offset);
        return gc.getTime();
    }

    public static Date fechaPrimeraHora(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date fechaUltimaHora(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.MILLISECOND, 999);

        return c.getTime();
    }

    public static Long getDiferenciaSegundos(Date inicial, Date ahora) {
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        cal1.setTime(inicial);
        cal2.setTime(ahora);

        //Calculate difference in milliseconds
        Long diff = Math.abs(cal2.getTimeInMillis() - cal1.getTimeInMillis());
        // Calculate difference in seconds
        return (diff / 1000);
    }

    public static String getFechaLiteral(Date fechaAcordada) {

        StringBuilder fechaLiteral = new StringBuilder();

        DateFormat fechaFormatter = new SimpleDateFormat(SPANISHDATE);
        DateFormat fechaLargaFormatter = new SimpleDateFormat("'el ' EEEE dd ' de ' MMMM");
        DateFormat horaFormatter = new SimpleDateFormat("H");

        GregorianCalendar hoy = new GregorianCalendar();

        if (fechaFormatter.format(hoy.getTime()).equals(fechaFormatter.format(fechaAcordada))) {
            fechaLiteral.append("Hoy");
        } else {
            hoy.add(GregorianCalendar.DAY_OF_MONTH, 1);
            if (fechaFormatter.format(hoy.getTime()).equals(fechaFormatter.format(fechaAcordada))) {
                fechaLiteral.append("Mañana");
            } else {
                fechaLiteral.append(fechaLargaFormatter.format(fechaAcordada));
            }
        }

        int hora = Integer.parseInt(horaFormatter.format(fechaAcordada));
        switch (hora) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                fechaLiteral.append(" a la mañana");
                break;
            case 11:
            case 12:
            case 13:
            case 14:
                fechaLiteral.append(" al mediodia");
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                fechaLiteral.append(" a la tarde");
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                fechaLiteral.append(" a la noche");
                break;
            default:
            	break;
        }
        return fechaLiteral.toString();
    }

    /**
     * Extract date
     * 
     * @return Date object
     * @throws ParseException 
     */
    public static Date extractDate(String text) throws ParseException {
        Date date = null;
        boolean dateFound = false;

        String year = null;
        String month = null;
        String monthName = null;
        String day = null;
        String hour = null;
        String minute = null;
        String second = null;
        String ampm = null;

        String regexDelimiter = "[-:\\/.,]";
        String regexDay = "((?:[0-2]?\\d{1})|(?:[3][01]{1}))";
        String regexMonth = "(?:([0]?[1-9]|[1][012])|(Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?))";
        String regexYear = "((?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3}))";
        String regexHourMinuteSecond = "(?:(?:\\s)((?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):([0-5][0-9])(?::([0-5][0-9]))?(?:\\s?(am|AM|pm|PM))?)?";
        String regexEndswith = "(?![\\d])";

        // DD/MM/YYYY
        String regexDateEuropean =
            regexDay + regexDelimiter + regexMonth + regexDelimiter + regexYear + regexHourMinuteSecond + regexEndswith;

        // MM/DD/YYYY
        String regexDateAmerican =
            regexMonth + regexDelimiter + regexDay + regexDelimiter + regexYear + regexHourMinuteSecond + regexEndswith;

        // YYYY/MM/DD
        String regexDateTechnical =
            regexYear + regexDelimiter + regexMonth + regexDelimiter + regexDay + regexHourMinuteSecond + regexEndswith;

        // see if there are any matches
        Matcher m = checkDatePattern(regexDateEuropean, text);
        if (m.find()) {
            day = m.group(1);
            month = m.group(2);
            monthName = m.group(3);
            year = m.group(4);
            hour = m.group(5);
            minute = m.group(6);
            second = m.group(7);
            ampm = m.group(8);
            dateFound = true;
        }

        if(!dateFound) {
            m = checkDatePattern(regexDateAmerican, text);
            if (m.find()) {
                month = m.group(1);
                monthName = m.group(2);
                day = m.group(3);
                year = m.group(4);
                hour = m.group(5);
                minute = m.group(6);
                second = m.group(7);
                ampm = m.group(8);
                dateFound = true;
            }
        }

        if(!dateFound) {
            m = checkDatePattern(regexDateTechnical, text);
            if (m.find()) {
                year = m.group(1);
                month = m.group(2);
                monthName = m.group(3);
                day = m.group(3);
                hour = m.group(5);
                minute = m.group(6);
                second = m.group(7);
                ampm = m.group(8);
                dateFound = true;
            }
        }

        if(dateFound) {
            String dateFormatPattern = "";
            String dayPattern = "";
            String dateString = "";

            if(day != null) {
                dayPattern = "d" + (day.length() == 2 ? "d" : "");
            }

            if(day != null && month != null && year != null) {
                dateFormatPattern = "yyyy MM " + dayPattern;
                dateString = year + " " + month + " " + day;
            } else if(monthName != null) {
                if(monthName.length() == 3) dateFormatPattern = "yyyy MMM " + dayPattern;
                else dateFormatPattern = "yyyy MMMM " + dayPattern;
                dateString = year + " " + monthName + " " + day;
            }

            if(hour != null && minute != null) {
                dateFormatPattern += " hh:mm";
                dateString += " " + hour + ":" + minute;
                if(second != null) {
                    dateFormatPattern += ":ss";
                    dateString += ":" + second;
                }
            }

            if(!dateFormatPattern.equals("") && !dateString.equals("")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern.trim(), Locale.US);
                date = dateFormat.parse(dateString.trim());
            }
        }

        return date;
    }

    private static Matcher checkDatePattern(String regex, String text) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        return p.matcher(text);
    }
}
