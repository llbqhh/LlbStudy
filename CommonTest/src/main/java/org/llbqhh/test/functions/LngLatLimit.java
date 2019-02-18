package org.llbqhh.test.functions;

/**
 * ��γ�ȼ�����
 *
 * @author Administrator
 */
public class LngLatLimit {
    public static void main(String[] args) {
        double lng;
        double lat;
        lng = 116.176861;
        lat = 49.752473;
        double distance = 2.5;
        getLimitsByDistance(lng, lat, distance);
    }

    /**
     * ���ݸ����ľ�γ�ȣ�ȡ��ָ����Χ����λǧ�ף��ľ�γ���ٽ�ֵ
     *
     * @param lng
     * @param lat
     * @param distance
     */
    public static void getLimitsByDistance(double lng, double lat, double distance) {
        double range = 180 / Math.PI * distance / 6372.797; //����� limit �ʹ������� ��km ֮��,��λkm
        double lngR = range / Math.cos(lat * Math.PI / 180);
        double maxLat = lat + range; //���γ��
        double minLat = lat - range; //��Сγ��
        double maxLng = lng + lngR; //��󾭶�
        double minLng = lng - lngR; //��С����
        System.out.println(maxLat);
        System.out.println(minLat);
        System.out.println(maxLng);
        System.out.println(minLng);
    }
}
