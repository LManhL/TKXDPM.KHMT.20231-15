package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CD extends Media {

    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;

    public CD() throws SQLException{

    }

    public CD(int id, String title, String category, int price, int quantity, String type, String artist,
            String recordLabel, String musicType, Date releasedDate) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " artist='" + artist + "'" + ", recordLabel='" + recordLabel + "'"
                + "'" + ", musicType='" + musicType + "'" + ", releasedDate='"
                + releasedDate + "'" + "}";
    }
    /* Hàm getMediaById() vi phạm nguyên tắc OCP
     * Phương thức getMediaById giả định cụ thể về cách dữ liệu được tổ chức trong cơ sở dữ liệu (bảng CD join với Media).
     * Điều này tạo ra sự phụ thuộc chặt chẽ vào chi tiết cụ thể của cơ sở dữ liệu.
     * Cách giải quyết: 
     * - Bước 1. Sử dụng Interfaces: Định nghĩa một interface có tên MediaRepository mô tả các dịch vụ lấy dữ liệu từ cơ sở dữ liệu
     * 		mà lớp CD sẽ sử dụng, mà không biết chi tiết cụ thể về cơ sở dữ liệu.
     * - Bước 2. Implement MediaRepository cho Cơ Sở Dữ Liệu.
     * - Bước 3. Tạo một biến có tên mediaRepository bên trong lớp cha của nó là Media
     * - Bước 4. Gọi biến mediaRepository để thực thi các hàm tương ứng, ví dụ bên trong hàm getMediaById gọi mediaRepository.getCDById()
     */
    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                     "aims.CD " +
                     "INNER JOIN aims.Media " +
                     "ON Media.id = CD.id " +
                     "where Media.id = " + id + ";";
        ResultSet res = stm.executeQuery(sql);
		if(res.next()) {
            
            // from media table
            String title = "";
            String type = res.getString("type");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");

            // from CD table
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");
           
            return new CD(id, title, category, price, quantity, type, 
                          artist, recordLabel, musicType, releasedDate);
            
		} else {
			throw new SQLException();
		}
    }

    @Override
    public List getAllMedia() {
        return null;
    }

}
