package sgh.util;

import java.awt.Color;

/**
 *
 * @author Nicholas Campanelli
 */
public class ProgramDefaults {
    
    private static int userType = 3;
    private static String userName = "?";
    
    private static final Color baseColor = new Color(40, 100, 200);
    
    private static final Color baseColorLight =  getLightShade(baseColor, 2f);
    private static final Color baseColorDark =  getDarkShade(baseColor, 1f);
    private static final Color backgroundColor = getLightShade(baseColor, 1f);
    
    private static Color getLightShade(Color color, float perc){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        float rPerc = (r*100)/255;
        float gPerc = (g*100)/255;
        float bPerc = (b*100)/255;
        
        float newR = rPerc + ((((rPerc - 100)*-1)/10)*perc);
        float newG = gPerc + ((((gPerc - 100)*-1)/10)*perc);
        float newB = bPerc + ((((bPerc - 100)*-1)/10)*perc);
        
        int finalR = Integer.min(255, (int) Math.ceil(255*(newR)/100));
        int finalG = Integer.min(255, (int) Math.ceil(255*(newG)/100));
        int finalB = Integer.min(255, (int) Math.ceil(255*(newB)/100));
                
        return new Color(finalR, finalG, finalB);
    }
    
    private static Color getDarkShade(Color color, float perc){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        float rPerc = (r*100)/255;
        float gPerc = (g*100)/255;
        float bPerc = (b*100)/255;
        
        float newR = rPerc - ((((rPerc - 100)*-1)/10)*perc);
        float newG = gPerc - ((((gPerc - 100)*-1)/10)*perc);
        float newB = bPerc - ((((bPerc - 100)*-1)/10)*perc);
        
        int finalR = Integer.max(0, (int) Math.ceil(255*(newR)/100));
        int finalG = Integer.max(0, (int) Math.ceil(255*(newG)/100));
        int finalB = Integer.max(0, (int) Math.ceil(255*(newB)/100));
                
        return new Color(finalR, finalG, finalB);
    }
    
    public static int getUserType(){
        return userType;
    }
    
    public static void setUserType(int userType){
        if(userType >= 1 && userType <= 3){
            ProgramDefaults.userType = userType;
        }
    }
    
    public static String getUserName(){
        return userName;
    }
    
    public static void setUserName(String userName){
        ProgramDefaults.userName = userName;
    }
    
    public static Color getBaseColor(){
        return baseColor;
    }
    
    public static Color getBaseColorLight(){
        return baseColorLight;
    }
    
    public static Color getBaseColorDark(){
        return baseColorDark;
    }
    
    public static Color getBackgroundColor(){
        return backgroundColor;
    }
}
