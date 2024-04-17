package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TextEditor extends JFrame implements ActionListener {
    private JTextPane textPane; // Cambio a JTextPane
    private JFileChooser fileChooser;
    private JButton boldButton;
    private JButton italicButton;
    private JButton underlineButton;
    private JButton fontTypeButton;
    private JButton fontSizeButton;
    private JButton colorButton;

    private final String[] fontNames = {"Arial", "Times New Roman", "Courier New", "Verdana", "Tahoma"};
    private final Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};

    public TextEditor() {
        setTitle("Editor de texto");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textPane = new JTextPane(); // Cambio a JTextPane
        JScrollPane scrollPane = new JScrollPane(textPane); // Cambio a JTextPane
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem openItem = new JMenuItem("Abrir");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Guardar");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();

        boldButton = new JButton("Negrita");
        italicButton = new JButton("Cursiva");
        underlineButton = new JButton("Subrayado");
        fontTypeButton = new JButton("Fuente");
        fontSizeButton = new JButton("Tamaño");
        colorButton = new JButton("Color");

        boldButton.addActionListener(this);
        italicButton.addActionListener(this);
        underlineButton.addActionListener(this);
        fontTypeButton.addActionListener(this);
        fontSizeButton.addActionListener(this);
        colorButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(boldButton);
        buttonPanel.add(italicButton);
        buttonPanel.add(underlineButton);
        buttonPanel.add(fontTypeButton);
        buttonPanel.add(fontSizeButton);
        buttonPanel.add(colorButton);
        add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String selectedText = textPane.getSelectedText(); // Cambio a JTextPane
        if (selectedText != null && !selectedText.isEmpty()) {
            if (e.getSource() == boldButton) {
                applyStyle(Font.BOLD);
            } else if (e.getSource() == italicButton) {
                applyStyle(Font.ITALIC);
            } else if (e.getSource() == underlineButton) {
                applyUnderline(true);
            } else if (e.getSource() == fontTypeButton) {
                applyFontType();
            } else if (e.getSource() == fontSizeButton) {
                applyFontSize();
            } else if (e.getSource() == colorButton) {
                applyTextColor();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione algún texto para aplicar los cambios.", "Texto no seleccionado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void applyStyle(int style) {
        StyledDocument doc = textPane.getStyledDocument(); // Cambio a JTextPane
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBold(attrs, (style & Font.BOLD) != 0);
        StyleConstants.setItalic(attrs, (style & Font.ITALIC) != 0);
        doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), attrs, false);
    }

    private void applyUnderline(boolean underline) {
        StyledDocument doc = textPane.getStyledDocument(); // Cambio a JTextPane
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setUnderline(attrs, underline);
        doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), attrs, false);
    }

    private void applyFontType() {
        String selectedFont = (String) JOptionPane.showInputDialog(this, "Selecciona la fuente", "Fuente seleccionada",
                JOptionPane.PLAIN_MESSAGE, null, fontNames, textPane.getFont().getFamily()); // Cambio a JTextPane
        if (selectedFont != null) {
            StyledDocument doc = textPane.getStyledDocument(); // Cambio a JTextPane
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attrs, selectedFont);
            doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), attrs, false);
        }
    }

    private void applyFontSize() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el tamaño de fuente:");
        try {
            int size = Integer.parseInt(input);
            if (size > 0) {
                StyledDocument doc = textPane.getStyledDocument(); // Cambio a JTextPane
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setFontSize(attrs, size);
                doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), attrs, false);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tamaño de fuente no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyTextColor() {
        Color selectedColor = (Color) JOptionPane.showInputDialog(this, "Seleccione el color del texto", "Color seleccionado",
                JOptionPane.PLAIN_MESSAGE, null, colors, Color.BLACK);
        if (selectedColor != null) {
            StyledDocument doc = textPane.getStyledDocument(); // Cambio a JTextPane
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, selectedColor);
            doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), attrs, false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TextEditor();
        });
    }
}
