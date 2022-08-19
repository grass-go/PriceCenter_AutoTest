package tron.common.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
public class Configuration {

    private Configuration c = new Configuration();

    private static Config config;

    // 配置文件相对路径
    public static String confFilePath = "testng.conf";

//    private static final Logger log = LoggerFactory.getLogger("Configuration");

    /**
     * constructor.
     */

    public static Config getByPath( String configurationPath) {
        if (config == null) {
            initEnv();
            if("qa".equals(env)){
                configurationPath = "src/test/resources/testng_qa.conf";
            }else {
                configurationPath = "src/test/resources/testng.conf";
            }
            log.info(configurationPath);
            File configFile = new File(System.getProperty("user.dir") + "/" + configurationPath);
            if (configFile.exists()) {
                try {
                    config = ConfigFactory.parseReader(new InputStreamReader(new
                            FileInputStream(configurationPath)));
                    log.info("use user defined config file in current dir");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    log.error("load user defined config file exception: " + e.getMessage());
                }
            } else {
                config = ConfigFactory.load(configurationPath);
                log.info("user defined config file doesn't exists, use default config file in jar");
            }
        }
        return config;
    }

    // 区分qa和线上等的token和配置使用
    private static void initEnv(){
        Map<String,String> envs = System.getenv();
        if(envs.containsKey("env")){
            env = envs.get("env");
        }
    }
    private static  String env;

}
