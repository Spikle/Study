import net.sf.clipsrules.jni.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
public class Main implements ActionListener {

    class ProgramRunner extends SwingWorker<Integer,Integer>{
        Environment workEnv;
        String loadFile;
        JButton restartButton;
        public ProgramRunner( Environment theEnv, String file, JButton theRestartButton ){
            this.workEnv = theEnv;
            this.loadFile = file;
            this.restartButton = theRestartButton;
        }

        @Override
        protected Integer doInBackground() throws Exception{
            workEnv.clear();
            if(loadFile != null){
                try{
                    workEnv.load(loadFile);
                } catch (CLIPSLoadException e) { e.printStackTrace(); return 0; }
            }
            workEnv.reset();
            workEnv.run();
            return 1;
        }

        protected void done() {
            try {
                restartButton.setEnabled(true);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    JFrame labFrame;
    RouterTextArea labTextArea;
    Environment labEnv;
    JButton restartButton, yesButton, noButton;

    public Main(){
        labEnv = new Environment();
        labFrame = new JFrame("GUI CLIPS");
        labFrame.getContentPane().setLayout(new BoxLayout(labFrame.getContentPane(), BoxLayout.Y_AXIS));
        labFrame.setSize(800, 500);
        labFrame.setMinimumSize(new Dimension(400, 250));
        labFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            labTextArea = new RouterTextArea(labEnv);
        } catch (Exception e){ e.printStackTrace(); return; }

        JScrollPane labPane = new JScrollPane(labTextArea);
        labPane.setPreferredSize(new Dimension(800, 500));
        JPanel restartPanel = new JPanel();
        restartPanel.setPreferredSize(new Dimension(800,50));

        restartButton = new JButton("Restart");
        restartButton.setEnabled(false);
        restartButton.setActionCommand("Restart");
        restartButton.addActionListener(this);

        yesButton = new JButton("YES");
        yesButton.setEnabled(true);
        yesButton.setActionCommand("YES");
        yesButton.addActionListener(this);

        noButton = new JButton("NO");
        noButton.setEnabled(true);
        noButton.setActionCommand("NO");
        noButton.addActionListener(this);

        restartPanel.add(restartButton);

        GroupLayout layout = new GroupLayout(restartPanel);
        restartPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(restartButton).addComponent(yesButton).addComponent(noButton));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(restartButton).addComponent(yesButton).addComponent(noButton));

        labFrame.getContentPane().add(restartPanel);
        labFrame.getContentPane().add(labPane);
        labFrame.pack();
        labFrame.setVisible(true);

        runLab();
    }

    public void runLab(){
        String filename = "GameDevelopment.clp";
        new ProgramRunner(labEnv,filename,restartButton).execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }

    private void onActionPerformed(ActionEvent ae) throws Exception{
        Robot rb=new Robot();
        if (ae.getActionCommand().equals("Restart")){
            labTextArea.setText("");
            restartButton.setEnabled(false);
            labTextArea.grabFocus();
            runLab();
        }
        if (ae.getActionCommand().equals("YES")){
            labTextArea.appendChars("yes");
            labTextArea.grabFocus();
        }
        if (ae.getActionCommand().equals("NO")){
            labTextArea.appendChars("no");
            labTextArea.grabFocus();
        }
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try{
            onActionPerformed(ae);
        }catch (Exception e){ e.printStackTrace(); }
    }
}
