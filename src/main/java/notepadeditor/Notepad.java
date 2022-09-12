package notepadeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notepad implements ActionListener {
    JFrame jFrame, replaceFrame, font_frame;
    JMenuBar menubar;
    JMenu file, edit, format, help;
    JMenuItem New, Open, Save, SaveAs, Exit, pageSetUp, print,
                cut, copy, paste, replace, dateTime,
                font, fontColor, textAreaColor;
    JTextArea textArea;
    JComboBox cb_font_family, cb_font_style, cb_font_size;
    File file1;

    JFileChooser fileChooser;

    JTextField textField1, textField2;
    JButton button, ok;
    String title = "Untitled - Notepad";

    public Notepad(){

        try{
            //for Windows look and feel
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //cross platform look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            System.out.println(e);
        }

        jFrame = new JFrame(title);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(3);

        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Tejeshwari\\Downloads\\notepad icon.png");
        jFrame.setIconImage(icon);

        menubar = new JMenuBar();

        file = new JMenu("File");
        //file items
        New = new JMenuItem("New");
        New.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
        New.addActionListener(this);
        file.add(New);

        Open = new JMenuItem("Open");
        Open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
        Open.addActionListener(this);
        file.add(Open);

        Save = new JMenuItem("Save");
        Save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        Save.addActionListener(this);
        file.add(Save);

        SaveAs = new JMenuItem("Save As");
        SaveAs.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.SHIFT_DOWN_MASK));
        SaveAs.addActionListener(this);
        file.add(SaveAs);

        file.addSeparator();
        pageSetUp = new JMenuItem("Page Setup");
        pageSetUp.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_DOWN_MASK));
        pageSetUp.addActionListener(this);
        file.add(pageSetUp);

        print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.SHIFT_DOWN_MASK));
        print.addActionListener(this);
        file.add(print);

        file.addSeparator();
        Exit = new JMenuItem("Exit");
        Exit.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));
        Exit.addActionListener(this);
        file.add(Exit);

        edit = new JMenu("Edit");
        //edit menu items
        cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_DOWN_MASK));
        cut.addActionListener(this);
        edit.add(cut);

        copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));
        copy.addActionListener(this);
        edit.add(copy);

        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK));
        paste.addActionListener(this);
        edit.add(paste);

        edit.addSeparator();

        replace = new JMenuItem("Replace");
        replace.setAccelerator(KeyStroke.getKeyStroke('H', InputEvent.CTRL_DOWN_MASK));
        replace.addActionListener(this);
        edit.add(replace);

        edit.addSeparator();

        dateTime = new JMenuItem("Date/Time");
        dateTime.addActionListener(this);
        edit.add(dateTime);

        format = new JMenu("Format");
        //format menu items
        font = new JMenuItem("Font");
        font.addActionListener(this);
        format.add(font);

        format.addSeparator();

        fontColor = new JMenuItem("Font Color");
        fontColor.addActionListener(this);
        format.add(fontColor);

        textAreaColor = new JMenuItem("Text Area Color");
        textAreaColor.addActionListener(this);
        format.add(textAreaColor);

        help = new JMenu("Help");
        help.addActionListener(this);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(format);
        menubar.add(help);

        jFrame.setJMenuBar(menubar);

        //JScrollPane is added over JFrame
        //text area is added over JScrollPane
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jFrame.add(scrollPane);
        //jFrame.add(textArea);

        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser = new JFileChooser();

        if(e.getSource() == New){
            newNotepad();
        }

        if(e.getSource() == Exit){
            System.exit(0);
        }

        if(e.getSource() == Save){
            save();
        }

        if(e.getSource() == Open){
            open();
        }

        if(e.getSource() == SaveAs){
            saveAs();
        }

        if(e.getSource() == pageSetUp){
            pageSetUp();
        }

        if(e.getSource() == print){
            printPage();
        }

        if(e.getSource() == cut){
            textArea.cut();
        }

        if(e.getSource() == copy){
            textArea.copy();
        }

        if(e.getSource() == paste){
            textArea.paste();
        }

        if(e.getSource() == replace){
            replaceFrame();
        }

        if(e.getSource() == button){
            replace();
        }

        if(e.getSource() == dateTime){
            setDateTime();
        }

        if(e.getSource() == fontColor){
            setFontColor();
        }

        if(e.getSource() == textAreaColor){
            setTextAreaColor();
        }

        if(e.getSource() == font){
            openFontFrame();
        }

        if(e.getSource() == ok){
            setFontToNotepad();
        }
    }

    public void newNotepad(){
        String text = textArea.getText();
        if(!text.equals("")){
            int i = JOptionPane.showConfirmDialog(jFrame, "Do you want to save this file?");
            //0 -> yes, 1 -> no, 2 -> cancel
            if(i == 0){
                saveAs();
                if(!jFrame.getTitle().equals(title)) {
                    setTitle(title);
                    textArea.setText("");
                }
            } else if(i == 1){
                textArea.setText("");
            }
        }
    }

    public void open(){
        int i = fileChooser.showOpenDialog(jFrame);

        if(i == 0){
            try{
                textArea.setText("");
                file1 = fileChooser.getSelectedFile();
                FileInputStream fileInputStream = new FileInputStream(file1);

                int c;

                while((c = fileInputStream.read()) != -1){
                    char letter = (char) c;
                    textArea.append(String.valueOf(letter));
                }

                fileInputStream.close();
                setTitle(file1.getName());

            } catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public void pageSetUp(){
        //pre-defined code
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.pageDialog(printerJob.defaultPage());
    }

    public void printPage(){
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        if(printerJob.printDialog()){
            try{
                printerJob.print();
            } catch (PrinterException exc){
                System.out.println(exc);
            }
        }
    }
    public void saveAs(){
        JFileChooser fileChooser = new JFileChooser();
        int i = fileChooser.showSaveDialog(jFrame);

        if(i == 0){
            try{
                String text = textArea.getText();
                byte[] b = text.getBytes();

                file1 = fileChooser.getSelectedFile();

                FileOutputStream fileOutputStream = new FileOutputStream(file1);
                fileOutputStream.write(b);
                fileOutputStream.close();

                setTitle(file1.getName());
            } catch(Exception e){
                System.out.println(e);
            }

        } else {
            JOptionPane.showMessageDialog(jFrame,"Are you sure?", "File Not Saved", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void save() {
        if (jFrame.getTitle().equals(title)) {
            saveAs();

        } else {
            try {
                String text = textArea.getText();
                byte[] b = text.getBytes();

                file1 = fileChooser.getSelectedFile();

                FileOutputStream fileOutputStream = new FileOutputStream(file1);
                fileOutputStream.write(b);

                fileOutputStream.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    void setTitle(String title){
        jFrame.setTitle(title);
    }

    public void replace(){
        String find_what = textField1.getText();
        String replace_with = textField2.getText();

        String text = textArea.getText();
        String new_text = text.replace(find_what, replace_with);
        textArea.setText(new_text);

        replaceFrame.setVisible(false);
    }

    public void replaceFrame(){
        replaceFrame = new JFrame("Replace");
        replaceFrame.setSize(500, 500);
        replaceFrame.setLayout(null);

        JLabel label1 = new JLabel("Find What: ");
        label1.setBounds(50, 50, 80, 40);
        replaceFrame.add(label1);

        textField1 = new JTextField();
        textField1.setBounds(170, 50, 200, 40);
        replaceFrame.add(textField1);

        JLabel label2 = new JLabel("Replace With: ");
        label2.setBounds(50, 100, 100, 40);
        replaceFrame.add(label2);

        textField2 = new JTextField();
        textField2.setBounds(170, 100, 200, 40);
        replaceFrame.add(textField2);

        button = new JButton("Replace");
        button.addActionListener(this);
        button.setBounds(200, 150, 100, 40);
        replaceFrame.add(button);

        replaceFrame.setVisible(true);
    }

    public void setDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String date_time = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        textArea.append(date_time);
    }

    public void setFontColor() {
        Color color = JColorChooser.showDialog(jFrame, "Select Font Color", Color.BLACK);
        textArea.setForeground(color);
    }

    public void setTextAreaColor() {
        Color color = JColorChooser.showDialog(jFrame, "Select Font Color", Color.WHITE);
        textArea.setBackground(color);
    }

    public void openFontFrame(){
        font_frame = new JFrame("Fonts");
        font_frame.setSize(650, 300);
        font_frame.setLayout(null);

        GraphicsEnvironment envi = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilies = envi.getAvailableFontFamilyNames();
        cb_font_family = new JComboBox(fontFamilies);
        cb_font_family.setBounds(50, 50, 200, 40);
        font_frame.add(cb_font_family);

        String[] font_style = {"Plain", "Bold", "Italic"};
        cb_font_style = new JComboBox(font_style);
        cb_font_style.setBounds(300, 50, 100, 40);
        font_frame.add(cb_font_style);

        Integer[] font_size = {10, 16, 20, 25, 30, 35, 40, 50, 60};
        cb_font_size = new JComboBox(font_size);
        cb_font_size.setBounds(450, 50, 80, 40);
        font_frame.add(cb_font_size);

        ok = new JButton("OK");
        ok.setBounds(250, 150, 80, 50);
        ok.addActionListener(this);
        font_frame.add(ok);

        font_frame.setVisible(true);
    }

    public void setFontToNotepad(){
        String font_family = (String) cb_font_family.getSelectedItem();
        String font_style = (String) cb_font_style.getSelectedItem();
        Integer font_size = (Integer) cb_font_size.getSelectedItem();

        int font_style_no = 0;
        switch (font_style) {
            case "Plain":
                font_style_no = Font.PLAIN;
                break;
            case "Bold":
                font_style_no = Font.BOLD;
                break;
            case "Italic":
                font_style_no = Font.ITALIC;
                break;
        }

        Font font1 = new Font(font_family, font_style_no, font_size);
        textArea.setFont(font1);

        font_frame.setVisible(false);
    }
}
