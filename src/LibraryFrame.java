package pelxy_finalin_finali;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryFrame extends JFrame {
    private JPanel panel;
    private JLabel booksLabel;

    public LibraryFrame() {
        setTitle("Library");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        booksLabel = new JLabel("Books:");
        booksLabel.setBounds(20, 20, 100, 25);
        add(booksLabel);

        panel = new JPanel();
        panel.setBounds(20, 50, 340, 200);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        loadBooks();
    }

    private void loadBooks() {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");

                JLabel bookLabel = new JLabel(title + " by " + author + " - $" + price);
                JButton buyButton = new JButton("Buy");

                buyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Successfully purchased!");
                    }
                });

                JPanel bookPanel = new JPanel();
                bookPanel.add(bookLabel);
                bookPanel.add(buyButton);

                panel.add(bookPanel);
            }

            panel.revalidate();
            panel.repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
