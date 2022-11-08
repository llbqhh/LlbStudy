package org.llbqhh.study.bigdata.hive.metastore;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hadoop.hive.metastore.api.*;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于测试使用metastroe client对table进行操作时，对于不同hive版本的兼容性
 * metastore 客户端版本3.1.2
 *   hive-1.2.2 测试失败，不兼容，未解决
 *   hive-2.3.9(h2底座hive版本) 测试失败，不兼容，未解决
 *   hive-3.1.2(目前h3底座hive版本) 测试通过
 *   hive-3.1.3 测试通过
 *   hive-4.0.0-alpha-1 测试通过
 * metastore 客户端版本2.3.9
 *   hive-1.2.2 测试通过
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
 *
 */
@Slf4j
public class HiveMetastoreTableTest {

    private String testDb = "default";
    private String testTable = "test_tmp_table";
    // 测试修改表名
    private String testTableAlterName = "test_tmp_table_new";
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
        testCreateTable();
        testAlterTable();
        testDropTableColumn();
        testShowTables();
        testDropTable();
    }

    @Test
    public void testCreateTable() {
        String tableOwner = "lilibiao";
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            Table table = new Table();
            // 基本信息
            table.setTableName(testTable);
            table.setDbName(testDb);
            table.setOwner(tableOwner);
//            table.setTableType(TableType.MANAGED_TABLE.name());
            table.setTableType(TableType.EXTERNAL_TABLE.name());


            // 表属性
            Map<String, String> tblParameters = Maps.newHashMap();
            tblParameters.put("a", "a");
            tblParameters.put(alterProperty, "no");
            table.setParameters(tblParameters);

            // 分区列
            List<FieldSchema> partitionKeys = new ArrayList<>();
            partitionKeys.add(new FieldSchema("dt", "string", "name filed"));
            table.setPartitionKeys(partitionKeys);

            // 列及其他
            SerDeInfo serdeInfo = new SerDeInfo();
            serdeInfo.setName(testTable);
            serdeInfo.setParameters(tblParameters);
            StorageDescriptor sd = new StorageDescriptor();
            sd.setSerdeInfo(serdeInfo);
            sd.setParameters(tblParameters);
            List<FieldSchema> cols = new ArrayList<>();
            cols.add(new FieldSchema("name", "string", "name filed"));
            cols.add(new FieldSchema("age", "smallint", "age filed"));
            sd.setCols(cols);
            // 存储格式
            sd.setInputFormat("org.apache.hadoop.mapred.TextInputFormat");
            sd.setOutputFormat("org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat");
            sd.getSerdeInfo().setSerializationLib("org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe");
            table.setSd(sd);

            // 设置权限
//            table.setPrivileges(new PrincipalPrivilegeSet(
//                    ImmutableMap.of(tableOwner, ImmutableList.of(
//                            new PrivilegeGrantInfo("SELECT", 0, tableOwner, PrincipalType.USER, true),
//                            new PrivilegeGrantInfo("INSERT", 0, tableOwner, PrincipalType.USER, true),
//                            new PrivilegeGrantInfo("UPDATE", 0, tableOwner, PrincipalType.USER, true),
//                            new PrivilegeGrantInfo("DELETE", 0, tableOwner, PrincipalType.USER, true)
//                    )),
//                    ImmutableMap.of(),
//                    ImmutableMap.of()));
            client.createTable(table);
            log.info("<<<<<<<<<<create table {}", client.getTable(table.getDbName(), table.getTableName()));
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAlterTable() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            Table table = client.getTable(testDb, testTable);
            // 修改表属性
            String oldProperty = table.getParameters().get(alterProperty);
            table.getParameters().put(alterProperty, "yes");
            String oldName = table.getTableName();
            // 修改表名
            table.setTableName(testTableAlterName);
            int oldColSize = table.getSd().getColsSize();
            // 增加字段
            table.getSd().addToCols(new FieldSchema("new_filed1", "smallint", "new_filed1.."));
            table.getSd().addToCols(new FieldSchema("new_filed2", "smallint", "new_filed2.."));
            client.alter_table(testDb, testTable, table);
            Table newTable = client.getTable(table.getDbName(), table.getTableName());
            log.info("<<<<<<<<<<alter table ok {}", newTable);
            log.info("<<<<<<<<<<alter table name, oldName {}, newName {}", oldName, newTable.getTableName());
            log.info("<<<<<<<<<<alter table property, oldName {}, newName {}", oldProperty, newTable.getParameters().get(alterProperty));
            log.info("<<<<<<<<<<alter table cols, oldCol size {}, newCol size {}", oldColSize, newTable.getSd().getColsSize());
            assert !StringUtils.equals(oldName, newTable.getTableName())
                    && !StringUtils.equals(oldProperty, newTable.getParameters().get(alterProperty))
                    && oldColSize < newTable.getSd().getColsSize();
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDropTableColumn() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            Table table = client.getTable(testDb, testTableAlterName);
            int oldColSize = table.getSd().getColsSize();
            // 修改表属性
            table.getParameters().put(alterProperty, "yes");
            // 修改表名
            table.setTableName(testTableAlterName);
            // 增加字段
            List<FieldSchema> cols = table.getSd().getCols();
            System.out.println(cols);
            cols.remove(cols.size() - 1);
            client.alter_table(testDb, testTableAlterName, table);
            System.out.println(client.getTable(testDb, testTableAlterName));
            Table newTable = client.getTable(table.getDbName(), table.getTableName());
            log.info("<<<<<<<<<<alter table drop col, oldCol size {}, newCol size {}", oldColSize, newTable.getSd().getColsSize());
            assert oldColSize > newTable.getSd().getColsSize();
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDropTable() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            client.dropTable(testDb, testTableAlterName);
            log.info("<<<<<<<<<<drop table {}", testTableAlterName);
            List<String> tables = client.getAllTables(testDb);
            assert !tables.contains(testTable) && !tables.contains(testTableAlterName);
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testShowTables() {
        try (TestHiveMetaStoreClient client = getHiveMetaStoreClient()) {
            List<String> tables = client.getAllTables(testDb);
            log.info("<<<<<<<<<<show tables size {}", tables.size());
            log.info("<<<<<<<<<<show tables result {}", tables);
        } catch (MetaException e) {
            throw new RuntimeException(e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

}
