import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
public class MyOutputStream extends OutputStream {
	private JTextArea textArea;
	
	public MyOutputStream(JTextArea textArea){
		this.textArea=textArea;
	}
	
    public void write(byte[] b) throws IOException {
        // append the data as characters to the JTextArea control
        textArea.append( new String(b,"UTF-8"));
    }

	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		byte[] bytes=new byte[1];
		bytes[0]=(byte)b;
		 textArea.append( new String(bytes,"ISO-8859-1"));
		
	}   
}
