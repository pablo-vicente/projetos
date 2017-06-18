/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.claviculario.persistencia;

import br.ufsc.ine5605.claviculario.entidades.Veiculo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author pablo
 */
public class MapeadorVeiculo {
    private HashMap<Integer, Veiculo> listaVeiculos;
    private String filename = "src/br/ufsc/ine5605/claviculario/persistencia/files/veiculo.rtm";
    
    public MapeadorVeiculo(){
        listaVeiculos = new HashMap<>();
        load();
    }
    
    public Veiculo get(String placa){
        return listaVeiculos.get(placa);
    }
    
    public void put(Veiculo veiculo){
        //String[] dados = veiculo.getPlaca().split("-");
        //int parte1 = Integer.parseInt(dados[0]);
        //idPlaca;
        listaVeiculos.put(veiculo.getPlaca(),veiculo);
        
        persist();
    }
    
    public void load(){
        try{
            FileInputStream entrada = new FileInputStream(filename);
            ObjectInputStream objetoEntrada = new ObjectInputStream(entrada);

            this.listaVeiculos = (HashMap<Integer,Veiculo>) objetoEntrada.readObject();

            objetoEntrada.close();
            entrada.close();

            objetoEntrada = null;
            entrada=null;
        
        }catch(FileNotFoundException ae){
            System.out.println(ae);
        }catch(IOException ae){
            System.out.println(ae);
        }catch(ClassNotFoundException ae){
            System.out.println(ae);
        }
    }
    
    public void persist(){
        try{
            FileOutputStream saida = new FileOutputStream(filename);
            ObjectOutputStream objetoSaida = new ObjectOutputStream(saida);

            objetoSaida.flush();
            saida.flush();

            objetoSaida=null;
            saida=null;
        }catch(FileNotFoundException ae){
            System.out.println(ae);
        }catch(IOException ae){
            System.out.println(ae);
        }
        
    }
    
    public void remove(String placa){
        listaVeiculos.remove(placa);
    }
        
    public Collection getList(){
        return listaVeiculos.values();
    }
}
