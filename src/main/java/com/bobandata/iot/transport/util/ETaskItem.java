package com.bobandata.iot.transport.util;

public class ETaskItem {
	public static final int UNKNOWN = 0;
	// 增量
	public static final int PROFILE = 1;
	// 底码
	public static final  int VIEW = 2;
	// 瞬时量
	public static final int INSTANTANEOUS = 3;
	// 四象限无功增量
	public static final int RP_PROFILE = 4;
	// 四象限无功底码
	public static final int RP_VIEW = 5;
	// 费率
	public static final int TARIFF = 6;
	// 需量
	public static final int DEMAND = 8;
	//日需量
	public static final int DAY_DEMAND = 19;
	//事件
	public static final int EVENT = 10;
	//读时间
	public static final int READ_CLOCK = 11;
	//时间同步
	public static final int SYNCH_CLOCK = 12;

}
