
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Ders {
    public void insertUpdateDeleteStudent(char operasyon, Integer id, String ders, Integer saat){
      
      Connection con = MyConnection.getConnection();
      PreparedStatement ps;
      
      // i == insert yani ekleme yapma
      if(operasyon == 'i'){
          try {
              ps = con.prepareStatement("INSERT INTO `ders`(`etiket`, `saat_sayısı`) VALUES (?, ?)");
              ps.setString(1, ders);
              ps.setInt(2, saat);
              
              if(ps.executeUpdate() > 0){
                  JOptionPane.showMessageDialog(null, "Yeni ders eklendi.");
              }
          } catch (SQLException ex) {
              Logger.getLogger(Ogrenci.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      
       // u == update yani güncelleme
      if(operasyon == 'u'){
          try {
              ps = con.prepareStatement("UPDATE `ders` SET `etiket`= ?, `saat_sayısı`= ? WHERE `id` = ?");
              ps.setString(1, ders);
              ps.setInt(2, saat);
              ps.setInt(3, id); 
              
              if(ps.executeUpdate() > 0){
                  JOptionPane.showMessageDialog(null, "Ders bilgileri güncellendi.");
              }
          } catch (SQLException ex) {
              Logger.getLogger(Ogrenci.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      
      //d == delete sil
      if(operasyon == 'd'){
          int YesOrNo = JOptionPane.showConfirmDialog(null, "Bu dersi alan öğrencilerin puanları da silinecek.", "Dersi sil", JOptionPane.OK_CANCEL_OPTION, 0);
          
          if(YesOrNo == JOptionPane.OK_OPTION){
               try {
              ps = con.prepareStatement("DELETE FROM `ders` WHERE `id` = ?");
              ps.setInt(1, id); 
              if(ps.executeUpdate() > 0){
                  JOptionPane.showMessageDialog(null, "Ders ve bilgileri silindi.");
              }
          } catch (SQLException ex) {
              Logger.getLogger(Ogrenci.class.getName()).log(Level.SEVERE, null, ex);
          }
          } 
      }
  }
    
    public boolean isCourseExist(String courseName){
        boolean isExist = false;
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `ders` WHERE `etiket` = ?");
            ps.setString(1, courseName);
          
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                isExist = true;
            }
        } catch (SQLException ex) {
          Logger.getLogger(Ogrenci.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }
    
    public void fillCourseJTable(JTable table){
      Connection con = MyConnection.getConnection();
      PreparedStatement ps;
      try {
          ps = con.prepareStatement("SELECT * FROM `ders`");
          
          ResultSet rs = ps.executeQuery();
          DefaultTableModel model = (DefaultTableModel)table.getModel();
          
          Object[] row;
          
          while(rs.next()){
              row = new Object[3];
              row[0] = rs.getInt(1);
              row[1] = rs.getString(2);
              row[2] = rs.getInt(3);
              
              model.addRow(row);
          }
          
          
      } catch (SQLException ex) {
          Logger.getLogger(Ogrenci.class.getName()).log(Level.SEVERE, null, ex);
      } 
  }
    
    public int getCourseId(String courseLabel){
        int courseId = 0;
        
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `ders` WHERE `etiket` = ?");
            ps.setString(1, courseLabel);
          
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                courseId = rs.getInt("Id");
            }
        } catch (SQLException ex) {
          Logger.getLogger(Ogrenci.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return courseId;
    }
    
    public void fillCourseCombo(JComboBox combo){
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `ders`");
          
            ResultSet rs = ps.executeQuery();
          
            while(rs.next()){
                combo.addItem(rs.getString(2));
            }
        }catch (SQLException ex) {
         Logger.getLogger(Ogrenci.class.getName()).log(Level.SEVERE, null, ex);
      }
      
  }
    
}
