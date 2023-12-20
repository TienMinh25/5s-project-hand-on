package com.tienminh.a5s_project_hand_on.classes;

import java.util.ArrayList;

public class GenCSV {
    private Integer score;
    private String title;
    private String description;

    public GenCSV(Integer score, String title, String description) {
        this.score = score;
        this.title = title;
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static String generateCSV(ArrayList<Integer> scores){
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Column1,Column2,Column3\n");
        return csvContent.toString();
    }
    //         static public String generateCSV() {
//        // Lấy dữ liệu từ cơ sở dữ liệu
////        List<MyData> dataList = yourDatabaseManager.getDataToExport();
//
//        StringBuilder csvContent = new StringBuilder();
//
//        // Thêm tiêu đề cột
//        csvContent.append("Column1,Column2,Column3\n");
//
//        // Thêm dữ liệu từ cơ sở dữ liệu vào file CSV
//        for (MyData data : dataList) {
//            csvContent.append(data.getColumn1()).append(",")
//                    .append(data.getColumn2()).append(",")
//                    .append(data.getColumn3()).append("\n");
//        }
//
//        return csvContent.toString();
//    }

}
