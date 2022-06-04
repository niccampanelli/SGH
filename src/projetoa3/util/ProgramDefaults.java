package projetoa3.util;

import java.awt.Color;

/**
 *
 * @author Nicholas Campanelli
 */
public class ProgramDefaults {
    
    private static int userType = 3;
    private static String userName = "?";
    
    private static final Color baseColor = new Color(45, 201, 14);
    private static final Color baseColorLight = new Color(Integer.min(255, baseColor.getRed()+20),
                                                    Integer.min(255, baseColor.getGreen()+20),
                                                    Integer.min(255, baseColor.getBlue()+20));
    private static final Color baseColorDark = new Color(Integer.max(0, baseColor.getRed()-20),
                                                    Integer.max(0, baseColor.getGreen()-20),
                                                    Integer.max(0, baseColor.getBlue()-20));
    private static final Color backgroundColor = new Color(Integer.min(255, baseColor.getRed()+220),
                                                    Integer.min(255, baseColor.getGreen()+220),
                                                    Integer.min(255, baseColor.getBlue()+220));
    
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
