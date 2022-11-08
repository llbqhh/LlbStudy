package org.llbqhh.study.bigdata.hive.metastore;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.PrincipalType;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * 用于测试使用metastroe client对database进行操作时，对于不同hive版本的兼容性
 * metastore 客户端版本3.1.2
 *   hive-1.2.2 测试失败，不兼容，未解决
 *   hive-2.3.9(h2底座hive版本) 测试失败，不兼容，未解决
 *   hive-3.1.2(目前h3底座hive版本) 测试通过
 *   hive-3.1.3 测试通过
 *   hive-4.0.0-alpha-1 测试通过
 * metastore 客户端版本2.3.9
 *   hive-1.2.2 未测试，需要低版本hadoop
 *   hive-2.3.9(h2底座hive版本) 测试通过
 *   hive-3.1.2(目前h3底座hive版本) 测试通过
 *   hive-3.1.3 测试通过
 *   hive-4.0.0-alpha-1 测试通过
 * metastore 客户端版本1.2.2
 *   hive-1.2.2 测试通过
 *   hive-2.3.9(h2底座hive版本) 测试通过
 *   hive-3.1.2(目前h3底座hive版本) 测试通过
 *   hive-3.1.3 测试通过
 *   hive-4.0.0-alpha-1 测试通过
 */
@Slf4j
public class HiveMetastoreDatabaseTest {


    private String testDb = "test_tmp_db";
    private String alterProperty = "alter";

    static {
        System.setProperty("hadoop.home.dir", "D:/AllProjects/IdeaHistoryProjects/winutils/hadoop-3.2.2/");
    }

    private TestHiveMetaStoreClient getHiveMetaStoreClient() throws MetaException {
        HiveConf hiveConf = new HiveConf();
        // hive1.2.2
        hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS, "thrift://lilibiao.vm.ubuntu:9083");
        // 更高hive版本
//        hiveConf.set("hive.metastore.uris", "thrift://lilibiao.vm.ubuntu:9083");
        TestHiveMetaStoreClient client = new TestHiveMetaStoreClient(hiveConf);
        return client;
    }

    @Test
    public void testInOrder() {
        testCreateDatabase();
        testAlterDatabase();
        testShowDatabases();
        testDeleteDatabase();
    }

    @Test
    public void testCreateDatabase() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            Database database = new Database();
            database.setName(testDb);
            database.setDescription("created by meta-manage");
            database.setOwnerName("lilibiao");
            Map<String, String> parameters = Maps.newHashMap();
            parameters.put("a", "a");
            parameters.put(alterProperty, "no");
            database.setParameters(parameters);
            database.setOwnerType(PrincipalType.USER);
            client.createDatabase(database);
            log.info("<<<<<<<<<<create db {}", client.getDatabase(database.getName()));
            assert client.getDatabase(testDb) != null;
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAlterDatabase() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            Database oldDb = client.getDatabase(testDb);
            Map<String, String> parameters = oldDb.getParameters();
            String oldAlter = parameters.get(alterProperty);
            log.info("<<<<<<<<<<old parameters alter {}", oldAlter);
            // 2.3.9版本不支持修改desc
            oldDb.setDescription("alter by meta-manage");
            oldDb.setOwnerName("lilibiao02");
            parameters.put(alterProperty, "yes");
            oldDb.setParameters(parameters);
            client.alterDatabase(testDb, oldDb);
            Database newDb = client.getDatabase(oldDb.getName());
            log.info("<<<<<<<<<<alter db {}", newDb);
            assert !StringUtils.equals(oldAlter, newDb.getParameters().get(alterProperty));
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteDatabase() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            client.dropDatabase(testDb);
            log.info("<<<<<<<<<<drop db {}", testDb);
            assert !client.getAllDatabases().contains(testDb);
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testShowDatabases() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            List<String> databases = client.getAllDatabases();
            log.info("<<<<<<<<<<show databases size {}", databases.size());
            log.info("<<<<<<<<<<show databases result {}", databases);
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }
}

class TestHiveMetaStoreClient extends HiveMetaStoreClient implements AutoCloseable {
    public TestHiveMetaStoreClient(HiveConf conf) throws MetaException {
        super(conf);
    }
}