package com.bobandata.iot.transport.util;

import io.netty.buffer.ByteBuf;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    private static SimpleDateFormat getDateFormat(String parttern)
            throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    private static int getInteger(Date date, int dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }

    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }
        return dateString;
    }

    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0L;
        Map map = new HashMap();
        List absoluteValues = new ArrayList();

        if ((timestamps != null) && (timestamps.size() > 0)) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(((Long) timestamps.get(i)).longValue() -
                                ((Long) timestamps
                                        .get(j))
                                        .longValue());
                        absoluteValues.add(Long.valueOf(absoluteValue));

                        long[] timestampTmp = {((Long) timestamps.get(i)).longValue(),
                                ((Long) timestamps
                                        .get(j))
                                        .longValue()};
                        map.put(Long.valueOf(absoluteValue), timestampTmp);
                    }

                }

                long minAbsoluteValue = -1L;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = ((Long) absoluteValues.get(0)).longValue();
                }
                for (int i = 0; i < absoluteValues.size(); i++) {
                    for (int j = i + 1; j < absoluteValues.size(); j++) {
                        if (((Long) absoluteValues.get(i)).longValue() > ((Long) absoluteValues.get(j)).longValue())
                            minAbsoluteValue = ((Long) absoluteValues.get(j)).longValue();
                        else {
                            minAbsoluteValue = ((Long) absoluteValues.get(i)).longValue();
                        }
                    }
                }

                if (minAbsoluteValue != -1L) {
                    long[] timestampsLastTmp = (long[]) map.get(Long.valueOf(minAbsoluteValue));
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                    } else if (absoluteValues.size() == 1) {
                        long dateOne = timestampsLastTmp[0];
                        long dateTwo = timestampsLastTmp[1];
                        if (Math.abs(dateOne - dateTwo) < 100000000000L) {
                            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                        } else {
                            long now = new Date().getTime();
                            if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
                                timestamp = dateOne;
                            } else timestamp = dateTwo;
                        }
                    }
                }
            } else {
                timestamp = ((Long) timestamps.get(0)).longValue();
            }
        }

        if (timestamp != 0L) {
            date = new Date(timestamp);
        }
        return date;
    }

    public static boolean isDate(String date) {
        boolean isDate = false;
        if ((date != null) &&
                (StringToDate(date) != null)) {
            isDate = true;
        }

        return isDate;
    }

    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map map = new HashMap();
        List timestamps = new ArrayList();
        for (DateStyle style : DateStyle.values()) {
            Date dateTmp = StringToDate(date, style.getValue());
            if (dateTmp != null) {
                timestamps.add(Long.valueOf(dateTmp.getTime()));
                map.put(Long.valueOf(dateTmp.getTime()), style);
            }
        }
        dateStyle = (DateStyle) map.get(Long.valueOf(getAccurateDate(timestamps).getTime()));
        return dateStyle;
    }

    public static Date StringToDate(String date) {
        DateStyle dateStyle = null;
        return StringToDate(date, dateStyle);
    }

    public static Date StringToDate(String date, String parttern) {
        Date myDate = null;
        if (date != null)
            try {
                myDate = getDateFormat(parttern).parse(date);
            } catch (Exception localException) {
            }
        return myDate;
    }

    public static Date StringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle == null) {
            List timestamps = new ArrayList();
            for (DateStyle style : DateStyle.values()) {
                Date dateTmp = StringToDate(date, style.getValue());
                if (dateTmp != null) {
                    timestamps.add(Long.valueOf(dateTmp.getTime()));
                }
            }
            myDate = getAccurateDate(timestamps);
        } else {
            myDate = StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    public static String DateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null)
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception localException) {
            }
        return dateString;
    }

    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    public static String StringToString(String date, String parttern) {
        return StringToString(date, null, parttern);
    }

    public static String StringToString(String date, DateStyle dateStyle) {
        return StringToString(date, null, dateStyle);
    }

    public static String StringToString(String date, String olddParttern, String newParttern) {
        String dateString = null;
        if (olddParttern == null) {
            DateStyle style = getDateStyle(date);
            if (style != null) {
                Date myDate = StringToDate(date, style.getValue());
                dateString = DateToString(myDate, newParttern);
            }
        } else {
            Date myDate = StringToDate(date, olddParttern);
            dateString = DateToString(myDate, newParttern);
        }
        return dateString;
    }

    public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle == null) {
            DateStyle style = getDateStyle(date);
            dateString = StringToString(date, style.getValue(), newDateStyle
                    .getValue());
        } else {
            dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle
                    .getValue());
        }
        return dateString;
    }

    public static String addYear(String date, int yearAmount) {
        return addInteger(date, 1, yearAmount);
    }

    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, 1, yearAmount);
    }

    public static String addMonth(String date, int yearAmount) {
        return addInteger(date, 2, yearAmount);
    }

    public static Date addMonth(Date date, int yearAmount) {
        return addInteger(date, 2, yearAmount);
    }

    public static String addDay(String date, int dayAmount) {
        return addInteger(date, 5, dayAmount);
    }

    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, 5, dayAmount);
    }

    public static String addHour(String date, int hourAmount) {
        return addInteger(date, 11, hourAmount);
    }

    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, 11, hourAmount);
    }

    public static String addMinute(String date, int hourAmount) {
        return addInteger(date, 12, hourAmount);
    }

    public static Date addMinute(Date date, int hourAmount) {
        return addInteger(date, 12, hourAmount);
    }

    public static String addSecond(String date, int hourAmount) {
        return addInteger(date, 13, hourAmount);
    }

    public static Date addSecond(Date date, int hourAmount) {
        return addInteger(date, 13, hourAmount);
    }

    public static int getYear(String date) {
        return getYear(StringToDate(date));
    }

    public static int getYear(Date date) {
        return getInteger(date, 1);
    }

    public static int getMonth(String date) {
        return getMonth(StringToDate(date));
    }

    public static int getMonth(Date date) {
        return getInteger(date, 2);
    }

    public static int getDay(String date) {
        return getDay(StringToDate(date));
    }

    public static int getDay(Date date) {
        return getInteger(date, 5);
    }

    public static int getHour(String date) {
        return getHour(StringToDate(date));
    }

    public static int getHour(Date date) {
        return getInteger(date, 11);
    }

    public static int getMinute(String date) {
        return getMinute(StringToDate(date));
    }

    public static int getMinute(Date date) {
        return getInteger(date, 12);
    }

    public static int getSecond(String date) {
        return getSecond(StringToDate(date));
    }

    public static int getSecond(Date date) {
        return getInteger(date, 13);
    }

    public static String getDate(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD);
    }

    public static Date getCurDate(DateStyle dateStyle)
            throws Exception {
        SimpleDateFormat dateFormat = getDateFormat(dateStyle.getValue());
        String curDate = dateFormat.format(new Date());
        return dateFormat.parse(curDate);
    }

    public static String getCurDateStr(DateStyle dateStyle) {
        SimpleDateFormat dateFormat = getDateFormat(dateStyle.getValue());
        return dateFormat.format(new Date());
    }

    public static String getDate(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }

    public static String getTime(String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }

    public static String getTime(Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }

    public static Week getWeek(String date) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }

    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(7) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
        }

        return week;
    }

    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));
    }

    public static int getIntervalDays(Date date, Date otherDate) {
        date = StringToDate(getDate(date));
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) (time / 86400000L);
    }

    public static int getIntervalMinute(Date date, Date otherDate) {
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) time / 60000;
    }

    public static int getIntervalSecond(Date date, Date otherDate) {
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) time / 1000;
    }

    public static int getIntervalMilSecond(Date date, Date otherDate) {
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) time;
    }

    public static int getdaysOfCurMonth() {
        Calendar c = Calendar.getInstance();
        return c.getActualMaximum(5);
    }

    public static double getIntervalHours(String startDate, String endDate, DateStyle dateStyle) {
        long startTime = 0L;
        long endTime = 0L;
        startTime = StringToDate(startDate, dateStyle).getTime();
        endTime = StringToDate(endDate, dateStyle).getTime();
        return Math.abs(endTime - startTime) / 3600000.0D;
    }

    public static int getIntervalMinutes(String startDate, String endDate, DateStyle dateStyle) {
        long startTime = 0L;
        long endTime = 0L;
        startTime = StringToDate(startDate, dateStyle).getTime();
        endTime = StringToDate(endDate, dateStyle).getTime();
        return (int) Math.abs(endTime - startTime) / 60000;
    }

    public static String stringToDateStr(String rawTime, DateStyle yyyyMmDdHhMm) {
        String year = rawTime.substring(0, 4);
        String month = rawTime.substring(4, 6);
        String day = rawTime.substring(6, 8);
        String hour = rawTime.substring(8, 10);
        String minutes = rawTime.substring(10, 12);
        String seconds = rawTime.substring(12, 14);
        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
    }

    public static boolean compareTime(String startTime, String endTime, DateStyle dateStyle)
            throws ParseException {
        return StringToDate(endTime, dateStyle).getTime() > StringToDate(startTime, dateStyle).getTime();
    }

    public static Long getTimeIntervalMinutes(String startDate, String endDate, DateStyle dateStyle) {
        long startTime = 0L;
        long endTime = 0L;
        startTime = StringToDate(startDate, dateStyle).getTime();
        endTime = StringToDate(endDate, dateStyle).getTime();
        return Long.valueOf(Math.abs(endTime - startTime) / 60000L);
    }

    public static Date getShortDateByBuffer(ByteBuf buffer) {
        byte[] bytes = new byte[1];
        buffer.readBytes(bytes);
        int day = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        buffer.readBytes(bytes);
        int month = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        buffer.readBytes(bytes);
        int year = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        Date date = StringToDate("20" + year + "-" + month + "-" + day, DateStyle.YYYY_MM_DD);
        return date;
    }

    public static Date getStandardDateByBuffer(ByteBuf buffer) {
        byte by = buffer.readByte();
        String dateSrt = Integer.toHexString(by&0xFF);
        int month = Integer.parseInt(dateSrt.substring(0,1), 16);
        int year = Integer.parseInt(dateSrt.substring(1,2), 16);
        Date date = StringToDate("20" + year + "-" + month, DateStyle.YYYY_MM);
        return date;
    }

    public static Date getDateByBuffer(ByteBuf buffer) {
        byte[] bytes = new byte[1];
        buffer.readBytes(bytes);
        int min = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        buffer.readBytes(bytes);
        int hour = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        buffer.readBytes(bytes);
        int day = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        buffer.readBytes(bytes);
        int month = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        buffer.readBytes(bytes);
        int year = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        Date date = StringToDate("20" + year + "-" + month + "-" + day + " " + hour + ":" + min, DateStyle.YYYY_MM_DD_HH_MM);
        return date;
    }

    public static Date getTimestampByBuffer(ByteBuf buffer) {
        byte[] bytes = new byte[1];
        buffer.readBytes(bytes);
        int msec = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16);
        buffer.readBytes(bytes);
        int sec = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16)>>2;
        buffer.readBytes(bytes);
        int min = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16)&0x3F;
        buffer.readBytes(bytes);
        int hour = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16)&0x1F;
        buffer.readBytes(bytes);
        int day = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16)&0x1F;
        buffer.readBytes(bytes);
        int month = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16)&0x0F;
        buffer.readBytes(bytes);
        int year = Integer.parseInt(HexUtils.encodeHexStr(bytes), 16)&0x7F;
        Date date = StringToDate("20" + year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec + " " + msec, DateStyle.YYYY_MM_DD_HH_MM_SS_S);
        return date;
    }

    public static void getBufferByDateShort(ByteBuf buffer, Date date) {
        String dateStr = DateToString(date, DateStyle.YYYY_MM_DD);
        int day = Integer.parseInt(dateStr.substring(8, 10));
        buffer.writeByte(HexUtils.intToByte(day));
        int month = Integer.parseInt(dateStr.substring(5, 7));
        buffer.writeByte(HexUtils.intToByte(month));
        int year = Integer.parseInt(dateStr.substring(2, 4));
        buffer.writeByte(HexUtils.intToByte(year));
    }

    public static void getBufferByStandardDate(ByteBuf buffer, Date date) {
        String dateStr = DateToString(date, DateStyle.YYYY_MM);
        String yearStr = dateStr.substring(3, 4);
        String monthStr =dateStr.substring(5, 7);
        int monthInt = Integer.parseInt(monthStr);
        monthStr = Integer.toHexString(monthInt);
        dateStr=yearStr+monthStr;
        int dateInt = Integer.parseInt(dateStr,16);
        buffer.writeByte(HexUtils.intToByte(dateInt));
    }

    public static void getBufferByDate(ByteBuf buffer, Date date) {
        String dateStr = DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
        int min = Integer.parseInt(dateStr.substring(14, 16));
        buffer.writeByte(HexUtils.intToByte(min));
        int hour = Integer.parseInt(dateStr.substring(11, 13));
        buffer.writeByte(HexUtils.intToByte(hour));
        int day = Integer.parseInt(dateStr.substring(8, 10));
        buffer.writeByte(HexUtils.intToByte(day));
        int month = Integer.parseInt(dateStr.substring(5, 7));
        buffer.writeByte(HexUtils.intToByte(month));
        int year = Integer.parseInt(dateStr.substring(2, 4));
        buffer.writeByte(HexUtils.intToByte(year));
    }

    public static void getBufferByTimestamp(ByteBuf buffer, Date date) {
        String dateStr = DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS_S);
        int msec = Integer.parseInt(dateStr.substring(20, 21));
        buffer.writeByte(HexUtils.intToByte(msec));
        int sec = Integer.parseInt(dateStr.substring(17, 19))<<2;
        buffer.writeByte(HexUtils.intToByte(sec));
        int min = Integer.parseInt(dateStr.substring(14, 16));
        buffer.writeByte(HexUtils.intToByte(min));
        int hour = Integer.parseInt(dateStr.substring(11, 13));
        buffer.writeByte(HexUtils.intToByte(hour));
        int day = Integer.parseInt(dateStr.substring(8, 10));
        buffer.writeByte(HexUtils.intToByte(day));
        int month = Integer.parseInt(dateStr.substring(5, 7));
        buffer.writeByte(HexUtils.intToByte(month));
        int year = Integer.parseInt(dateStr.substring(2, 4));
        buffer.writeByte(HexUtils.intToByte(year));
    }
}