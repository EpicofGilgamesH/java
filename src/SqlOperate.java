import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Date 2021-05-08 15:05
 * @Created by wangjie
 */
public class SqlOperate {

	public static void main(String[] args) {
		String insert = "insert into TAB_REBACK_TRANSFER_EXPRESS (WAYBILL_NO, TRANSFER_WAYBILL_NO, APPLY_TYPE_CODE, APPLY_TYPE_NAME, EXPRESS_TYPE_ID, EXPRESS_TYPE_CODE, EXPRESS_TYPE_NAME, PACKAGE_CHARGE_WEIGHT, TOTAL_FREIGHT, SETTLEMENT_CODE, SETTLEMENT_NAME, NEW_PACKAGE_CHARGE_WEIGHT, NEW_TOTAL_FREIGHT, NEW_SETTLEMENT_ID, NEW_SETTLEMENT_CODE, NEW_SETTLEMENT_NAME, SENDER_NAME, SENDER_MOBILE_PHONE, SENDER_TELPHONE, SENDER_PROVINCE_ID, SENDER_PROVINCE_NAME, SENDER_CITY_ID, SENDER_CITY_NAME, SENDER_AREA_ID, SENDER_AREA_NAME, SENDER_TOWNSHIP, SENDER_STREET, SENDER_DETAILED_ADDRESS, RECEIVER_NAME, RECEIVER_MOBILE_PHONE, RECEIVER_TELPHONE, RECEIVER_PROVINCE_ID, RECEIVER_PROVINCE_NAME, RECEIVER_CITY_ID, RECEIVER_CITY_NAME, RECEIVER_AREA_ID, RECEIVER_AREA_NAME, RECEIVER_TOWNSHIP, RECEIVER_STREET, RECEIVER_DETAILED_ADDRESS, NEW_SENDER_NAME, NEW_SENDER_MOBILE_PHONE, NEW_SENDER_TELPHONE, NEW_SENDER_PROVINCE_ID, NEW_SENDER_PROVINCE_NAME, NEW_SENDER_CITY_ID, NEW_SENDER_CITY_NAME, NEW_SENDER_AREAID, NEW_SENDER_AREANAME, NEW_SENDER_TOWNSHIP, NEW_SENDER_STREET, NEW_SENDER_DETAILED_ADDRESS, NEW_RECEIVER_NAME, NEW_RECEIVER_MOBILE_PHONE, NEW_RECEIVER_TELPHONE, NEW_RECEIVER_PROVINCE_ID, NEW_RECEIVER_PROVINCE_NAME, NEW_RECEIVER_CITY_ID, NEW_RECEIVER_CITY_NAME, NEW_RECEIVER_AREA_ID, NEW_RECEIVER_AREANAME, NEW_RECEIVER_TOWNSHIP, NEWRECEIVER_STREET, NEWRECEIVER_DETAILED_ADDRESS, ABNORMAL_REG_NETWORK_ID, ABNORMAL_REG_NETWORK_NAME, APPLY_NETWORK_ID, APPLY_NETWORK_CODE, APPLY_NETWORK_NAME, APPLY_STAFF_CODE, APPLY_STAFF_NAME, APPLY_SOURCE, APPLY_SOURCE_NAME, EXAMINE_STAFF_NAME, EXAMINE_STAFF_CODE, EXAMINE_NETWORK_ID, EXAMINE_NETWORK_NAME, EXAMINE_TIME, APPLY_TIME, STATUS, STATUS_NAME, REBACK_TRANSFER_NETWORK_ID, REBACK_TRANSFER_NETWORK_CODE, REBACK_TRANSFER_NETWORK_NAME, SENDER_NETWORK_NAME, SENDER_NETWORK_CODE, SENDER_NETWORK_ID, PRINT_FLAG, PRINT_COUNT, REVOKE_CODE,REVOKE_NAME, REVOKE_TIME, REVOKE_NETWORK_NAME, REVOKE_NETWORK_CODE, REVOKE_NETWORK_ID, REJECT_NAME, REJECT_TIME, REASON, REJECT_NETWORK_NAME, REJECT_NETWORK_CODE, REJECT_NETWORK_ID, ENTRY_TYPE, TRANSFER_REASON, REBACK_REASON, REBACK_TRANSFER_REASON, DISPATCH_CODE, DISPATCH_NAME, GOODS_TYPE_ID, GOODS_TYPE_CODE, GOODS_TYPE_NAME, PARCEL_NUMBER, PACKAGE_LENGTH, PACKAGE_WIDE, PACKAGE_HIGH, BOX_NUMBER, COD_NEED, COD_MONEY, INSURED, INSURED_AMOUNT,PRINT_TIME, PRINT_NETWORK_NAME, PRINT_NETWORK_CODE, PRINT_NETWORK_ID, PRINT_USER_NAME, PRINT_USER_CODE, PRINT_USER_ID, PICK_FINANCE_NAME, PICK_FINANCE_CODE, PICK_FINANCE_ID, ORDER_SOURCE_CODE,CUSTOMER_ORDER_ID,SEND_CODE,SEND_NAME, ORIGIN_ID,ORIGIN_CODE,ORIGIN_NAME, DESTINATION_ID,DESTINATION_CODE,DESTINATION_NAME, IS_REAL_NAME,REAL_NAME,ID_NO_TYPE,ID_NO,SEX, CUSTOMER_ID,CUSTOMER_CODE,CUSTOMER_NAME, PAID_MODE_ID,PAID_MODE_CODE,PAID_MODE_NAME,NEW_INSURED,NEW_INSURED_AMOUNT, IS_NEED_RECEIPT,APPLY_NETWORK_TYPE_ID,TERMINAL_DISPATCH_CODE ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";

		String param = "JD0039592161(String), null, 4(Integer), 退回(String), null, EZ(String), EZ(String), 25(BigDecimal), 250000(BigDecimal), PP_CASH(String), PP_CASH(String), 25(BigDecimal), 250000(BigDecimal), null, PP_CASH(String), PP_CASH(String), null, null, null, null, DKI JAKARTA(String), null, JAKARTA(String), null, (String), (String), (String), null, null, null, null, null, DKI JAKARTA(String), null, JAKARTA(String), null, (String), (String), (String), null, null, 13685260233(String), null, 4(Integer), NANGGROE ACEH DARUSSALAM(String), 9(Integer), BANDA ACEH(String), 13(Integer), MEURAKSA(String), (String), (String), null, null, 13688556622(String), null, 4(Integer), NANGGROE ACEH DARUSSALAM(String), 9(Integer), BANDA ACEH(String), 14(Integer), JAYA BARU(String), (String), (String), null, null, null, 22(Integer), 001188(String), HQ(String), zhanglong(String), 张龙(String), 1(Integer), 人工录入(String), null, null, null, null, null, 2021-05-13 14:45:56.754(Timestamp), 1(Integer), 待审核(String), 22(Integer), 001188(String), HQ(String), HQ(String), 001188(String), 22(Integer), 0(Integer), 0(Integer), null, null, null, null, null, null, null, null, null, null, null, null, 1(Integer), null, 123(String), 123(String), null, null, null, bm000002(String), BARANG(String), 1(Integer), null, null, null, 0(Integer), 0(Integer), 0(BigDecimal), 0(Integer), 0(BigDecimal), null, null, null, null, null, null, null, HQ(String), 888888(String), 22(Integer), null, null, 1(String), 上门寄件(String), null, null, null, null, null, null, 0(Integer), null, 0(Integer), null, null, 636032(Integer), IJOLIJOL(String), IJOLIJOL(String), null, 2(String), TUNAI(String), 0(Integer), null, 0(Integer), 22(Integer), null";

		String regex = "\\?";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(insert);

		String[] split = param.split(",");
		for (int i = 0; i < split.length; i++) {
			int index = split[i].indexOf("(");
			if (index != -1) {
				if (split[i].contains("String") || split[i].contains("Timestamp")) {
					split[i] = "'" + split[i].substring(0, index).trim() + "'";
				} else {
					split[i] = split[i].substring(0, index);
				}
			}
		}

		int i = 0;
		while (matcher.find()) {
			insert = insert.replaceFirst("\\?", split[i]);
			i++;
		}
		System.out.println(insert);
	}
}
