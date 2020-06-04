
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author lizzy
 * @Date 2020/5/20 10:44
 * @Version 1.0
 */
public class AtomikosJTATest {

    private static AtomikosDataSourceBean createAtomikosDataSourceBean(String dbName) {
        //连接池基本属性
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:mysql://localhost:3306/" + dbName);
        properties.setProperty("user", "root");
        properties.setProperty("password", "Epic0f@il");
        //使用AtomikosDataSourceBean封装com.mysql.jdbc.jdbc2.optional.MysqlXADataSource
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        //atomikos要求为每个AtomikosDataSourceBean名称，为了方便记忆，这里设置为dbName相同
        ds.setUniqueResourceName(dbName);
        ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        ds.setXaProperties(properties);
        return ds;
    }

    public static void main(String[] args) throws SystemException, SQLException {
        AtomikosDataSourceBean ds1 = createAtomikosDataSourceBean("test");
        AtomikosDataSourceBean ds2 = createAtomikosDataSourceBean("test1");
        Connection connection1 = null;
        Connection connection2 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        UserTransaction transaction = new UserTransactionImp();
        try {
            //开启事务
            transaction.begin();
            //执行db1
            connection1 = ds1.getConnection();
            ps1 = connection1.prepareStatement("INSERT INTO testtowpo(name,remark) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, "测试1");
            ps1.setString(2, "标记1");
            ps1.executeUpdate();

            //执行db2
            connection2 = ds2.getConnection();
            ps2 = connection2.prepareStatement("INSERT INTO testonepo(name,remark) VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, "测试2");
            ps2.setString(2, "标记2");
            ps2.executeUpdate();

            //模拟程序异常 可以实现跨库的事务
            int a = 1 / 0;

            //两阶段提交
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            ps1.close();
            ps2.close();
            connection1.close();
            connection2.close();
        }
    }
}
