//package me.stephenj.sqlope.common.utils;
//
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.ResultSetMetaData;
//import com.mysql.jdbc.Statement;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Date;
//
//public class DbExport {
//    public void backupDB() {
//        Connection conn = null;
//        String dbName = null;
//        try {
//            conn = jdbcDao.getDataSource().getConnection();
//            dbName = conn.getCatalog();
//        } catch (SQLException e) {
//            throw new RuntimeException("无法获取数据库连接！");
//        }
//        String tableName = null,
//                procName = null;
//        BufferedWriter writer = null;
//        Statement stmtInfo = null, stmtData = null;
//        ResultSet rsInfo = null, rsData = null;
//        try {
//            //存放文件目录
//            String folderBackup = CommonUtils.absoluteClassPathUsrPath(Constants.FOLDER_BACKUP);
//            //文件名
//            String sqlFilename = String.format("%s-%s.sql", dbName, Dates.dateToString(new Date(), Constants.DATETIME_FORMAT_FILENAME));
//            writer = new BufferedWriter(new FileWriter(folderBackup + Constants.PATH_SEPARATOR + sqlFilename));
//
//            /*
//             * 头内容
//             */
//            writer.write("/*!40101 SET NAMES utf8 */;");
//            writer.newLine();
//            writer.write("/*!40101 SET SQL_MODE=''*/;");
//            writer.newLine();
//            writer.write("/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;");
//            writer.newLine();
//            writer.write("/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;");
//            writer.newLine();
//            writer.write("/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;");
//            writer.newLine();
//            writer.write("/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;");
//            writer.flush();
//
//            /*
//             * 导出表数据
//             */
//            stmtInfo = (Statement) conn.createStatement();
//            rsInfo = stmtInfo.executeQuery(String.format("SHOW FULL TABLES FROM `%s` WHERE TABLE_TYPE = 'BASE TABLE'", dbName));
//            // 遍历所有表
//            while(rsInfo.next()) {
//                tableName = rsInfo.getString(1);
//                this.dbBackExportTable(conn, tableName, writer, true);
//            }    //end for tables
//            if(null != rsInfo) {
//                rsInfo.close();
//            }
//            if(null != stmtInfo) {
//                stmtInfo.close();
//            }
//
//            /*
//             * 导出存储过程信息
//             */
//            stmtInfo = (Statement) conn.createStatement();
//            rsInfo = stmtInfo.executeQuery(String.format("SELECT `SPECIFIC_NAME` from `INFORMATION_SCHEMA`.`ROUTINES` WHERE `ROUTINE_SCHEMA` = '%s' AND ROUTINE_TYPE = 'PROCEDURE'; ", dbName));
//            while (rsInfo.next()) {
//                procName = rsInfo.getString(1);
//
//                /* 存储过程结构 */
//                stmtData = (Statement) conn.createStatement();
//                rsData = stmtData.executeQuery(String.format("SHOW CREATE PROCEDURE `%s`", procName));
//                if(!rsData.next()) {
//                    continue ;
//                }
//                writer.newLine();
//                writer.newLine();
//                writer.write(String.format("/* Procedure structure for procedure `%s` */", procName));
//                writer.newLine();
//                writer.write(String.format("/*!50003 DROP PROCEDURE IF EXISTS  `%s` */;", procName));
//                writer.newLine();
//                writer.write("DELIMITER $$");
//                writer.newLine();
//                writer.append("/*!50003 ").append(rsData.getString(3)).append(" */$$");
//                writer.newLine();
//                writer.write("DELIMITER ;");
//                if(null != rsData) {
//                    rsData.close();
//                }
//                if(null != stmtData) {
//                    stmtData.close();
//                }
//            }
//
//            /*
//             * 尾内容
//             */
//            writer.newLine();
//            writer.newLine();
//            writer.write("/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;");
//            writer.newLine();
//            writer.write("/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;");
//            writer.newLine();
//            writer.write("/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;");
//            writer.newLine();
//            writer.write("/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;");
//            writer.newLine();
//
//            writer.close();
//            if(null != stmtData) {
//                stmtData.close();
//            }
//            if(null != rsData) {
//                rsData.close();
//            }
//            if(null != stmtInfo) {
//                stmtInfo.close();
//            }
//            if(null != rsInfo) {
//                rsInfo.close();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    private void dbBackExportTable(Connection conn, String tableName, BufferedWriter writer, boolean bulkFlag) throws SQLException, IOException {
//        Statement stmt = null;
//        ResultSet rs = null;
//        /* 表结构 */
//        stmt = (Statement) conn.createStatement();
//        rs = stmt.executeQuery(String.format("SHOW CREATE TABLE `%s`", tableName));
//        if(!rs.next()) {
//            return ;
//        }
//        writer.newLine();
//        writer.newLine();
//        writer.write(String.format("/*Table structure for table `%s` */", tableName));
//        writer.newLine();
//        writer.write(String.format("DROP TABLE IF EXISTS `%s`;", tableName));
//        writer.newLine();
//        writer.write(rs.getString(2) + ";");
//        writer.newLine();
//        if(null != rs) {
//            rs.close();
//        }
//        if(null != stmt) {
//            stmt.close();
//        }
//
//        /* 导出表数据 */
//        // 先获取记录数
//        stmt = (Statement) conn.createStatement();
//        rs = stmt.executeQuery(String.format("SELECT COUNT(1) FROM `%s`", tableName));
//        int rowCount = rs.next() ? rs.getInt(1) : 0;
//        if(0 >= rowCount) {
//            writer.flush();
//            return ;
//        }
//        writer.write(String.format("/*Data for the table `%s` */", tableName));
//        writer.newLine();
//
//        stmt = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//        stmt.setFetchSize(Integer.MIN_VALUE);
//        stmt.setFetchDirection(ResultSet.FETCH_REVERSE);
//        rs = stmt.executeQuery(String.format("SELECT * FROM `%s`", tableName));
//        int colCount = 0;
//        Object colValue = null;
//        // 所有数据用","连接
//        if(!bulkFlag) {
//            while(rs.next()) {
//                colCount = rs.getMetaData().getColumnCount();
//
//                writer.write(String.format("INSERT INTO `%s` VALUES (", tableName));
//                // 获取表每一列数据
//                for(int j = 0; j < colCount; j ++) {
//                    if(j > 0) {
//                        writer.write(',');
//                    }
//                    colValue = rs.getObject(j + 1);
//                    if(null != colValue) {
//                        writer.write(String.format("'%s'", CommonUtils.escapeString(colValue.toString())));
//                    } else {
//                        writer.write("NULL");
//                    }
//                }    //end for one record columns
//                writer.write(");");
//                writer.newLine();
//                writer.flush();
//            }    //end for table records
//        }
//        // 每行数据独立分开
//        else {
//            ResultSetMetaData rsMetaData = null;
//            int counter = 0;
//            while(rs.next()) {
//                ++ counter;
//                rsMetaData = (ResultSetMetaData) rs.getMetaData();
//                colCount = rsMetaData.getColumnCount();
//
//                // 第一条记录，则列出列名
//                if(1 == counter) {
//                    writer.write(String.format("INSERT INTO `%s` (", tableName));
//                    for(int i = 0; i < colCount; i ++) {
//                        if(i > 0) {
//                            writer.write(",");
//                        }
//                        writer.append('`').append(rsMetaData.getColumnName(i + 1)).append('`');
//                    }
//                    writer.append(") VALUES ");
//                }
//                // 获取表每一列数据
//                for(int j = 0; j < colCount; j ++) {
//                    writer.write((0 >= j) ? '(' : ',');
//                    colValue = rs.getObject(j + 1);
//                    if(null != colValue) {
//                        writer.write(String.format("'%s'", CommonUtils.escapeString(colValue.toString())));
//                    } else {
//                        writer.write("NULL");
//                    }
//                }    //end for one record columns
//                // 是否是最后记录
//                if(rowCount > counter) {
//                    writer.write("),");
//                } else {
//                    writer.write(");");
//                }
//                writer.flush();
//            }    //end for table records
//        }
//
//        if(null != rs) {
//            rs.close();
//        }
//        if(null != stmt) {
//            stmt.close();
//        }
//    }
//}
