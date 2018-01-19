
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kira Katou
 */
public class ChatSwing extends javax.swing.JFrame {
    private final Queue<String> requests;
    private final Queue<String> responses;
    private final List<String> commands; 
    private String lastWhisperer;
    /**
     * Creates new form ChatSwing
     */
    public ChatSwing() {
        initComponents();
        this.commands = asList("/name");
        this.requests = new ConcurrentLinkedQueue<>();
        this.responses = new ConcurrentLinkedQueue<>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtChat = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMsg = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setMaximumSize(new java.awt.Dimension(400, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 600));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtChat.setEditable(false);
        txtChat.setMinimumSize(new java.awt.Dimension(400, 20));
        jScrollPane3.setViewportView(txtChat);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 472;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane3, gridBagConstraints);

        txtMsg.setColumns(20);
        txtMsg.setRows(5);
        jScrollPane2.setViewportView(txtMsg);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 286;
        gridBagConstraints.ipady = 76;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jButton1.setText("Enter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.ipady = 76;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jButton1, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String message = this.txtMsg.getText();
        sendMessage(message);
        try {
            if(commands.stream().filter(x -> message.startsWith(x)).count() <= 0){
                if(message.startsWith("/whisper") ){
                    if(message.split(" ", 3).length == 3){
                        setTextToTextPane("Whisp to " + message.split(" ")[1] 
                            + "\n" + message.split(" ", 3)[2] + "     \n", false);
                    }else{
                        setTextToTextPane("Help "  + "\n     "
                                + "/whisper [name] [message]\n", true);
                    }
                    
                }else if(message.startsWith("/r") ){
                    if(lastWhisperer != null){
                        setTextToTextPane("Whisp to " + lastWhisperer
                            + "\n" + message.split(" ")[1] + "     \n", false);
                    }else{
                        setTextToTextPane("Error " + "\n     "
                                + "The user you are trying to contact not available\n", true);
                    }
                    
                }else {
                    setTextToTextPane("Me\n" + this.txtMsg.getText() + "     \n", false);
                }
                
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(ChatSwing.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtMsg.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed
   
    public void setTextToTextPane(String txt,boolean received) throws BadLocationException{
        StyledDocument doc = txtChat.getStyledDocument();
        if(received){   
            SimpleAttributeSet left = new SimpleAttributeSet();
            StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(doc.getLength(), 1, left, false);
            doc.insertString(doc.getLength(), txt, left);
            
            
        }else{
            SimpleAttributeSet right = new SimpleAttributeSet();
            StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
            doc.setParagraphAttributes(doc.getLength(), 1, right, false);
            doc.insertString(doc.getLength(), txt, right);
        }

    }
    public void sendMessage(String message){
        synchronized (requests) {
            requests.offer(message);
            requests.notify();
        }
    }
    
    public void initialize() throws IOException, BadLocationException{
        final Socket socket = new Socket("localhost", 1286);
        final CountDownLatch doneSignal = new CountDownLatch(2);

        System.out.println("Connected to the server");

        // start input and output thread
        final ThreadedInputStream inputStream = new ThreadedInputStream(socket, responses, doneSignal);
        final ThreadedOutputStream outputStream = new ThreadedOutputStream(socket, requests, doneSignal);

        inputStream.start();
        outputStream.start();
        
        while (inputStream.isAlive() && outputStream.isAlive()){
            String request = null;
            synchronized (responses) {
                request = responses.poll();
            }
            if(request != null){
                int chara = request.indexOf(":");
                System.out.println(request);
                if (chara != -1) {
                    if(request.startsWith("/whisperFrom")){
                            lastWhisperer = request.split(" ")[1].substring(0, request.split(" ")[1].length() - 1);
                            String message = "Whips from " + lastWhisperer + "\n     " 
                                    + request.substring(chara + 1, request.length()) + "\n";
                            setTextToTextPane(message, true);
                    }else {
                            String message = request.substring(0 , chara) + "\n     " 
                                    + request.substring(chara + 1, request.length()) + "\n";
                            setTextToTextPane(message, true);
                    }
                }else {
                    String message = "System\n     " 
                                    + request.substring(chara + 1, request.length()) + "\n";
                    setTextToTextPane(message, true);
                }
            }
            synchronized (responses) {
                try {
                    responses.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
        }

        System.out.println("Disconnected from server");
        socket.close();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane txtChat;
    private javax.swing.JTextArea txtMsg;
    // End of variables declaration//GEN-END:variables
}
