package com.ldcr.dlock.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 分布式锁工具类
 *
 * @author zhanghonglong
 * @date 2020/1/6 18:40
 */
public class LockUtil {

    /**
     * 获取本机网卡物理地址
     *
     * @return
     */
    public static String getLocalMAC() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] macs = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (byte mac : macs) {
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            throw new IllegalStateException("getLocalMAC error");
        }
    }

    /**
     * 获取jvm进程ID
     *
     * @return
     */
    public static String getJvmPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        return runtime.getName().split("@")[0];
    }

    public static void main(String[] arg) {
        System.out.println("mac:" + LockUtil.getLocalMAC());
        System.out.println("jvmPid:" + LockUtil.getJvmPid());
    }

}

