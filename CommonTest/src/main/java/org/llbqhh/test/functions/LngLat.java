package org.llbqhh.test.functions;

/**
 * ��γ�ȼ�����
 *
 * @author Administrator
 */
public class LngLat {
    static final double EARTH_RADIUS = 6378.137;

    /**
     * ���ݸ�����������γ�ȼ�������֮��ľ���
     *
     * @param lng1 ����1
     * @param lat1 γ��1
     * @param lng2 ����2
     * @param lat2 γ��2
     * @return ���ؾ���
     */
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
// double EARTH_RADIUS = 6372.797;
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS * 1000;
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        double sTwo = Double.parseDouble(df.format(s));
        return sTwo;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static void main(String[] args) {
        double lng1 = 116.176861;
        double lat1 = 49.752473;

        double lng2 = 116.176861;
        double lat2 = 49.753473;

        double longt = GetDistance(lng1, lat1, lng2, lat2);
        System.out.println(longt);
    }
}
