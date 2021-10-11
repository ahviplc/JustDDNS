package com.lc.ddns.service;

import com.aliyuncs.alidns.model.v20150109.DescribeSubDomainRecordsResponse;
import com.lc.ddns.util.AliDdnsUtils;
import com.lc.ddns.util.AutoStartUtil;
import com.lc.ddns.util.IPUtil;
import com.lc.ddns.util.MyUtils;
import com.lc.ddns.util.PropertiesUtil;
import com.lc.ddns.util.ShortCut;
import org.apache.log4j.Logger;

import java.util.List;


public class DdnsService {
    private static Logger logger = Logger.getLogger(DdnsService.class);
    private boolean isAutoStart = false;

    public void Start() {
        try {
            CheckAutoStart();
            CheckRecords();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("DdnsService Start 发生了错误,重新检查");
        } finally {
            Integer checkTime = Integer.parseInt(PropertiesUtil.getProperty("CheckTime"));
            MyUtils.sleep(checkTime);
            Start();
        }
    }

    /**
     * 检测域名解析
     *
     * @throws Exception cuo
     */
    private void CheckRecords() throws Exception {
        logger.info("CheckRecords 正在爬取主机公网IP地址...");
        String pubIP = IPUtil.getIP();//当前主机所在的公网IP地址
        logger.info("CheckRecords 爬取到的主机公网IP地址...为=> " + pubIP);
        logger.info("CheckRecords 检测域名解析...");
        for (String subDomainName : PropertiesUtil.getProperty("ChiledDomainNames").split(",")) {
            List<DescribeSubDomainRecordsResponse.Record> subDomainRecordsList = AliDdnsUtils.getDescribeSubDomainRecords(subDomainName);//获取子域名信息
            if (subDomainRecordsList.size() == 0) {//没有这个域名
                //添加
                logger.info("CheckRecords 添加解析记录：" + subDomainName + ":" + pubIP);
                boolean isSuccess = AliDdnsUtils.addDomainRecord(subDomainName, pubIP);
            } else {//有
                //判断解析值是否符合当前ip
                DescribeSubDomainRecordsResponse.Record record = subDomainRecordsList.get(0);
                if (!record.getValue().equals(pubIP) || !record.getType().equals("A") || !record.getRR().equals(subDomainName)) {//不相等,修改
                    logger.info("CheckRecords 修改解析记录：" + subDomainName + ":" + pubIP);
                    AliDdnsUtils.editDomainRecord(record.getRecordId(), subDomainName, pubIP);
                } else {
                    logger.info("CheckRecords 已存在此解析 公网ip未改变 无需更新dns解析 此解析记录详情 子域名: " + subDomainName + " 公网ip: " + record.getValue() + " 对应三级域名为: " + subDomainName + "." + record.getDomainName());
                }
            }
        }
        logger.info("CheckRecords 检测完毕...");
    }

    private void CheckAutoStart() {
        if (isAutoStart != PropertiesUtil.getProperty("AutoStart").equals("1")) {
            isAutoStart = PropertiesUtil.getProperty("AutoStart").equals("1");
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1, path.lastIndexOf("/"));
            ShortCut.createShortCut(path + "/StartAliYunDdns.bat", path + "/StartAliYunDdns");
            if (isAutoStart) {
                AutoStartUtil.setAutoStart(true, path + "/StartAliYunDdns.lnk");
                logger.info("CheckAutoStart 设置为开机启动");
            } else {
                AutoStartUtil.setAutoStart(false, path + "/StartAliYunDdns.lnk");
                logger.info("CheckAutoStart 取消开机启动");
            }
        }
    }
}
