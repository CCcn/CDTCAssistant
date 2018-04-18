package com.cdtc.student.cdtcassistant.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * 网络工具类
 * Created by pcc on 2018/4/18.
 *
 * @author pcc
 */
public class NetWorkUtil {

    /**
     * 获取ip地址
     */
//    public static String getIpAddress(Context context) {
//        String ip = null;
//        ConnectivityManager conMann = (ConnectivityManager)
//                context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mobileNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        NetworkInfo wifiNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//        if (mobileNetworkInfo.isConnected()) {
//            ip = getLocalIpAddress();
//        } else if (wifiNetworkInfo.isConnected()) {
//            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            int ipAddress = wifiInfo.getIpAddress();
//            ip = intToIp(ipAddress);
//        }
//        return ip;
//    }

//    private static String getLocalIpAddress() {
//        try {
//            String ipv4;
//            ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface ni : nilist) {
//                ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
//                for (InetAddress address : ialist) {
//                    if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = address.getHostAddress())) {
//                        return ipv4;
//                    }
//                }
//
//            }
//
//        } catch (SocketException ex) {
//        }
//        return null;
//    }

    private static String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
}
