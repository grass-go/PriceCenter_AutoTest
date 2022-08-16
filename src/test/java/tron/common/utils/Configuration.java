package tron.common.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


public class Configuration {

    private static Config config;

    // 配置文件相对路径
    public static String confFilePath = "testng.conf";

    private static final Logger logger = LoggerFactory.getLogger("Configuration");

    /**
     * constructor.
     */

    public static Config getByPath( String configurationPath) {
//        if (isBlank(configurationPath)) {
//            throw new IllegalArgumentException("Configuration path is required!");
//        }

        if (config == null) {
            if("qa".equals(env)){
                configurationPath = "src/test/resources/testng_qa.conf";
            }else {
                configurationPath = "src/test/resources/testng.conf";
            }
            System.out.println(configurationPath);
            File configFile = new File(System.getProperty("user.dir") + "/" + configurationPath);
            if (configFile.exists()) {
                try {
                    config = ConfigFactory.parseReader(new InputStreamReader(new
                            FileInputStream(configurationPath)));
                    logger.info("use user defined config file in current dir");
                } catch (FileNotFoundException e) {
                    logger.error("load user defined config file exception: " + e.getMessage());
                }
            } else {
                config = ConfigFactory.load(configurationPath);
                logger.info("user defined config file doesn't exists, use default config file in jar");
            }
        }
        return config;
    }


    // 根据不同的环境加载不同的配置文件，方便测试
    @Parameters({"env"})
    @BeforeSuite()
    public void getMonitorUrl(String env) {
        this.env = env;
    }

    private static  String env;

}
