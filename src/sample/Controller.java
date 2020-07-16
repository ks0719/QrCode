package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.xml.soap.Text;
import java.io.*;
import java.net.URLClassLoader;

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
             fc = new FileChooser();
             fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("QR코드 생성 리스트", new String[] { "*.txt" }));
             f = this.fc.showOpenDialog(this.w);
        if(f!=null){
            try {
                this.Text_loc.setText(this.f.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
          }
    public void QR_loc() {
       dc = new DirectoryChooser();
       f = this.dc.showDialog(this.w);
       if(f!=null){
           try {
               this.QR_loc.setText(this.f.getAbsolutePath());
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }
    public void Sample_loc() {

        dc = new DirectoryChooser();
        f = dc.showDialog(this.w);
        if(f!=null){
            String outFileName = this.f.getAbsolutePath()+"\\양식 파일.xlsx";
            try {
                //아래걸로 해야지 다운되네요.
                InputStream fis=getClass().getResourceAsStream("/sample/양식 파일.xlsx");
                FileOutputStream fos = new FileOutputStream(outFileName);
                int data = 0;
                byte[] b = new byte[4096];
                while ((data = fis.read(b)) != -1)
                    fos.write(b, 0, data);
                fis.close();
                fos.close();
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(e.toString());
                a.setTitle("파일 생성 결과");
                a.showAndWait();
            }
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("파일이 정상적으로 생성되었습니다.경로:"+outFileName);
            a.setTitle("파일 생성 결과");
            a.showAndWait();
        }
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
                    if(lines.length!=6){
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("내용이 올바르지 않습니다. 파일의 내용을 다시 확인해주세요.\n위치:"+i+"번째 줄");
                        a.setHeaderText("양식 파일 에러");
                        a.setTitle("결과");
                        a.showAndWait();
                        return;
                    }
                    System.out.println(lines+"/"+QR_loc.getText());
                    br.new_BarCode(lines,QR_loc.getText());
                }
                i++;
            }
            reader.close();
        }catch (FileNotFoundException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("경로가 제대로 설정되지 않았습니다.! 재 확인 바랍니다.");
            a.setHeaderText("완료");
            a.setTitle("결과");
            a.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
