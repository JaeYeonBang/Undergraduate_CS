package project.service;
import project.domain.Marker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;




public class GoogleAPIService {


    static final String apikey = "AIzaSyCBwnTPZFm2EAmJrmiQSrFqHzjMS60lNnI";
    public void downloadMap(String loacation , double [] center, ArrayList <Marker> markers){
        try{


//            String imageURL = "https://maps.googleapis.com/maps/api/staticmap?center="+ center_str_x + "," + center_str_y + "&zoom=14&size=600x600" +
//                    "&markers=icon:"+icon1+"%7C"+ center_str_x + "," + center_str_y +
//                    "&key="+apikey;
            String imageURL = makeUrl( center, 14 , 600 , 600 , markers) ;
//            System.out.println(imageURL);
            URL url = new URL(imageURL);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(loacation);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b,0,length);
            }
            is.close();
            os.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String makeUrl(double [] center, int zoom , int size_x , int size_y , ArrayList <Marker> markers){
        String center_str_x = Double.toString(center[0]);
        String center_str_y = Double.toString(center[1]);
        String result = "https://maps.googleapis.com/maps/api/staticmap?center=";
        result += center_str_x + "," + center_str_y;
        result += "&zoom="+ Integer.toString(zoom);
        result += "&size="+Integer.toString(size_x)+"x"+Integer.toString(size_y);
        for (Marker marker : markers){
            result +=  "&markers=icon:"+marker.getIcon_Url()+"%7C"+ marker.getX() + "," + marker.getY();
        }
        result += "&key="+apikey;
        return result;
    }

    public ImageIcon getMap(String location){
        return new ImageIcon((new ImageIcon(location)).getImage().getScaledInstance(612,612, Image.SCALE_SMOOTH));
    }

    public void fileDelete (String filename){
        File f = new File(filename);
        f.delete();
    }
}
