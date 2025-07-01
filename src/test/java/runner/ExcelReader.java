package runner;

import org.json.simple.JSONObject;

import utils.ExcelUtils;

public class ExcelReader {

	public int getDataSets(String sheetName, String dataFlag, String filePath) {
		ExcelUtils XlsReader = new ExcelUtils(filePath);
		int flagRowNumber = 1;

		while (!XlsReader.getCellData(sheetName, 0, flagRowNumber).equalsIgnoreCase(dataFlag)) {
			flagRowNumber++;
		}
		int dataStartRowNumber = flagRowNumber + 2;
		int totalRows = 0;

		while (!XlsReader.getCellData(sheetName, 0, dataStartRowNumber).equals("")) {
			totalRows++;
			dataStartRowNumber++;
		}
		System.out.println("Total Rows :: " + totalRows);
		return totalRows;
	}

	public JSONObject getTestData(String sheetName, String dataFlag, String filePath, int iteration) {
		ExcelUtils XlsReader = new ExcelUtils(filePath);
		int flagRowNumber = 1;

		while (!XlsReader.getCellData(sheetName, 0, flagRowNumber).equalsIgnoreCase(dataFlag)) {
			flagRowNumber++;
		}

		int dataStartRowNumber = flagRowNumber + 2;
		int colStartRowNumber = flagRowNumber + 1;
		int index = 1;

		while (!XlsReader.getCellData(sheetName, 0, dataStartRowNumber).equals("")) {
			int colNumber = 0;
			JSONObject json = new JSONObject();
			if (index == iteration) {
				while (!XlsReader.getCellData(sheetName, colNumber, dataStartRowNumber).equals("")) {
					String data = XlsReader.getCellData(sheetName, colNumber, dataStartRowNumber);
					String column = XlsReader.getCellData(sheetName, colNumber, colStartRowNumber);
					// System.out.println(column + " :: " + data);
					json.put(column, data);
					colNumber++;
				}
				return json;
			} else {
				index++;
			}
			dataStartRowNumber++;
		}
		return new JSONObject();
	}
}
