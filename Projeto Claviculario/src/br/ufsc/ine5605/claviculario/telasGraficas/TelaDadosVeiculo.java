/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.claviculario.telasGraficas;

import br.ufsc.ine5605.claviculario.controladores.ControladorVeiculos;
import br.ufsc.ine5605.claviculario.valueObjects.VeiculoVO;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author pablo
 */
public class TelaDadosVeiculo extends JFrame implements ActionListener {

    private ControladorVeiculos controladorVeiculos;
    private JLabel lbPlaca;
    private JLabel lbMarca;
    private JLabel lbModelo;
    private JLabel lbAno;
    private JLabel lbKmAtual;
    private JLabel lbDisponível;
    
    private JFormattedTextField tfPlaca;
    private JTextField tfMarca;
    private JTextField tfModelo;
    private JFormattedTextField tfAno;
    private JFormattedTextField tfKmAtual;
    private JTextField lbDisponibilidade;
    private JLabel infoTela;
    private JButton btCadastrar;
    
    public TelaDadosVeiculo(){
        this.controladorVeiculos = controladorVeiculos;
        inicia();
    }
    
    public void inicia(){
        
        lbPlaca = new JLabel();
        lbMarca = new JLabel();
        lbModelo = new JLabel();
        lbAno = new JLabel();
        lbKmAtual = new JLabel();
        lbDisponível = new JLabel();
        infoTela = new JLabel();
        
        try{
            tfPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
        } catch (ParseException ex) {
            Logger.getLogger(TelaDadosFuncionario.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        tfMarca = new JTextField();
        tfModelo = new JTextField();
        
        try{
            tfAno = new JFormattedTextField(new MaskFormatter("####"));
        } catch (ParseException ex) {
            Logger.getLogger(TelaDadosFuncionario.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
        tfKmAtual = new JFormattedTextField(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        lbDisponibilidade = new JTextField();
        infoTela = new JLabel();
        btCadastrar = new JButton();

        lbPlaca.setText("Placa: ");
        lbPlaca.setBounds(50, 50, 100, 50);

        tfPlaca.setBounds(170, 60, 120, 25);
        
        lbMarca.setText("Marca: ");
        lbMarca.setBounds(50, 100, 100, 50);    
        
        tfMarca.setBounds(170, 110, 120, 25);

        lbModelo.setText("Modelo: ");
        lbModelo.setBounds(50, 150, 100, 50);  
        
        tfModelo.setBounds(170, 160, 120, 25);

        lbAno.setText("Ano: ");
        lbAno.setBounds(50, 200, 100, 50); 
        
        tfAno.setBounds(170, 210, 120, 25);

        lbKmAtual.setText("KM Atual: ");
        lbKmAtual.setBounds(50, 250, 100, 50); 
        
        tfKmAtual.setBounds(170, 260, 120, 25);

        lbDisponível.setText("Disponibilidade: ");
        lbDisponível.setBounds(50, 300, 100, 50); 

        lbDisponibilidade.setBounds(170, 310, 120, 25);
        lbDisponibilidade.setEditable(false);
        
        btCadastrar.setText("Cadastrar");
        btCadastrar.setBounds(50, 360, 120, 30);
        btCadastrar.addActionListener(this);
        
        infoTela.setForeground(Color.red);
        infoTela.setBounds(50, 400, 300, 25);
        
        Container container = getContentPane();
        container.setLayout(null);

        container.add(lbPlaca);
        container.add(lbMarca);
        container.add(lbModelo);
        container.add(lbAno);
        container.add(lbKmAtual);
        container.add(lbDisponível);
        
        container.add(tfPlaca);
        container.add(tfMarca);
        container.add(tfModelo);
        container.add(tfAno);
        container.add(tfKmAtual);
        container.add(lbDisponibilidade);
        container.add(infoTela);
        container.add(btCadastrar);
        
        setSize(680,460);
        setLocationRelativeTo(null);
        setResizable(false);
    
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==btCadastrar){
            if(tfPlaca.getText().equals("   -    ")){
                infoTela.setText("Campo placa está em branco.");
                infoTela.setForeground(Color.BLUE);
                repaint();
            }else if(tfMarca.getText().equals("")){
                infoTela.setText("Campo marca está em branco.");
                infoTela.setForeground(Color.green);
                repaint();
            }else if(tfModelo.getText().equals("")){
                infoTela.setText("Campo modelo está em branco.");
                infoTela.setForeground(Color.ORANGE);
                repaint();
            }else if(tfAno.getText().equals("    ")){
                infoTela.setText("Campo ano está em branco.");
                infoTela.setForeground(Color.MAGENTA);
                repaint();
            }else if(tfKmAtual.getText().equals("")){
                infoTela.setText("Campo KM Atual está em branco.");
                infoTela.setForeground(Color.MAGENTA);
                repaint();
             }else{
                infoTela.setText("");
                infoTela.setForeground(Color.MAGENTA);
                
                VeiculoVO veiculoVO = new VeiculoVO();
                veiculoVO.placa = tfPlaca.getText();
                veiculoVO.marca = tfMarca.getText();
                veiculoVO.modelo = tfModelo.getText();
                veiculoVO.ano = Integer.parseInt(tfAno.getText());
                veiculoVO.kmAtual = Integer.parseInt(tfAno.getText());
                veiculoVO.chaveClaviculario = true;
                
                lbDisponibilidade.setText("Disponível");
                
                infoTela.setForeground(Color.red);
                infoTela.setText(ControladorVeiculos.getInstance().cadastrarVeiculo(veiculoVO));
                
            }
        }
    }
    
}
    
