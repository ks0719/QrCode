package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;

public class Controller {


    @FXML
    TextField Text_loc;
    @FXML
    TextField QR_loc;
    @FXML
    Button Text_btn;
    @FXML
    Button QR_btn;
    @FXML
    Button Sample_btn;
    @FXML
    Button Action_btn;
    @FXML
    Window w;

    FileChooser fc;
    DirectoryChooser dc;
    File f;

    public void Text_loc() {
             this.fc = new FileChooser();
             this.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("QR코드 생성 리스트", new String[] { "*.txt" }));
             this.f = this.fc.showOpenDialog(this.w);
           try {
                   this.Text_loc.setText(this.f.getAbsolutePath());
                 } catch (Exception e) {
                  e.printStackTrace();
                }
          }
    public void QR_loc() {
        this.dc = new DirectoryChooser();
        this.f = this.dc.showDialog(this.w);
        try {
            this.QR_loc.setText(this.f.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Sample_loc() {
        this.dc = new DirectoryChooser();
        this.f = dc.showDialog(this.w);
        this.f.getAbsolutePath();

        String inFileName = inFileName = String.valueOf(Main.class.getResource("").getPath().replaceAll("%", " ").replaceAll("/", "\\\\")) + "양식 파일.xlsx";
        String outFileName = this.f.getAbsolutePath()+"\\양식 파일.xlsx";
        try {
            FileInputStream fis = new FileInputStream(inFileName);
            FileOutputStream fos = new FileOutputStream(outFileName);
            int data = 0;
            byte[] b = new byte[4096];
            while ((data = fis.read(b)) != -1)
                fos.write(b, 0, data);
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("파일이 정상적으로 생성되었습니다.");
        a.setTitle("파일 생성 결과");
        a.showAndWait();
    }

    BarCode br=new BarCode();
    public void Action_btn(){
        f=new File(Text_loc.getText());
        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(f),"euc-kr"));
            String line="";
            int i=0;
            while((line=reader.readLine())!=null){
                //System.out.println(line);
                if(i>0){
                    String[] lines=line.split("\t");
                    System.out.println(lines+"/"+QR_loc.getText());
                    br.new_BarCode(lines,QR_loc.getText());
                }
                i++;
            }
            reader.close();
        }catch (FileNotFoundException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("파일이 없습니다.!"+e);
            a.setTitle("결과");
            a.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
