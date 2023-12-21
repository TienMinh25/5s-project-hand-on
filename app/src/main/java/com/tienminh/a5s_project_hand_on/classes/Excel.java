package com.tienminh.a5s_project_hand_on.classes;

import java.util.ArrayList;

public class Excel {
    private Integer score;
    private String title;
    private String description;

    public Excel(Integer score, String title, String description) {
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
        ArrayList<Excel> csvs = new ArrayList<>();
        csvs.add(new Excel(scores.get(0), "Nền sàn", "Có sạch sẽ và được giữ gìn tốt không? Có rác trên sàn không?"));
        csvs.add(new Excel(scores.get(1), "Thùng rác", "Có sạch sẽ và đặt ở vị trí hợp lý không?"));
        csvs.add(new Excel(scores.get(2), "Thùng gạt tàn", "Có sạch sẽ và đặt ở vị trí hợp lý không?"));
        csvs.add(new Excel(scores.get(3), "Tường", "Có sạch sẽ và được giữ gìn tốt không?"));
        csvs.add(new Excel(scores.get(4), "Cửa sổ", "Có sạch sẽ và được giữ gìn tốt không?"));
        csvs.add(new Excel(scores.get(5), "Trần", "Có sạch sẽ và được giữ gìn tốt không? Có bụi hoặc mạng nhện không?"));
        csvs.add(new Excel(scores.get(6), "Đèn", "Có sạch sẽ, an toàn và được bố trí hợp lý không? Còn sử dụng tốt hay không?"));
        csvs.add(new Excel(scores.get(7), "Góc hành lang", "Có rác hoặc đồ vật nào không cần thiết không?"));

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
