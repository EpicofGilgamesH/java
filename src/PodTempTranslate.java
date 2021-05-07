import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * @Description TODO
 * @Date 2021-04-08 11:06
 * @Created by wangjie
 */
public class PodTempTranslate {

	public static void main(String[] args) {


		final String[] json = {"[{\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"到件扫描\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 92,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，重量为【$[weight]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"中心到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 90,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，重量为【$[weight]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"集货到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 91,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，重量为【$[weight]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"入仓扫描\",\n" +
				"\t\"operationType\": \"RECEIPT\",\n" +
				"\t\"scanTypeCode\": 20,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已入库，扫描人员【$[scanByCode]-$[scanByName])】，扫描人员上传的重量为【$[weight]】，长度为【$[length]】，宽度为【$[width]】，高度为【$[high]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"入库扫描\",\n" +
				"\t\"operationType\": \"PARTNER_RECEIPT\",\n" +
				"\t\"scanTypeCode\": 200,\n" +
				"\t\"template\": \"您的快件已存放至【$[remark1]】，请前往【$[remark2]】及时取件，如有疑问请联系派件员【$[staffContact]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"快件取出扫描\",\n" +
				"\t\"operationType\": \"PARTNER_DELIVERY_OUT\",\n" +
				"\t\"scanTypeCode\": 201,\n" +
				"\t\"template\": \"您的快件已从【$[remark2]】重新取出，将继续为您派件，请等待快递员与您联系，电话【$[staffContact]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"出库扫描\",\n" +
				"\t\"operationType\": \"PARTNER_SIGNING_SCAN\",\n" +
				"\t\"scanTypeCode\": 300,\n" +
				"\t\"template\": \"已签收，签收人凭取件码签收，如有疑问请联系：【$[staffContact]】，期待再次为您服务。\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"快件揽收\",\n" +
				"\t\"operationType\": \"EXPRESS_COLLECTION\",\n" +
				"\t\"scanTypeCode\": 10,\n" +
				"\t\"template\": \"【$[scanNetworkName]】的业务员【$[staffCode]-$[staffName]($[staffContact])】已取件\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"建包扫描\",\n" +
				"\t\"operationType\": \"PACK_SCAN\",\n" +
				"\t\"scanTypeCode\": 30,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已装包，扫描人员【$[scanByCode]-$[scanByName]】，包号【$[packageNumber]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"问题件扫描\",\n" +
				"\t\"operationType\": \"ABNORMAL_PIECE_SCAN\",\n" +
				"\t\"scanTypeCode\": 110,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行问题件扫描，扫描人员是【$[scanByCode]-$[scanByName]】，问题原因【$[remark1]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"出仓扫描\",\n" +
				"\t\"operationType\": \"DELIVERY_OUT\",\n" +
				"\t\"scanTypeCode\": 94,\n" +
				"\t\"template\": \"【$[scanNetworkName]】的派件员【$[staffCode]-$[staffName]($[staffContact])】正在派件，扫描人员【$[scanByCode]-$[scanByName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"代理点收入扫描\",\n" +
				"\t\"operationType\": \"COLLECTION_BY_PROXY\",\n" +
				"\t\"scanTypeCode\": 202,\n" +
				"\t\"template\": \"您的快件已存放在【$[remark2]】代理点，请及时前往取件，如有疑问请联系【$[scanByName]，$[remark3]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"装车扫描\",\n" +
				"\t\"operationType\": \"LOADING_SCAN\",\n" +
				"\t\"scanTypeCode\": 40,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已装车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[remark2]】，航班号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"留仓件入仓\",\n" +
				"\t\"operationType\": \"REMAIN_STORAGE_SCAN\",\n" +
				"\t\"scanTypeCode\": 120,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行留仓扫描，扫描人员是【$[scanByCode]-$[scanByName]】，留仓原因【$[remark1]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"留仓件入仓\",\n" +
				"\t\"operationType\": \"REMAIN_STORAGE_SCAN\",\n" +
				"\t\"scanTypeCode\": 120,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行留仓扫描，扫描人员是【$[scanByCode]-$[scanByName]】，留仓原因【$[remark1]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"发件扫描\",\n" +
				"\t\"operationType\": \"SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 50,\n" +
				"\t\"template\": \"快件在【$[scanNetworkName]】由【$[scanByCode]-$[scanByName]】扫描发往【$[nextStopName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"快件签收\",\n" +
				"\t\"operationType\": \"SIGNING_SCAN\",\n" +
				"\t\"scanTypeCode\": 100,\n" +
				"\t\"template\": \"快件已签收，签收人是【$[remark1]】，签收网点【$[scanNetworkName]】，派件员是【$[staffCode]-$[staffName]($[staffContact])】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"卸车扫描\",\n" +
				"\t\"operationType\": \"UNLOADING_SCAN\",\n" +
				"\t\"scanTypeCode\": 80,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已卸车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[remark2]】，车牌号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"拆包扫描\",\n" +
				"\t\"operationType\": \"UNPACK_SCAN\",\n" +
				"\t\"scanTypeCode\": 31,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已拆包，扫描人员【$[scanByCode]-$[scanByName]】，包号【$[packageNumber]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"到件扫描\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 92,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，重量为【$[weight]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"中心到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 90,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，重量为【$[weight]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"集货到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 91,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，重量为【$[weight]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"装车扫描\",\n" +
				"\t\"operationType\": \"LOADING_SCAN\",\n" +
				"\t\"scanTypeCode\": 40,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已装车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[remark2]】，航班号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"建包扫描\",\n" +
				"\t\"operationType\": \"PACK_SCAN\",\n" +
				"\t\"scanTypeCode\": 30,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已装包，扫描人员【$[scanByCode]-$[scanByName]】，包号【$[packageNumber]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"发件扫描\",\n" +
				"\t\"operationType\": \"SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 50,\n" +
				"\t\"template\": \"快件在【$[scanNetworkName]】由【$[scanByCode]-$[scanByName]】扫描发往【$[nextStopName]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"卸车扫描\",\n" +
				"\t\"operationType\": \"UNLOADING_SCAN\",\n" +
				"\t\"scanTypeCode\": 80,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已卸车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[remark2]】，车牌号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"拆包扫描\",\n" +
				"\t\"operationType\": \"UNPACK_SCAN\",\n" +
				"\t\"scanTypeCode\": 31,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已拆包，扫描人员【$[scanByCode]-$[scanByName]】，包号【$[packageNumber]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"退件登记\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 170,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行退回件登记，登记人员是【$[scanByName]($[scanByCode])】，退回原因【$[remark3]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"拦截件扫描\",\n" +
				"\t\"operationType\": \"INTERCEPTOR_SCAN\",\n" +
				"\t\"scanTypeCode\": 180,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行拦截件扫描，扫描人员是【$[scanByName]($[scanByCode])】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"拦截件登记\",\n" +
				"\t\"operationType\": \"INTERCEPTOR_REGISTRATION\",\n" +
				"\t\"scanTypeCode\": 190,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行拦截件登记，登记人员是【$[scanByName]($[scanByCode])】，拦截原因【$[remark1]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"装车扫描\",\n" +
				"\t\"operationType\": \"LOADING_SCAN\",\n" +
				"\t\"scanTypeCode\": 40,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已装车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[taskCode]】，车牌号【$[carNumber]】\",\n" +
				"\t\"trackingType\": \"TASKCODE\",\n" +
				"\t\"trackingTypeCode\": 4\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"卸车扫描\",\n" +
				"\t\"operationType\": \"UNLOADING_SCAN\",\n" +
				"\t\"scanTypeCode\": 80,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已卸车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[taskCode]】\",\n" +
				"\t\"trackingType\": \"TASKCODE\",\n" +
				"\t\"trackingTypeCode\": 4\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"封发车扫描\",\n" +
				"\t\"operationType\": \"PACK_SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 60,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已封发车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[taskCode]】，车牌号【$[carNumber]】，车签号【$[carSignNumber]】\",\n" +
				"\t\"trackingType\": \"TASKCODE\",\n" +
				"\t\"trackingTypeCode\": 4\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"到解车扫描\",\n" +
				"\t\"operationType\": \"UNCAR_SCAN\",\n" +
				"\t\"scanTypeCode\": 70,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已到卸车，扫描人员【$[scanByCode]-$[scanByName]】，任务编号【$[taskCode]】，车牌号【$[carNumber]】，车签号【$[carSignNumber]】\",\n" +
				"\t\"trackingType\": \"TASKCODE\",\n" +
				"\t\"trackingTypeCode\": 4\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"退件撤销\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 171,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行退回件撤销，撤销人员是【$[scanByName]($[scanByCode])】，撤销原因【$[remark2]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"转寄登记\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 400,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行转寄件登记，登记人员是【$[scanByName]($[scanByCode])】，转寄原因【$[remark2]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"转寄撤销\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 401,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行转寄件撤销，撤销人员是【$[scanByName]($[scanByCode])】，撤销原因【$[remark2]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"退件扫描\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 172,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行退回件扫描，扫描人员是【$[scanByName]($[scanByCode])】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"拦截件撤销\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 191,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行拦截件撤销，撤销人员是【$[scanByName]($[scanByCode])】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"转寄扫描\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 402,\n" +
				"\t\"template\": \"【$[scanNetworkName]】已进行转寄件扫描，扫描人员是【$[scanByName]($[scanByCode])】，转寄新单号【$[remark2]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"航空发件\",\n" +
				"\t\"operationType\": \"SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 51,\n" +
				"\t\"template\": \"快件在【$[scanNetworkName]】发出，下一站是【$[nextStopName]】，航班号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"航空发件\",\n" +
				"\t\"operationType\": \"SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 51,\n" +
				"\t\"template\": \"快件在【$[scanNetworkName]】发出，下一站是【$[nextStopName]】，航班号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"航空到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 93,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"航空到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 93,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"完结\",\n" +
				"\t\"scanTypeCode\": 130,\n" +
				"\t\"template\": \"$[remark2] \",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"装车发件\",\n" +
				"\t\"operationType\": \"LOADING_SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 1,\n" +
				"\t\"template\": \"快件在【$[scanNetworkName]】发出，发往【$[nextStopName]】，扫描员【$[scanByName]】，任务编号【$[remark2]】，航班号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"装车发件\",\n" +
				"\t\"operationType\": \"LOADING_SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 1,\n" +
				"\t\"template\": \"快件在【$[scanNetworkName]】发出，发往【$[nextStopName]】，扫描员【$[scanByName]】，任务编号【$[remark2]】，航班号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"装车发件\",\n" +
				"\t\"operationType\": \"LOADING_SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 1,\n" +
				"\t\"template\": \"快件在【$[scanNetworkName]】发出，发往【$[nextStopName]】，扫描员【$[scanByName]】，任务编号【$[remark2]】，航班号【$[remark1]】\",\n" +
				"\t\"trackingType\": \"TASKCODE\",\n" +
				"\t\"trackingTypeCode\": 4\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"卸车到件\",\n" +
				"\t\"operationType\": \"UNLOADING_ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 2,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，扫描员【$[scanByName]】，任务单号【$[remark2]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\",\n" +
				"\t\"trackingTypeCode\": 1\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"卸车到件\",\n" +
				"\t\"operationType\": \"UNLOADING_ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 2,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，扫描员【$[scanByName]】，任务单号【$[remark2]】\",\n" +
				"\t\"trackingType\": \"PACKAGE\",\n" +
				"\t\"trackingTypeCode\": 2\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"卸车到件\",\n" +
				"\t\"operationType\": \"UNLOADING_ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 2,\n" +
				"\t\"template\": \"快件到达【$[scanNetworkName]】上一站是【$[nextStopName]】，扫描员【$[scanByName]】，任务单号【$[remark2]】\",\n" +
				"\t\"trackingType\": \"TASKCODE\",\n" +
				"\t\"trackingTypeCode\": 4\n" +
				"}]"};

		String[] json1 = {"[{\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"快件揽收\",\n" +
				"\t\"operationType\": \"EXPRESS_COLLECTION\",\n" +
				"\t\"scanTypeCode\": 10,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】【$[scanNetworkName]】您的极兔小哥$[staffName]($[staffContact])已取件。如有异常问题或需投诉请拨打网点电话:$[scanNetworkContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"入仓扫描\",\n" +
				"\t\"operationType\": \"RECEIPT\",\n" +
				"\t\"scanTypeCode\": 20,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】包裹顺利到达【$[scanNetworkName]】$[scanNetworkContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"发件扫描\",\n" +
				"\t\"operationType\": \"SEND_SCAN\",\n" +
				"\t\"scanTypeCode\": 50,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】快件离开【$[scanNetworkName]】已发往【$[nextStopName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"到件扫描\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 92,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】快件到达【$[scanNetworkName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"集货到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 91,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】快件到达【$[scanNetworkName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"中心到件\",\n" +
				"\t\"operationType\": \"ARRIVAL_SCAN\",\n" +
				"\t\"scanTypeCode\": 90,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】快件到达【$[scanNetworkName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"出仓扫描\",\n" +
				"\t\"operationType\": \"DELIVERY_OUT\",\n" +
				"\t\"scanTypeCode\": 94,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】【$[scanNetworkName]】的极兔小哥$[staffName]($[staffContact])正在派件，如有异常问题或需投诉请拨打网点电话:$[scanNetworkContact] \",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"入库扫描\",\n" +
				"\t\"operationType\": \"PARTNER_RECEIPT\",\n" +
				"\t\"scanTypeCode\": 200,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】您的包裹已存放至【$[remark1]】，记得早点来【$[remark2]】取件哦！有问题请找极兔小哥$[staffContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"代理点收入扫描\",\n" +
				"\t\"operationType\": \"COLLECTION_BY_PROXY\",\n" +
				"\t\"scanTypeCode\": 202,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】极兔喊您取件啦！单号$[billCode]，地址$[remark2]，可联系$[remark3]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"快件取出扫描\",\n" +
				"\t\"operationType\": \"PARTNER_DELIVERY_OUT\",\n" +
				"\t\"scanTypeCode\": 201,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】您的包裹已从【$[remark1]】重新取出，极兔小哥将继续为您派件，请等待我们的来电，电话$[staffContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"快件签收\",\n" +
				"\t\"operationType\": \"SIGNING_SCAN\",\n" +
				"\t\"scanTypeCode\": 100,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】包裹已签收！签收人是【$[remark1]】，如有疑问请联系：$[staffContact]，如有异常问题或需投诉请拨打网点电话：$[scanNetworkContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"出库扫描\",\n" +
				"\t\"operationType\": \"PARTNER_SIGNING_SCAN\",\n" +
				"\t\"scanTypeCode\": 300,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】包裹已签收！签收人凭取件码签收，如有疑问请联系：$[staffContact]，如有异常问题或需投诉请拨打网点电话：$[scanNetworkContact] \",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"code\": \"16\",\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"问题件扫描\",\n" +
				"\t\"operationType\": \"ABNORMAL_PIECE_SCAN\",\n" +
				"\t\"scanTypeCode\": 110,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】签收失败了，如有疑问请联系极兔小哥:$[staffContact]/$[scanNetworkContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"code\": \"20\",\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"问题件扫描\",\n" +
				"\t\"operationType\": \"ABNORMAL_PIECE_SCAN\",\n" +
				"\t\"scanTypeCode\": 110,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】签收失败了，如有疑问请联系极兔小哥:$[staffContact]/$[scanNetworkContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"code\": \"5\",\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"问题件扫描\",\n" +
				"\t\"operationType\": \"ABNORMAL_PIECE_SCAN\",\n" +
				"\t\"scanTypeCode\": 110,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】发件人取消寄件了，希望下次不要错过您，如有疑问，可以再联系我吗？:$[staffContact]/$[scanNetworkContact]\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"code\": \"5\",\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"航空运输\",\n" +
				"\t\"scanTypeCode\": 41,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】航空运输中\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"code\": \"172\",\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"退件扫描\",\n" +
				"\t\"scanTypeCode\": 172,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】【$[scanNetworkName]】已进行退回件扫描，扫描人员是【$[scanByName]】\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"转寄扫描\",\n" +
				"\t\"operationType\": \"REBACKEXPRESS\",\n" +
				"\t\"scanTypeCode\": 402,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】【$[scanNetworkName]】已进行转寄件扫描，扫描人员是【$[scanByName]($[scanByCode])】\",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}, {\n" +
				"\t\"countryEnum\": \"CN\",\n" +
				"\t\"operationDesc\": \"完结\",\n" +
				"\t\"scanTypeCode\": 130,\n" +
				"\t\"template\": \"【$[scanNetworkCity]】订单异常，请联系寄件网点【$[remark3]】或者总部客服【400-820-1666】 \",\n" +
				"\t\"trackingType\": \"WAYBILL\"\n" +
				"}]"};

		/*List<Node> nodes = JSON.parseArray(json, Node.class);
		List<Node> nodes1 = JSON.parseArray(json1, Node.class);
		nodes.addAll(nodes1);

		List<String> list = new ArrayList<>();
		nodes.stream().forEach(x -> {
			//处理operationDesc
			list.add(x.getOperationDesc());
			String[] split = x.template.split("【");
			for (int i = 0; i < split.length; i++) {
				String s = split[i];
				int index = s.indexOf("】");
				if (index != -1) {
					s = s.substring(index + 1);
				}
				String[] split1 = s.split("，");
				list.addAll(Arrays.asList(split1));
			}
		});

		list.removeAll(Collections.singleton(null));
		list.removeAll(Collections.singleton(""));
		List<String> collect = list.stream().distinct().collect(Collectors.toList());
		collect.forEach(System.out::println);*/

		Path path = Paths.get("C:\\Users\\Administrator\\Desktop", "translate.txt");
		Map<String, String> map = new HashMap<>();
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(x -> {
				if (StrUtil.isNotEmpty(x)) {
					String[] s = x.split("\\t");
					String key = s[0];
					String value = s[s.length - 1];
					map.put(key, value);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> map1 = map;
		map1 = sortMap(map1);

		final String[] a = {json[0]};
		final String[] b = {json1[0]};
		map1.forEach((k, v) -> {
			a[0] = a[0].replace(k, v);
			b[0] = b[0].replace(k, v);
		});

		System.out.println(a[0]);
		System.out.println(b[0]);

	}

	private static Map<String, String> sortMap(Map<String, String> map) {
		List<String> list = new ArrayList<>(map.keySet());
		Collections.sort(list, Comparator.comparingInt(String::length).reversed());
		Map<String, String> map1 = new LinkedHashMap<>();
		list.forEach(x -> map1.put(x, map.get(x)));
		return map1;
	}

	static class Node {
		private String operationDesc;
		private String template;

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		public String getOperationDesc() {
			return operationDesc;
		}

		public void setOperationDesc(String operationDesc) {
			this.operationDesc = operationDesc;
		}
	}
}
