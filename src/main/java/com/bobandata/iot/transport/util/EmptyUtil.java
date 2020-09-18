 package com.bobandata.iot.transport.util;

 import java.util.List;

 /**
  * @Author: lizhipeng
  * @Description: 判断一个对象是否包含内容
  * @Company: 上海博般数据技术有限公司
  * @Date: Created in 17:52 2018/11/19.
  */
 public class EmptyUtil
 {
   public static boolean isEmpty(Object obj)
   {
     if (obj == null)
     {
       return true;
     }
     if ((obj instanceof List))
     {
       return ((List)obj).size() == 0;
     }
     if ((obj instanceof String))
     {
       return ((String)obj).trim().equals("");
     }
     return false;
   }

   public static boolean isNotEmpty(Object obj)
   {
    return !isEmpty(obj);
   }

     /**
      * 判断当前系统是不是Linux
      * @return
      */
     public static boolean isLinux() {
         return System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;
     }
 }