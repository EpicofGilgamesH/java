package rabbit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Date 2020-12-04 16:44
 * @Created by wangjie
 */
public class Compare {

	public static void main(String[] args) {

		String a = "WAYBILL_NO" +
				",PRINTS_NUMBER" +
				",PICK_NETWORK_NAME" +
				",DELIVERY_TIME" +
				",SENDER_NAME" +
				",SENDER_MOBILE_PHONE" +
				",SENDER_TELPHONE" +
				",SENDER_POSTAL_CODE" +
				",SENDER_COUNTRY_NAME" +
				",SENDER_PROVINCE_NAME" +
				",SENDER_CITY_NAME" +
				",SENDER_AREA_NAME" +
				",SENDER_TOWNSHIP" +
				",SENDER_STREET" +
				",SENDER_DETAILED_ADDRESS" +
				",RECEIVER_NAME" +
				",RECEIVER_TELPHONE" +
				",RECEIVER_MOBILE_PHONE" +
				",RECEIVER_POSTAL_CODE" +
				",RECEIVER_COUNTRY_NAME" +
				",RECEIVER_PROVINCE_NAME" +
				",RECEIVER_CITY_NAME" +
				",RECEIVER_AREA_NAME" +
				",RECEIVER_TOWNSHIP" +
				",RECEIVER_STREET" +
				",RECEIVER_DETAILED_ADDRESS" +
				",PACKAGE_NUMBER" +
				",PACKAGE_CHARGE_WEIGHT" +
				",SETTLEMENT_CODE" +
				",SETTLEMENT_NAME" +
				",COD_NEED" +
				",COD_MONEY" +
				",COD_FEE" +
				",IS_COD_RECEIVE" +
				",INSURED" +
				",INSURED_AMOUNT" +
				",INSURED_FEE" +
				",FREIGHT" +
				",HANDICRAFT_FEE" +
				",OTHER_FEE" +
				",AFTER_TAX_FREIGHT" +
				",TOTAL_FREIGHT" +
				",REMARKS" +
				",WAYBILL_SOURCE_CODE" +
				",WAYBILL_SOURCE_NAME" +
				",IS_REFUND" +
				",IS_SIGN" +
				",PAID_MODE_CODE" +
				",PAID_MODE_NAME" +
				",RECEIVE_PAY_FEE" +
				",RECEIVER_SORTING_CODE" +
				",ORDER_SOURCE_CODE" +
				",TERMINAL_DISPATCH_CODE" +
				",PACKAGE_COLLECT_WEIGHT" +
				",GOODS_TYPE_ID" +
				",GOODS_TYPE_CODE" +
				",GOODS_TYPE_NAME" +
				",COLLECT_TIME" +
				",GOODS_NAME" +
				",IS_RECEIVE" +
				",PACKAGE_COST" +
				",IS_NEED_RECEIPT" +
				",RECEIPT_NO" +
				",IS_BUSINESS" +
				",PRINTER_COUNTERFOIL";

		String b = "id" +
				",waybillNo" +
				",printsNumber" +
				",pickNetworkName" +
				",deliveryTime" +
				",senderName" +
				",senderMobilePhone" +
				",senderTelphone" +
				",senderPostalCode" +
				",senderCountryName" +
				",senderProvinceName" +
				",senderCityName" +
				",senderAreaName" +
				",senderTownship" +
				",senderStreet" +
				",senderDetailedAddress" +
				",receiverName" +
				",receiverMobilePhone" +
				",receiverTelphone" +
				",receiverPostalCode" +
				",receiverCountryName" +
				",receiverProvinceName" +
				",receiverCityName" +
				",receiverAreaName" +
				",receiverTownship" +
				",receiverStreet" +
				",receiverDetailedAddress" +
				",packageNumber" +
				",packageChargeWeight" +
				",settlementCode" +
				",settlementName" +
				",codNeed" +
				",codMoney" +
				",codFee" +
				",isCodReceive" +
				",insured" +
				",insuredAmount" +
				",insuredFee" +
				",freight" +
				",handicraftFee" +
				",otherFee" +
				",afterTaxFreight" +
				",totalFreight" +
				",remarks" +
				",waybillSourceCode" +
				",waybillSourceName" +
				",senderFullAddress" +
				",receiverFullAddress" +
				",isRefund" +
				",isSign" +
				",paidModeCode" +
				",paidModeName" +
				",receivePayFee" +
				",receiverSortingCode" +
				",orderSourceCode" +
				",terminalDispatchCode" +
				",isBusiness" +
				",packageCollectWeight" +
				",goodsTypeId" +
				",goodsTypeCode" +
				",goodsTypeName" +
				",collectTime" +
				",printerCounterfoil" +
				",goodsName" +
				",isReceive" +
				",packageCost" +
				",receiptNo" +
				",quoteTypeCode" +
				",isPrivacy";

		List<String> list1 = Arrays.asList(a.split(","));
		List<String> list2 = Arrays.asList(b.split(","));
		list2 = list2.stream().map(x -> transToCamel(x)).collect(Collectors.toList());

		List<String> finalList1 = list2;
		List<String> collect1 = list1.stream().filter(x -> !finalList1.contains(x)).collect(Collectors.toList());

		List<String> collect2 = list2.stream().filter(x -> !list1.contains(x)).collect(Collectors.toList());
		printList(collect1);
		System.out.println("-------------------------------------------------------");
		printList(collect2);

		String tel = "15220167300，15220167301，152201";
		int length = tel.length();
		System.out.println("length:" + length);

		//取交集
	}

	private static final String UNDERLINE = "_";

	/**
	 * 将pojo类字段名转换为db中的驼峰名
	 *
	 * @param str
	 * @return
	 */
	public static String transToCamel(String str) {
		StringBuilder sb = new StringBuilder(str);
		int temp = 0;//定位
		if (!str.contains(UNDERLINE)) {
			for (int i = 0; i < str.length(); i++) {
				if (Character.isUpperCase(str.charAt(i))) {
					sb.insert(i + temp, UNDERLINE);
					temp += 1;
				}
			}
		}
		return sb.toString().toUpperCase();
	}

	public static void printList(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
