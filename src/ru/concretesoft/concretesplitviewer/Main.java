/*
 * Main.java
 *
 * Created on 27 ���� 2006 �., 14:30
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import ru.spb.ConcreteSoft.tipWindow.TipWindow;

/**
 *
 * @author  ��������� ������
 *
 * �������� ���� ���������.
 * �������� ��� ������, ��� �������� "�������"; ��� ������: ���� ��� ������ ������, ������ ��� ������ �����������;
 * ���� ��� ����������� ��������. 
 */
public class Main extends javax.swing.JFrame {
    private JFileChooser jFC; // ������ ��� ������ ������
 
    private GroupListModel groupListModel; // ������ ������ ��� ������ �����
    private AthleteListModel lM2; // ������ ������ ��� ������ �����������
    private GroupSelectionModel gSM; // ������ ������ ��� �����
//    private int[] viewSplits; // ������ ���������� ������ ������� ����������� � ���������
    private SplitViewer[] viewers = new SplitViewer[]{
        new StandardSplitViewer(),
        new SecondBestSplitViewer()
    }; // ������ ��������� ��������� ���������
    private TipThreadSplitViewer tipThread;
    private TipWindow tipWindow;
    /** Creates new form Main */
    public Main() {
        tipWindow = new TipWindow();
        initComponents();
        
        
        // ������������� ���������� �������
        jFC = new JFileChooser();
        
        groupListModel = new GroupListModel();
        lM2 = new AthleteListModel(getGraphics().getFontMetrics());
        lM2.setGroupsList(jList1);
        // ���������� ��������� ��������� ��������� � ���������� ������
        for(int i=0;i<viewers.length;i++){
            jComboBox1.addItem(viewers[i]);
            viewers[i].setModel(lM2);
        }

        
        jPanel1.add((javax.swing.JPanel)jComboBox1.getSelectedItem());
        
//        tipThread = new TipThreadSplitViewer(tipWindow, (SplitViewer)jComboBox1.getSelectedItem());
//        tipThread.start();
        // ������� ������� ��� �������
        jList1.setModel(groupListModel);
        gSM = new GroupSelectionModel(groupListModel);
        gSM.addListSelectionListener(lM2);
        groupListModel.addListDataListener(gSM);
        jList1.setSelectionModel(gSM);
        jList2.setModel(lM2);
        jList2.setSelectionModel(lM2);
        

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ConcreteSplitViewer");
        jButton1.setText(java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("Open_OSV_file"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(jButton1, gridBagConstraints);

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jComboBox1, gridBagConstraints);

        jButton2.setText(java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("Open_SFR_file"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(jButton2, gridBagConstraints);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 200));
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        jPanel2.add(jScrollPane1);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(200, 200));
        jList2.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(jList2);

        jPanel2.add(jScrollPane2);

        jSplitPane1.setRightComponent(jPanel2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jSplitPane1.setLeftComponent(jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jSplitPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * �����, ���������� �� ������� �� ������, ��� �������� SFR �������
     * 
     * ���������� ���������� ���� ������ �����, � ���� ���� ������, �� ������ ���� ����
     *
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Vector<String> exts = new Vector<String>();
        exts.add("txt");
        File f = showFileChooser(exts);
        if(f != null){
            try{
                SplitReader oR = new SFReader(f);
                readSplits(oR);
            }
            catch(IOException e){
                System.out.println(e);
            }
        }
        else;
    }//GEN-LAST:event_jButton2ActionPerformed
    
    // ���������� ������ ����� �� ������ ��������� �������
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        SplitViewer sV = (SplitViewer)jComboBox1.getSelectedItem();
        
        if((evt.getButton()==evt.BUTTON2)||(evt.getMouseModifiersText(evt.getModifiers()).equals("Shift+Button1"))){
            lM2.restoreAllSplits();
        }
        else if(evt.getButton()==evt.BUTTON1){
            
            sV.removeSplit(evt.getX());
        }
    }//GEN-LAST:event_jPanel1MouseClicked
    
    
    // ���������� ������ ������ ���������
    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        jPanel1.removeAll();
        if(tipThread!=null)
            tipThread.finish();
        else;
        
        if(evt.getStateChange()==evt.SELECTED){
            SplitViewer sV = (SplitViewer)evt.getItem();
            jPanel1.add((javax.swing.JPanel)jComboBox1.getSelectedItem());
            tipThread = new TipThreadSplitViewer(tipWindow, (SplitViewer)jComboBox1.getSelectedItem());
            tipThread.start();
            this.validate();
            repaint();
        }else;
    }//GEN-LAST:event_jComboBox1ItemStateChanged
    
    // ���������� ��������� ������ �����������
    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged


    }//GEN-LAST:event_jList2ValueChanged
    
    
    // ���������� ��������� ������ �����
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

    }//GEN-LAST:event_jList1ValueChanged

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked

    }//GEN-LAST:event_jList2MouseClicked

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked

    }//GEN-LAST:event_jList1MouseClicked
    
    /**
     * �����, ���������� �� ������� �� ������, ��� �������� OSV �������
     * 
     * ���������� ���������� ���� ������ �����, � ���� ���� ������, �� ������ ���� ����
     *
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Vector<String> exts = new Vector<String>();
        exts.add("osv");
        File f = showFileChooser(exts);
        if(f != null){
            try{
                SplitReader oR = new OSVReader(f);
                readSplits(oR);
            }
            catch(IOException e){
                System.out.println(e);
            }
        }
        else;
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /** 
     * ������ ������� � ������� ������
     *
     */
    private void readSplits(SplitReader sR){
        groupListModel.setGroups(sR.getAllGroups());
    }
    
    private File showFileChooser(Vector<String> exts){
        clear();
        
        // ������� ���������� ������� ������ � ����� ����������� ���� ������ �����
        MyFileFilter filter = new MyFileFilter(exts);
        
        jFC.setFileFilter(filter);
        int val = jFC.showOpenDialog(this);
        File f = null;
        // ���� ������ ������ OK, �� ��������� ���� � ��������� ������ �����
        if(val == jFC.APPROVE_OPTION){
            f = jFC.getSelectedFile();
        }
        jFC.removeChoosableFileFilter(filter);
        return f;
    }
    /** "���������" ���� ���������� ���������� �� ����������� �����
     *
     */
    private void clear(){
        lM2.setAthletes(null);
        lM2.setViewSplits(null);
        jList1.clearSelection();
        jList1.ensureIndexIsVisible(0);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
   
   
}
