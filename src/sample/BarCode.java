package sample;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class BarCode {
    public void new_BarCode(String[] line,String pathname) {
        //line[0]=코드,line[1]=이름,line[2]=생년월일,line[3]=성별,line[4]=직분,line[5]=구역
        String name="["+line[5]+"]"+line[1]+" "+line[4]+"["+line[3]+"]("+line[2]+")";
        System.out.println(Arrays.toString(line));
      try{

          File f = null;
          f=new File(pathname+"/"+name+".png");
          //코드 값?
          String codeurl=new String(line[0].getBytes("UTF-8"),"ISO-8859-1");
          //QR코드 바코드 생성값?
          int qrcodeColor=0xFF000000;
          //QR코드 배경색
          int backgroundColor=0xFFFFFFFF;

          QRCodeWriter qrCodeWriter=new QRCodeWriter();
          //3,4번째 파라미터값:width,height값 지정
          BitMatrix bitMatrix=qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE,500,500);

          MatrixToImageConfig matrixToImageConfig=new MatrixToImageConfig(qrcodeColor,backgroundColor);
          BufferedImage bufferedImage= MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
          //이미지생성
          ImageIO.write(bufferedImage,"png",f);
      }catch(Exception e){
          e.printStackTrace();
      }


    }
}
