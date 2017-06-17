package br.ufsc.ine5605.claviculario.controladores;

import br.ufsc.ine5605.claviculario.entidades.Funcionario;
import br.ufsc.ine5605.claviculario.enums.EntradaSaida;
import br.ufsc.ine5605.claviculario.zzantigos.telas.TelaFuncionarios;
import br.ufsc.ine5605.claviculario.telasGraficas.TelaGraficaDadosFuncionario;
import br.ufsc.ine5605.claviculario.valueObjects.FuncionarioVO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Pablo Vicente
 */

public class ControladorFuncionarios{
    //Atributos
    private static ControladorFuncionarios instance;
    private final TelaFuncionarios telaFuncionarios;
    private final HashMap<Integer, Funcionario> funcionarios;
    private final TelaGraficaDadosFuncionario telaGraficaDadosFuncionario;
        
    //Contrutor
    private ControladorFuncionarios(){
        funcionarios = new HashMap<>();
        telaFuncionarios = new TelaFuncionarios();
        telaGraficaDadosFuncionario = new TelaGraficaDadosFuncionario();
    }

    
    public void carregarTelaGraficaDadosFuncionarios(String titulo){
        telaGraficaDadosFuncionario.setTitle(titulo);
        telaGraficaDadosFuncionario.setVisible(true);
    }
    //Metodos Operacionais
    public String cadastrarFuncionario(FuncionarioVO funcionarioVO){
        
        String retornoCadastroFuncionario = EntradaSaida.MATRICULADUPLICA.getMensagem();
        
        if(!validarMatricula(funcionarioVO.matricula)){
            
            Funcionario funcionario = new Funcionario(funcionarioVO.matricula, funcionarioVO.nome, funcionarioVO.dataNascimento, funcionarioVO.telefone, funcionarioVO.cargo);
            funcionarios.put(funcionario.getMatricula(), funcionario);
            
            retornoCadastroFuncionario = EntradaSaida.FUNCIONARIOCADASTRADO.getMensagem();
        }
        
        return retornoCadastroFuncionario;
    }
    
    public String excluirFuncionario(int matricula){
        String retornoExclusao = EntradaSaida.MATRICULAINVALIDA.getMensagem();
        if(validarMatricula(matricula)){
            if(getFuncionario(matricula).getVeiculoPendente()!=null){
                retornoExclusao = EntradaSaida.FUNCIONARIPENDENTE.getMensagem();
            }else{
                funcionarios.remove(getFuncionario(matricula).getMatricula());
                retornoExclusao = EntradaSaida.FUNCIONARIOEXCLUIDO.getMensagem();
            }
        }
        return retornoExclusao;
    }
    
    public String pesquisarFuncionario(int matricula){
        String retornoPesquisa = EntradaSaida.MATRICULAINVALIDA.getMensagem();
        if(validarMatricula(matricula)){
            Funcionario funcionario = getFuncionario(matricula);
            String nome = funcionario.getNome();
            
            Calendar nasc = funcionario.getDataNascimento();
            Date dataNascimento = nasc.getTime();
            SimpleDateFormat dateF = new SimpleDateFormat();
            
            String telefone = funcionario.getTelefone();
            String cargo = funcionario.getCargo();
            boolean bloqueado = funcionario.isBloqueado();
            String veiculoPendente = funcionario.getVeiculoPendente();
            retornoPesquisa = matricula+","+nome+","+dataNascimento+","+telefone+","+cargo+","+bloqueado+","+veiculoPendente;
            
    }
        return retornoPesquisa;
    }
    
    public String atualizarCadastroFuncionario(FuncionarioVO funcionarioVO){
        
        String retornoCadastro = EntradaSaida.MATRICULAINVALIDA.getMensagem();
        int matricula = funcionarioVO.matricula;
        String nome = funcionarioVO.nome;
        Calendar dataNascimento = funcionarioVO.dataNascimento;
        String telefone = funcionarioVO.telefone;
        String cargo = funcionarioVO.cargo;
        if(validarMatricula(matricula)){
           
           Funcionario funcionario = getFuncionario(matricula);
           funcionario.setNome(nome);
           funcionario.setDataNascimento(dataNascimento);
           funcionario.setTelefone(telefone);
           funcionario.setCargo(cargo);
           retornoCadastro = EntradaSaida.FUNCIONARIOATUALIZADO.getMensagem();
        } 
        return retornoCadastro;
    }
    
