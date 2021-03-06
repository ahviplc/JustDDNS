package com.lc.ddns.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云解析工具
 *
 * @author xiang
 */
public class AliDdnsUtils {
    private static Logger logger = Logger.getLogger(AliDdnsUtils.class);

    private static IAcsClient client = null;

    private static String regionId;
    private static String accessKeyId;
    private static String accessKeySecret;

    static {
        regionId = "cn-hangzhou"; // 必填固定值，必须为“cn-hanghou”
        accessKeyId = PropertiesUtil.getProperty("AccessKeyID").trim(); // your accessKey
        accessKeySecret = PropertiesUtil.getProperty("AccessKeySecret").trim(); // your accessSecret
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
    }

    /**
     * 重新加载客户端
     */
    public static void reload() {
        if(!accessKeyId.equals(PropertiesUtil.getProperty("AccessKeyID"))|| !accessKeySecret.equals(PropertiesUtil.getProperty("AccessKeySecret"))){
            regionId = "cn-hangzhou"; // 必填固定值，必须为“cn-hanghou”
            accessKeyId = PropertiesUtil.getProperty("AccessKeyID"); // your accessKey
            accessKeySecret = PropertiesUtil.getProperty("AccessKeySecret"); // your accessSecret
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            client = new DefaultAcsClient(profile);
            logger.info("reload 更新了AccessKey");
        }
    }


    /**
     * 获取客户端数据
     *
     * @return
     */
    public static IAcsClient getClient() {
        reload();
        return client;
    }

    /**
     * 添加解析记录
     *
     * @param subDomain 子域名
     * @param value     解析的公网ip地址
     * @return
     */
    public static boolean addDomainRecord(String subDomain, String value) {
        IAcsClient client = AliDdnsUtils.getClient(); //获取客户端
        AddDomainRecordRequest request = new AddDomainRecordRequest();
        request.setDomainName(PropertiesUtil.getProperty("DomainName")); //设置主域名
        request.setRR(subDomain); //设置 子域名
        request.setType("A"); //设置type 解析的是个IPV4
        request.setValue(value); //设置 解析的地址 解析的公网ip地址
        try {
            AddDomainRecordResponse response = client.getAcsResponse(request);
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            logger.error("addDomainRecord 添加解析记录 ErrCode:" + e.getErrCode());
            logger.error("addDomainRecord 添加解析记录 ErrMsg:" + e.getErrMsg());
            logger.error("addDomainRecord 添加解析记录 RequestId:" + e.getRequestId());
        }
        return false;
    }

    /**
     * 删除解析记录
     *
     * @param recordId 记录id
     * @return
     */
    public static boolean delDomainRecord(String recordId) {
        IAcsClient client = AliDdnsUtils.getClient(); //获取客户端
        DeleteDomainRecordRequest request = new DeleteDomainRecordRequest();
        request.setRecordId(recordId);
        try {
            DeleteDomainRecordResponse response = client.getAcsResponse(request);
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            logger.error("delDomainRecord 删除解析记录 ErrCode:" + e.getErrCode());
            logger.error("delDomainRecord 删除解析记录 ErrMsg:" + e.getErrMsg());
            logger.error("delDomainRecord 删除解析记录 RequestId:" + e.getRequestId());
        }
        return false;
    }

    /**
     * 修改解析记录
     *
     * @param recordId 记录id
     * @param rR       主机记录 也就是 子域名
     * @param value    解析的公网ip地址
     * @return
     */
    public static boolean editDomainRecord(String recordId, String rR, String value) {
        IAcsClient client = AliDdnsUtils.getClient();//获取客户端
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        request.setRecordId(recordId);//
        request.setRR(rR); //设置 主机记录 也就是 子域名
        request.setType("A"); //类型 A 将一个域名解析到一个ipv4地址
        request.setValue(value); //记录值 也就是IP 解析的公网ip地址
        try {
            UpdateDomainRecordResponse response = client.getAcsResponse(request);
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            logger.error("editDomainRecord 修改解析记录 ErrCode:" + e.getErrCode());
            logger.error("editDomainRecord 修改解析记录 ErrMsg:" + e.getErrMsg());
            logger.error("editDomainRecord 修改解析记录 RequestId:" + e.getRequestId());
        }
        return false;
    }

    /**
     * 获取子域名的记录列表
     *
     * @param subDomainName 子域名
     * @return
     */
    public static List<DescribeSubDomainRecordsResponse.Record> getDescribeSubDomainRecords(String subDomainName) {
        List<DescribeSubDomainRecordsResponse.Record> records = new ArrayList<>();
        IAcsClient client = AliDdnsUtils.getClient(); //获取客户端
        DescribeSubDomainRecordsRequest request = new DescribeSubDomainRecordsRequest(); //构造一个获取子域名的记录列表请求
        request.setSubDomain(subDomainName + "." + PropertiesUtil.getProperty("DomainName")); //如 "a.b"+"."+"xxx.com"
        try {
            DescribeSubDomainRecordsResponse response = client.getAcsResponse(request);
            records.addAll(response.getDomainRecords());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            logger.error("getDescribeSubDomainRecords 获取子域名的记录列表 ErrCode:" + e.getErrCode());
            logger.error("getDescribeSubDomainRecords 获取子域名的记录列表 ErrMsg:" + e.getErrMsg());
            logger.error("getDescribeSubDomainRecords 获取子域名的记录列表 RequestId:" + e.getRequestId());
        }
        return records;
    }

    /**
     * 获取主域名解析记录列表 也就是主域名的解析记录列表 list
     *
     * @return
     */
    public static List<DescribeDomainRecordsResponse.Record> getDescribeDomainRecords() {
        List<DescribeDomainRecordsResponse.Record> records = new ArrayList<>();
        IAcsClient client = AliDdnsUtils.getClient(); //获取客户端
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest(); //构造一个获取解析记录列表请求
        request.setDomainName(PropertiesUtil.getProperty("DomainName")); //获取主域名
        request.setPageSize(10l); //设置请求的每页的条数 10条
        try {
            DescribeDomainRecordsResponse response = client.getAcsResponse(request);
            records.addAll(response.getDomainRecords());
            //如果还有下一页
            while (response.getPageNumber() * response.getPageSize() < response.getTotalCount()) {
                request.setPageNumber(response.getPageNumber() + 1); //设置页数加一
                response = client.getAcsResponse(request); //再次请求
                records.addAll(response.getDomainRecords()); //添加到list里面
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            logger.error("getDescribeDomainRecords 获取主域名解析记录列表 ErrCode:" + e.getErrCode());
            logger.error("getDescribeDomainRecords 获取主域名解析记录列表 ErrMsg:" + e.getErrMsg());
            logger.error("getDescribeDomainRecords 获取主域名解析记录列表 RequestId:" + e.getRequestId());
        }
        return records;
    }
}
