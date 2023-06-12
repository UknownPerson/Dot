package xyz.dot.launchwrapper.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DialogDownload extends JDialog {
    public DialogDownload(Window owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        text = new JLabel();
        progressBar = new JProgressBar();
        buttonBar = new JPanel();
        cancelButton = new JButton();

        setTitle("Downloading assets...");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            {

                text.setText("");

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(text, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                                .addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                            .addContainerGap())
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(text)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                cancelButton.setText("Cancel");
                cancelButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(1);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

    }

    public void setText(String text) {
        this.text.setText(text);
    }

    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel text;
    private JProgressBar progressBar;
    private JPanel buttonBar;
    private JButton cancelButton;

    public JProgressBar getProgressBar() {
        return progressBar;
    }

}
