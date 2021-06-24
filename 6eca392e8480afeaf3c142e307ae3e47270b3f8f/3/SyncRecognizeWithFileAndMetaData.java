package com.baidu.acu.asr.sync;

import java.io.File;
import java.util.List;

import com.baidu.acu.pie.client.AsrClient;
import com.baidu.acu.pie.client.AsrClientFactory;
import com.baidu.acu.pie.model.AsrConfig;
import com.baidu.acu.pie.model.RecognitionResult;
import com.baidu.acu.pie.model.RequestMetaData;

/**
 *
 * 通过传入音频文件 以及 自己创建一个 RequestMetaData 对象,用来控制请求时候的数据发送速度等参数
 *
 * @author xutengchao
 * @create 2019-05-05 17:23
 */
public class SyncRecognizeWithFileAndMetaData {
    private static String appName = "test";
    private static String ip = "180.76.107.131";    // asr服务的ip地址
    private static Integer port = 8050;             // asr服务的端口
    private static String pid = "1906";             // asr模型编号(不同的模型在不同的场景下asr识别的最终结果可能会存在很大差异)
    private static String userName = "user1";       // 用户名
    private static String passWord = "password1";   // 密码
    private static String audioPath = "/Users/v_xutengchao/Desktop/data-audios/60s.wav"; // 音频文件路径

    public static void main(String[] args) {
        syncRecognizeWithFileAndMetaData();
    }

    private static void syncRecognizeWithFileAndMetaData() {
        // 创建调用asr服务的客户端
        // asrConfig构造后就不可修改
        AsrConfig asrConfig = new AsrConfig()
                .appName(appName)
                .serverIp(ip)
                .serverPort(port)
                .productId(pid)
                .userName(userName)
                .password(passWord);
        AsrClient asrClient = AsrClientFactory.buildClient(asrConfig);
        // 创建metaData
        RequestMetaData requestMetaData = new RequestMetaData();
        requestMetaData.sendPerSeconds(0.05); //
        requestMetaData.sendPackageRatio(1);  //
        requestMetaData.sleepRatio(1);        //
        List<RecognitionResult> results = asrClient.syncRecognize(new File(audioPath), requestMetaData);
        // don't forget to shutdown !!!
        asrClient.shutdown();
        printResult(results);
    }

    private static void printResult(List<RecognitionResult> results) {
        for (RecognitionResult result : results) {
            System.out.println(String.format(AsrConfig.TITLE_FORMAT,
                    "file_name",
                    "serial_num",
                    "start_time",
                    "end_time",
                    "result"));
            System.out.println(String.format(AsrConfig.TITLE_FORMAT,
                    audioPath,
                    result.getSerialNum(),
                    result.getStartTime(),
                    result.getEndTime(),
                    result.getResult()
            ));
        }
    }
}