    public String cadastrarVeiculosNoFuncionario(int matricula, String placa){
        String retornoCadastroVeiculoNoFuncionario = EntradaSaida.MATRICULAINVALIDA.getMensagem();
        if(validarMatricula(matricula)){
            if(!(getFuncionario(matricula).getCargo().equals(EntradaSaida.DIRETOR.getMensagem()))) {
                if(!ControladorPrincipal.getInstance().validarPlaca(placa)){
                    retornoCadastroVeiculoNoFuncionario = EntradaSaida.PLACAINEXISTENTE.getMensagem();
                }else{
                    Funcionario funcionario = getFuncionario(matricula);
                    if(funcionario.getVeiculos().contains(placa)){
                        retornoCadastroVeiculoNoFuncionario = EntradaSaida.VEICULOASSOCIADO.getMensagem();
                    }else{
                        funcionario.getVeiculos().add(placa);
                        retornoCadastroVeiculoNoFuncionario = EntradaSaida.VEICULOLIBERADO.getMensagem();
                    }
                }
            } else {
                retornoCadastroVeiculoNoFuncionario = EntradaSaida.FUNCIONARIOEHDIRETOR.getMensagem();
            }
        }
        return retornoCadastroVeiculoNoFuncionario;
    }
    
    public boolean validarMatricula(int matricula){
        return getFuncionario(matricula)!=null;
    }
    
    public FuncionarioVO getDadosFuncionario(int matricula){
        FuncionarioVO funcionarioVO = null;
        for(Funcionario funcionario : funcionarios.values()){
            if(funcionario != null && funcionario.getMatricula()==matricula){
                funcionarioVO.matricula = funcionario.getMatricula();
                funcionarioVO.nome = funcionario.getNome();
                funcionarioVO.dataNascimento = funcionario.getDataNascimento();
                funcionarioVO.telefone = funcionario.getTelefone();
                funcionarioVO.cargo = funcionario.getCargo();
                funcionarioVO.bloqueado = funcionario.isBloqueado();
                funcionarioVO.veiculoPendente = funcionario.getVeiculoPendente();
                funcionarioVO.veiculos = funcionario.getVeiculos();
                break;
            }
       }
        return funcionarioVO;
    }
    
    public Funcionario getFuncionario(int matricula){
        Funcionario funcionarioRetorno= null;
        for(Funcionario funcionario : funcionarios.values()){
            if(funcionario != null && funcionario.getMatricula()==matricula){
                funcionarioRetorno = funcionario;
                break;
            }
       }
            return funcionarioRetorno;
    }
    
    public String pedirPlacaVeiculo(){
        return ControladorPrincipal.getInstance().pedirPlacaVeiculo();
    }
    
    public void excluirVeiculoTodosFuncionarios(String placa){
        for(Funcionario funcionario : funcionarios.values()){
            if(funcionario.getVeiculos().contains(placa)){
                funcionario.getVeiculos().remove(placa);
            }
        }
    }
    
    public int quantidadeVeiculosFuncionarios(int matricula){
        return getFuncionario(matricula).getVeiculos().size();
    }
    
    public void carregarMenuPrincipal(){
        ControladorPrincipal.getInstance().carregarMenuPrincipal();
    }
    
    //public void exibirListaVeiculosFuncionario(int matricula){
        //telaFuncionarios.exibirTelaVeiculosAutorizados();
        //ControladorPrincipal.getInstance().exibirVeiculosAutorizados(matricula);
    //}
    
    public void exibirDadosDeTodosFuncionarios(){
        for(Funcionario funcionario:funcionarios.values()){
            telaFuncionarios.exibirDadosDoFuncionario(pesquisarFuncionario(funcionario.getMatricula()));
        }
    }
    
    public HashMap getFuncionarios() {
        HashMap<Integer, FuncionarioVO> funcionariosVO = new HashMap<>();
        
        for(Integer chave : funcionarios.keySet()) {
            Funcionario funcionario = funcionarios.get(chave);
            FuncionarioVO funcionarioVO = new FuncionarioVO();
            funcionarioVO.matricula = funcionario.getMatricula();
            funcionarioVO.nome = funcionario.getNome();
            funcionarioVO.dataNascimento = funcionario.getDataNascimento();
            funcionarioVO.telefone = funcionario.getTelefone();
            funcionarioVO.cargo = funcionario.getCargo();
            funcionarioVO.bloqueado = funcionario.isBloqueado();
            funcionarioVO.veiculos = funcionario.getVeiculos();
            funcionariosVO.put(funcionarioVO.matricula, funcionarioVO);
        }
        return funcionariosVO;
    }
    
    public Calendar pedirDataNascimento(){
        return telaFuncionarios.pedirDataNascimento();
    }
    
    public static ControladorFuncionarios getInstance() {
        if(instance == null){
            instance = new ControladorFuncionarios();
        }
        return instance;
    }

    
}