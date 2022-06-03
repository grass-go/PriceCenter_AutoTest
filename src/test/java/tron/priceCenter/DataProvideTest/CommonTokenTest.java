package tron.priceCenter.DataProvideTest;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;



import java.util.Map;
import org.testng.annotations.Test;
public class CommonTokenTest extends DataProviderTest {
        @Test(dataProvider = "dataSource")
        public void id2(Map data) {
            System.out.println(data);
            Assert.assertEquals(1,1);
        }

        @Test(dataProvider = "dataSource")
        public void id1(Map data) {
            System.out.println(data);
            Assert.assertEquals(1,1);
        }

    }

