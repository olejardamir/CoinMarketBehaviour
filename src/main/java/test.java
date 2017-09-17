import Compression.ZipIt;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by damir on 17/09/17.
 */
public class test {

    public static void main(String[] args){
    try {

        //sets the relevant directory where the files are to be placed and downloaded
        dirRefresh();


        //downloads the relevant images into a folder. This data does not have to be images,
        // it could be anything that describes the coin behaviour
        System.out.println("Downloading, please wait...");
        downloadImages();
        System.out.println("Calculating, please wait...");


        //Now, get all the files from a directory, to create the N-dimensional points
        List<File> files = (List<File>) FileUtils.listFiles(new File("./Data/downloadedImages"), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        //Get the points for each file
        HashMap<String, String> points = calculatePoints(files);

        //for each point, find the sum of the distances
        HashMap<String, Double> distances = findDistances(points);

        //To get a nicely sorted output, get the map of coins again
        HashMap<String, String> namesOfCoins = getFullNames();

        //Now sort it all nicely
        TreeMap<Double, String> result = getDoubleStringTreeMap(distances, namesOfCoins);


        //show the result on a screen
        for(Double key:result.keySet()){
            System.out.println(key+","+result.get(key));
        }

    }catch (Exception e){e.printStackTrace();}



    }

    private static TreeMap<Double, String> getDoubleStringTreeMap(HashMap<String, Double> distances, HashMap<String, String> namesOfCoins) {
        TreeMap<Double, String> result = new TreeMap<Double, String>();
        for(String key:namesOfCoins.keySet()){
            try {
                String name = namesOfCoins.get(key);
                Double value = distances.get(key);
                result.put(value, name);
            }catch (Exception e){}
        }
        return result;
    }

    private static HashMap<String, String> getFullNames() throws IOException {
        HashMap<String, String> namesOfCoins = new HashMap<String, String>();
        BufferedReader read = new BufferedReader(new FileReader("./Data/mapOfCoins"));
        String line = "";
        while((line=read.readLine())!=null){
            String[]sp = line.split("\t");
            namesOfCoins.put(sp[2],sp[0]+","+sp[1]);
//            System.out.println(sp[2]+":"+sp[0]+","+sp[1]);
        }
        read.close();
        return namesOfCoins;
    }

    private static HashMap<String, Double> findDistances(HashMap<String, String> points) {
        HashMap<String, Double> distances = new HashMap<String, Double>();
        Calculations cals = new Calculations();

        for(String keyA:points.keySet()){
            String ptA = points.get(keyA);
            double sum = 0;
            for(String keyB:points.keySet()){
                String ptB = points.get(keyB);
                double distance = cals.calculateDistance(ptA,ptB);
                sum = sum+distance;
            }
//            System.out.println(keyA+":"+sum);
            distances.put(keyA,sum);
        }
        return distances;
    }

    private static HashMap<String, String> calculatePoints(List<File> files) {
        ZipIt zipit = new ZipIt();
        HashMap<String, String> points = new HashMap<String, String>();

        for(File pt:files){
            String nm = pt.getName();
            nm = nm.substring(0,nm.indexOf(".png"));
//            System.out.println(nm);
            String point = "";
            for(File v:files){
                if(pt.getAbsolutePath().equals(v.getAbsolutePath())){
                    point = point+"0,";
                }
                else {
                    point = point + zipit.getValueOf(pt.getAbsolutePath(), v.getAbsolutePath()) + ",";
                }
            }
            points.put(nm,point);
//            System.out.println(point);

        }
        return points;
    }

    private static void downloadImages() throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("./Data/mapOfCoins"));
        String line = "";
        while((line=read.readLine())!=null){
            String[]sp = line.split("\t");
            String img = sp[2];
            String imageUrl = "https://files.coinmarketcap.com/generated/sparklines/"+img+".png";
            File newFile = new File("./Data/downloadedImages/"+img+".png");
            URL url = new URL(imageUrl);
            FileUtils.copyURLToFile(url, newFile);

        }
        read.close();
    }

    private static void dirRefresh() throws IOException {
        //First, delete a directory with the previously downloaded files, if exists
        File imgDir = new File("./Data/downloadedImages");
        if (imgDir.exists()) {
            FileUtils.deleteDirectory(imgDir);
        }

        //now, create a new dir with the same name
        imgDir.mkdir();
    }

}
