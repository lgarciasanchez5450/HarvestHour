package lib;

public class StringUtils {
    private StringUtils() {}
    public static boolean isNumeric(String s) {
        if (s==null) return false;
        try{
            Integer.parseInt(s);
            return true;
        } catch (Exception ignored){
            return false;
        }
    }
    public static int parseInt(String s,int def) {
        if (s==null) return def;
        try{
            return Integer.parseInt(s);
        } catch (Exception ignored){
            return def;
        }
    }

}
