package file;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ReadExcel {

	private static List<Vo> readExcel(String path, List<Integer> columns) {
		File file = new File(path);
		FileInputStream fis = null;
		Workbook workBook = null;
		List<Vo> resList = new ArrayList<>();
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				workBook = WorkbookFactory.create(fis);
				int numberOfSheets = workBook.getNumberOfSheets(); // 获取有几个sheet
				List<Vo> list = new ArrayList<>();
				for (int i = 0; i < numberOfSheets; i++) {
					Sheet sheetAt = workBook.getSheetAt(i);
					String sheetName = sheetAt.getSheetName();
					System.out.println("工作表名称:" + sheetName);
					// 当前sheet总数据行数
					int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
					System.out.println("当前表格的总行数:" + rowsOfSheet);
					// 第一行
					Row row0 = sheetAt.getRow(0);
					int physicalNumberOfCells = sheetAt.getRow(0).getPhysicalNumberOfCells();
					String[] title = new String[physicalNumberOfCells];
					for (int j = 0; j < physicalNumberOfCells; j++) {
						title[i] = row0.getCell(i).getStringCellValue();
						System.out.print(title[i] + "  ");
					}
					System.out.println();
					for (int r = 1; r < rowsOfSheet; r++) {
						Row row = sheetAt.getRow(r);
						int columnCount = row.getPhysicalNumberOfCells();
						Vo vo = new Vo();
						for (int c = 0; c < columnCount; c++) {
							Cell cell = row.getCell(c);
							CellType cellType = cell.getCellType();
							if (cell.getCellType().equals(CellType.BLANK)) {
								continue;
							}
							if (columns.get(0).equals(c)) {
								String stringCellValue = "";
								if (cellType.equals(CellType.NUMERIC)) {
									stringCellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
								} else if (cellType.equals(CellType.STRING)) {
									stringCellValue = cell.getStringCellValue();
								}
								vo.setPhone(stringCellValue);
							} else if (columns.get(1).equals(c)) {
								String stringCellValue = "";
								if (cellType.equals(CellType.NUMERIC)) {
									stringCellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
								} else if (cellType.equals(CellType.STRING)) {
									stringCellValue = cell.getStringCellValue();
								}
								stringCellValue = stringCellValue.substring(0, stringCellValue.length() - 1);
								vo.setScore(stringCellValue);
							}
						}
						resList.add(vo);
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (workBook != null) {
					try {
						workBook.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return resList;
	}

	private static StringBuilder getSqlList(List<Vo> list) {
		// String sql1 = "update user_center.uc_seller_extend set member_level =4 ,member_expire_time ='%s' where seller_id  in (select IFNULL(id,0)  from user_center.uc_seller us where contact_tel ='%s');";
		String sql1 = "update user_center.uc_seller_extend set member_level =4 ,member_expire_time ='%s' where seller_id =(IFNULL((select us.id  from user_center.uc_user uu left join user_center.uc_seller us on uu.id=us.user_id  where uu.phone ='%s'),0));";
		// String sql2 = "update user_center.uc_seller_combo_rights_record set s_level_unlock_times = %s,a_level_unlock_times =0,b_level_unlock_times=0 where seller_id  in (select IFNULL(id,0)  from user_center.uc_seller us where contact_tel ='%s');";
		String sql2 = "update user_center.uc_seller_combo_rights_record set s_level_unlock_times = %s,a_level_unlock_times =0,b_level_unlock_times=0 where seller_id =(IFNULL((select us.id  from user_center.uc_user uu left join user_center.uc_seller us on uu.id=us.user_id  where uu.phone ='%s'),0));";
		StringBuilder stringBuilder = new StringBuilder();
		list.forEach(x -> {
			stringBuilder.append(String.format(sql1, getCurrentDayEndTime(), x.getPhone())).append("\r\n");
			stringBuilder.append(String.format(sql2, x.getScore(), x.getPhone())).append("\r\n");
		});
		System.out.println(stringBuilder);
		return stringBuilder;
	}

	private static String getCurrentDayEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0);
		localDateTime = localDateTime.plusYears(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	private static void writeFile(String filePath, StringBuilder stringBuilder) {
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filePath);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Vo {
		private String phone;
		private String score;

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}
	}

	public static void main(String[] args) {
		List<Vo> list = readExcel("D:\\Users\\Desktop\\非洲商机商家开通信息.xlsx", Arrays.asList(0, 1));
		writeFile("D:\\Users\\Desktop\\非洲商机商家开通信息.txt", getSqlList(list));
		System.out.println("completed.");

		List<String> list1 = Arrays.asList(
				"15336926868"
				, "13867937417"
				, "15356791398"
				, "13362991122"
				, "13388659092"
				, "13516967352"
				, "13575936037"
				, "13705793558"
				, "18358002923"
				, "13705797969"
				, "15157923114"
				, "17816691919"
				, "13750931553"
				, "13867912904"
				, "13902792248"
				, "13388657201"
				, "13588688533"
				, "13516895873"
				, "18072367877"
				, "13822116603"
				, "13158847997"
				, "13735787547"
				, "15006805678"
				, "13906794137"
				, "13641208842"
				, "18767220101"
				, "13316203488"
				, "15868861027"
				, "13829801515"
				, "13510793187"
				, "13680907355"
				, "15818330429"
				, "13757912399"
				, "18457992523"
				, "13456748665"
				, "19957980425"
				, "13758978612"
				, "18676597744"
				, "15967994818");
		List<String> list2 = Arrays.asList(
				"15336926868"
				, "13867937417"
				, "15356791398"
				, "13362991122"
				, "13388659092"
				, "13516967352"
				, "13575936037"
				, "13705793558"
				, "18358002923"
				, "13705797969"
				, "15157923114"
				, "17816691919"
				, "13867912904"
				, "13588688533"
				, "13516895873"
				, "18072367877"
				, "15006805678"
				, "13906794137"
				, "13641208842"
				, "18767220101"
				, "13316203488"
				, "13829801515"
				, "15868861027"
				, "13757912399"
				, "18457992523"
				, "13456748665"
				, "13758978612"
				, "19957980425"
				, "15967994818"
				, "18676597744");
		Collection<String> subtract = CollectionUtils.subtract(list1, list2);
		System.out.println(subtract);
	}
}
