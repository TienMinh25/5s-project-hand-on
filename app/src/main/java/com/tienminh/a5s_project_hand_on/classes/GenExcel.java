package com.tienminh.a5s_project_hand_on.classes;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GenExcel {

    public static void generateExcel(ArrayList<Integer> scores, String nameOfRoom, String nameOfArea) {
        Integer diemtb = 0;
        ArrayList<Excel> excelList = new ArrayList<>();
        Log.d("SIZE_OF_ARRAY", String.valueOf(scores.size()));
        if (scores.size() != 0) {
            excelList.add(new Excel(scores.get(0), "Nền sàn", "Có sạch sẽ và được giữ gìn tốt không? Có rác trên sàn không?"));
            excelList.add(new Excel(scores.get(1), "Thùng rác", "Có sạch sẽ và đặt ở vị trí hợp lý không?"));
            excelList.add(new Excel(scores.get(2), "Thùng gạt tàn", "Có sạch sẽ và đặt ở vị trí hợp lý không?"));
            excelList.add(new Excel(scores.get(3), "Tường", "Có sạch sẽ và được giữ gìn tốt không?"));
            excelList.add(new Excel(scores.get(4), "Cửa sổ", "Có sạch sẽ và được giữ gìn tốt không?"));
            excelList.add(new Excel(scores.get(5), "Trần", "Có sạch sẽ và được giữ gìn tốt không? Có bụi hoặc mạng nhện không?"));
            excelList.add(new Excel(scores.get(6), "Đèn", "Có sạch sẽ, an toàn và được bố trí hợp lý không? Còn sử dụng tốt hay không?"));
            excelList.add(new Excel(scores.get(7), "Góc hành lang", "Có rác hoặc đồ vật nào không cần thiết không?"));
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        Row rowNameOfRoom = sheet.createRow(0);
        CellRangeAddress mergedRegion11 = new CellRangeAddress(0, 0, 0, 10);
        sheet.addMergedRegion(mergedRegion11);

        Cell mergeCell = rowNameOfRoom.createCell(0); // Create cell in the first column of the merged region
        mergeCell.setCellValue("Tên khu vực: " + nameOfArea + " - Tên phòng: " + nameOfRoom);

        // Create header row
        Row headerRow = sheet.createRow(1);
        headerRow.createCell(0).setCellValue("#");
        headerRow.createCell(1).setCellValue("Mục");
        headerRow.createCell(2).setCellValue("Các điểm kiểm tra");
        headerRow.createCell(3).setCellValue("Điểm");

        // Populate data rows
        for (int i = 0; i < excelList.size(); i++) {
            Excel excel = excelList.get(i);
            Row row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(excel.getTitle());
            row.createCell(2).setCellValue(excel.getDescription());
            row.createCell(3).setCellValue(excel.getScore());
            diemtb += excel.getScore();
        }

        Row footageRow = sheet.createRow(10);

        CellRangeAddress mergedRegion = new CellRangeAddress(10, 10, 0, 7); // From row 10, col 0 to row 10, col 3
        sheet.addMergedRegion(mergedRegion);

        Cell mergedCell = footageRow.createCell(0); // Create cell in the first column of the merged region
        mergedCell.setCellValue("Điểm: " + String.valueOf(diemtb/8.0));

        Row additionRow = sheet.createRow(11);
        CellRangeAddress mergedRegion1 = new CellRangeAddress(11, 11, 0, 7); // From row 10, col 0 to row 10, col 3
        sheet.addMergedRegion(mergedRegion1);
        Cell mergedCell1 = additionRow.createCell(0); // Create cell in the first column of the merged region
        mergedCell1.setCellValue("32-30: Rất tốt. Nên duy trì 5S thường xuyên./ 29-25: Tốt, có thể tiếp tục cải tiến hơn nữa. / Dưới 24: Cần hiểu rõ và áp dụng 5S tốt hơn.");

        // Save the workbook to a file
        try {
            File file = createExcelFile(generateUniqueExcelFileName());
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                Log.d("ExcelGenerator", "Excel file generated successfully: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ExcelGenerator", "Error creating Excel file: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File createExcelFile(String fileName) throws IOException {
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + "ExcelFile");
        }
        else
        {
            dir = new File(Environment.getExternalStorageDirectory() + "/" + "ExcelFile");
        }

        // Make sure the path directory exists.
        if (!dir.exists())
        {
            // Make it, if it doesn't exit
            boolean success = dir.mkdirs();
            if (!success)
            {
                dir = null;
            }
        }
        return new File(dir, fileName);
    }

    public static String generateUniqueExcelFileName() {
        // Lấy thời gian hiện tại để đảm bảo tính duy nhất
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Tạo một số ngẫu nhiên để thêm vào chuỗi
        Random random = new Random();
        int randomSuffix = random.nextInt(1000);

        // Kết hợp thời gian và số ngẫu nhiên để tạo tên file duy nhất
        String uniqueFileName = "excel_" + timeStamp + "_" + randomSuffix + ".xlsx";

        return uniqueFileName;
    }
}


