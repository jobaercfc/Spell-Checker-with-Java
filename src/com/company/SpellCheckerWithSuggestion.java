package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;


/**
 * Created by Surid on 8/20/2016.
 */
public class SpellCheckerWithSuggestion extends JFrame implements ActionListener,KeyListener{
    JTextField txtdata;
    JTextArea txtArea;
    JButton calbtn = new JButton("Check");
    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private BufferedWriter bw;
    char quote = '"';

    Scanner scan = new Scanner(System.in);

    public SpellCheckerWithSuggestion() {
        JPanel myPanel = new JPanel();
        add(myPanel);
        myPanel.setLayout(new GridLayout(3, 1));
        txtdata = new JTextField();
        txtArea = new JTextArea();
        calbtn.setPreferredSize(new Dimension(40,40));
        calbtn.addActionListener(this);
        txtdata.addKeyListener(this);

        myPanel.add(txtdata);
        myPanel.add(txtArea);
        myPanel.add(calbtn,BorderLayout.CENTER);
    }
    int wordCounter = 0;

    public void actionPerformed(ActionEvent e)
    {
        try
        {
            fw=new FileWriter("dictionary.txt", true);
            bw=new BufferedWriter(fw);

            try
            {
                fr=new FileReader("dictionary.txt");
                br=new BufferedReader(fr);

                if (e.getSource() == calbtn) {
                    String data = txtdata.getText();//perform your operation
                    String[] parts1 = data.split(" ");
                    String s = br.readLine();
                    if(s == null)
                    {
                        int count = 0;
                        for (int i = 0; i < parts1.length; i++) {
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            System.out.println("Asking for adding data!");

                            int dialogResult = JOptionPane.showConfirmDialog(null, "The word \"" + parts1[i] + "\" is not found in the dictionary..Would you like to add it? (YES/NO)", "Warning", dialogButton);

                            if(dialogResult == JOptionPane.YES_OPTION) {
                                if(count == 0)
                                {
                                    bw.write(parts1[i]);
                                    count++;
                                    System.out.println("Data added in the dictionary!");
                                    JOptionPane.showMessageDialog(null,"\"" +parts1[i]+"\" Added!");
                                }
                                else
                                {
                                    bw.write("," + parts1[i]);
                                    System.out.println("Data added in the dictionary!");
                                    JOptionPane.showMessageDialog(null,"\"" +parts1[i]+"\" Added!");
                                }
                            }
                            if(i == parts1.length - 1)
                            {
                                JOptionPane.showMessageDialog(null,"No more word to check!");
                                txtdata.setText("");
                            }
                        }
                    }
                    else {
                        String[] parts = s.split(",");
                        for (int i = 0; i < parts1.length; i++) {
                            int count = 0;
                            for (int j = 0; j < parts.length; j++) {
                                if (parts1[i].equals(parts[j])) {
                                    count = 0;
                                    break;
                                } else count++;
                            }
                            if (count == parts.length) {
                                int dialogButton = JOptionPane.YES_NO_OPTION;
                                System.out.println("Asking for adding data!");
                                int dialogResult = JOptionPane.showConfirmDialog(null, "The word \"" + parts1[i] + "\" is not found in the dictionary..Would you like to add it? (YES/NO)", "Warning", dialogButton);
                                if (dialogResult == JOptionPane.YES_OPTION) {
                                    bw.write("," + parts1[i]);
                                    System.out.println("Data added in the dictionary!");
                                    JOptionPane.showMessageDialog(null,"\"" +parts1[i]+"\" Added!");
                                }
                            }
                            if(i == parts1.length - 1)
                            {
                                JOptionPane.showMessageDialog(null,"No more word to check!");
                                txtdata.setText("");
                            }
                        }
                    }

                }
                br.close();
            }
            catch(ArrayIndexOutOfBoundsException e1)
            {
                System.out.println("Out of bound");
            }
            catch(FileNotFoundException e1)
            {
                System.out.println("File was not found!");
            }
            catch(IOException e1)
            {
                System.out.println("No file found!");
            }
            bw.close();
        }
        catch(FileNotFoundException e1)
        {
            System.out.println("Error1!");
        }
        catch(IOException e1)
        {
            System.out.println("Error2!");
        }
    }

    public void mymethod(String str) throws IOException {
        fr = new FileReader("dictionary.txt");
        br = new BufferedReader(fr);

        String[] parts1 = str.split(" ");

        String s = br.readLine();
        String[] parts = s.split(",");

        for (int i = 0; i < parts.length; i++) {
            System.out.println(parts[i]);
            System.out.println(str);
            substring_check(parts[i],parts1[parts1.length-1]);
        }
    }

    void substring_check(String string1,String string2)
    {
        if (string1.contains(string2)) {
            txtArea.append(string1+"\n");
        }
    }

    public static void main(String args[])
    {
        SpellCheckerWithSuggestion g = new SpellCheckerWithSuggestion();
        g.setLocation(10, 10);
        g.setSize(400, 300);
        g.setTitle("Spell Checker Suggestion");
        g.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        g.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            txtArea.setText("");
            String data = txtdata.getText();
            mymethod(data);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
