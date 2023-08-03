import Search.Client;
import Search.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SearchBoxDemo {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        Client cl=new Client();
        frame.setLocation(300, 200);
        frame.setSize(new Dimension(500, 300));
        frame.setTitle("Search Box");
        Container container = frame.getContentPane();
        JTextField textField = new JTextField();
        JButton button = new JButton("Find");
        textField.setBackground(Color.CYAN);
        textField.addKeyListener(new CustomKeyListener());
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("HI");
        JList<String> list = new JList<>(model);
        list.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        list.setBounds(100,100, 75,75);
        //scrollPane.add(list);
        scrollPane.setBackground(Color.BLUE);
        button.addActionListener(new CustomActionListener(textField,model,cl));
        container.setLayout(new BorderLayout());
        container.add(textField, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(button, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.validate();
        frame.addWindowListener(new CustomWindowListener());
    }
}

class CustomWindowListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        JFrame frm = (JFrame) e.getSource();
        frm.dispose();
    }
}
class CustomKeyListener extends KeyAdapter{
    @Override

    public void keyTyped(KeyEvent e) {
        //String a="";
        //a+=e.getKeyChar();
        //System.out.println(a);
    }

}
class CustomActionListener implements ActionListener{
    private final JTextField tf;
    Client cl;
    private final DefaultListModel<String> model;
    CustomActionListener(JTextField tf,DefaultListModel<String> model,Client cl){
        this.tf=tf;
        this.model = model;
        this.cl=cl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String s=tf.getText();
        List<String> l=new ArrayList<>();
        try {
            l=cl.main(s);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(s);
        model.clear();
        model.addAll(l);
        tf.setText("");
        ((JButton)e.getSource()).transferFocus();
    }
}