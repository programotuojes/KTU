package extendsFX;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public abstract class BaseConsole extends BaseApps { 
        String colorName1 = "black";
        String colorName2 = "blue";
        double textAreaW = 400, textAreaH = 500;
        public TextArea ta1 = null;
        public TextArea ta2 = null;
        public TextArea printArea = null;
        
    @Override
    public void start(Stage stage) throws Exception {
        if(colorName1 != null)
            ta1 = addTextArea(colorName1, textAreaW, textAreaH);
        if(colorName2 != null){
            ta2 = addTextArea(colorName2, textAreaW, textAreaH);
            if(ta1 == null)
                ta1 = ta2;
        } else 
            ta2 = ta1;
        printArea = ta2;
        addNewHBox();
        if(stage.getTitle() == null)
            stage.setTitle("Eksperimentai su 2 laukų console ©Eimutis");        
        super.start(stage);
    }
    @Override
    public void createControls() {
        if(ta1 == ta2)
            addButton("clear", e -> ta1.setText(""));
        else {
            addButton("clear1", e -> ta1.setText(""));
            addButton("clear2", e -> ta2.setText(""));
        }
    }
    public void setTextAreas(String colorName1, String colorName2,
                      double textAreaW, double textAreaH){
        this.colorName1 = colorName1;
        this.colorName2 = colorName2;
        this.textAreaW  = textAreaW;
        this.textAreaH  = textAreaH;
    }
    // alias 
    public void print(String s){
        printArea.appendText(s);
    }
    public void printLn(String s){
        printArea.appendText(s + nL);
    }
    public int readLastInt(){
        String temp = ta1.getSelectedText();
        if(temp.isEmpty())
            temp = ta1.getText();
        char[] chars = temp.toCharArray();
        int i = chars.length-1;
        while(i>=0 && !Character.isDigit(chars[i])) 
            i-- ;
        if(i<0) return 0;
        int i2 = i--;
        while(i>=0 && Character.isDigit(chars[i])) 
            i--;
        int sign = (i>=0 && chars[i]=='-')? -1: 1;
        int num = 0;
        while(++i <= i2)
            num = (num*10 + (chars[i] -'0'));
        return sign * num;
    }    
    public String readLastLine(){
        String temp = ta1.getSelectedText();
        if(temp.isEmpty())
            temp = ta1.getText();
        char[] chars = temp.toCharArray();
        char endLine = nL.charAt(0);
        int i = chars.length-1;
        while(i>=0 && Character.isWhitespace(chars[i])) 
            i-- ;
        if(i<0) return "";
        int i2 = i--;
        while(i>=0 && chars[i] != endLine) 
            i--;
        return new String(chars, i+1, i2-i);
    }    
}
