package rs.edu.raf.msa.pbp.utils;

public class PlayUtil
{

    public static double convertTimeToSeconds(String mmss)
    {
        String[] time = mmss.split(":");

        return Double.parseDouble(time[0]) * 60 + Double.parseDouble(time[1]);
    }

    public static int getQuarterIdx(String mmss)
    {
        return Integer.parseInt(mmss.split(":")[0]) / 12;
    }
}
