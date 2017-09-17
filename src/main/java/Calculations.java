/**
 * Created by damir on 17/09/17.
 */
public class Calculations {



    public double calculateDistance(String a, String b){
            a = trim(a);
            b = trim(b);

            float[] af = str2float(a);
            float[] bf = str2float(b);

            return ndistance(af,bf);
    }

    private float[] str2float(String s){
        String[] sp = s.split(",");
        float[] ret = new float[sp.length];
        for(int t=0;t<ret.length;t++){
            ret[t] = Float.parseFloat(sp[t]);
        }
        return ret;
    }

    private String trim(String s){
        s = s.trim();
        if(s.endsWith(",")){s=s.substring(0,s.length()-1);}
        return s;
    }

    private double ndistance(float[] a, float[] b) {
        float total = 0, diff;
        for (int i = 0; i < a.length; i++) {
            diff = b[i] - a[i];
            total += diff * diff;
        }
        return  Math.sqrt(total);
    }
}
