### a util for Shang

基于本地配置文件mysql.conf
在其中配置url、username、password、tableName参数，

jar包启动后将载入同目录下mysql.conf的配置，并读取同目录下的.csv文件，存入数据库中

创建数据表

    CREATE TABLE `stock_test` (
      `code` varchar(100) DEFAULT NULL,
      `date` date DEFAULT NULL,
      `open` decimal(20,10) DEFAULT NULL,
      `high` decimal(20,10) DEFAULT NULL,
      `low` decimal(20,10) DEFAULT NULL,
      `close` decimal(20,10) DEFAULT NULL,
      `change` decimal(20,10) DEFAULT NULL,
      `volume` bigint(20) DEFAULT NULL,
      `money` bigint(20) DEFAULT NULL,
      `traded_market_value` bigint(20) DEFAULT NULL,
      `market_value` bigint(20) DEFAULT NULL,
      `turnover` decimal(20,10) DEFAULT NULL,
      `adjust_price` decimal(20,10) DEFAULT NULL,
      `report_type` varchar(100) DEFAULT NULL,
      `report_date` varchar(100) DEFAULT NULL,
      `PE_TTM` varchar(100) DEFAULT NULL,
      `PS_TTM` varchar(100) DEFAULT NULL,
      `PC_TTM` varchar(100) DEFAULT NULL,
      `PB` varchar(100) DEFAULT NULL,
      `adjust_price_f` decimal(20,10) DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8

计算试用的sql语句

    select * from (select 'code','date','open','high','low','close','change','volume','money','traded_market_value','market_value','turnover','adjust_price','report_type','report_date','PE_TTM','PS_TTM','PC_TTM','PB','adjust_price_f' union select * from stock.stock_test where `change`>0.03 and date like '%-09-17') as stock_tmp into outfile 'D:\\test.xls';