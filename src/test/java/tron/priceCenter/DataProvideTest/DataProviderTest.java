package tron.priceCenter.DataProvideTest;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;


//Method方法是通过反射获取的，总之哪个方法调用我Method就是那个方法。
//method.getDeclaringClass().getSimpleName()可以获取方法所属的类的类名。

/*我这里规定了csv的文件名就是测试类的类名，用例名就是方法名。
  return (Iterator) new CSVData(…)就是将CSV读取类读取的结果返回，返回的类型是Iterator的，符合@DataProvider的返回值类型要求。当@Test(dataProvider = "dataSource")注解的测试方法执行时就会调用Iterator的hasNext()判断是否有数据和next()获取数据。
*/

public class DataProviderTest {
    /*
     * @DataProvider的返回值类型只能是Object[][]与Iterator[]
     *
     * @param method
     * @return
     */
    @DataProvider
    public Iterator<Object[]> dataSource(Method method) {
        return (Iterator) new CSVData(method.getDeclaringClass().getSimpleName(), method.getName());
    }
}
