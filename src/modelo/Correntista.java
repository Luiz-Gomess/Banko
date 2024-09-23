package modelo;

import java.util.ArrayList;

public class Correntista {
    private String cpf;
    private String nome;
    private String senha;
    private ArrayList<Conta> contas;

    public Correntista(String cpf, String nome, String senha){
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Conta> getContas() {
        return this.contas;
    }

    public double getSaldoTotal(){
        double saldoTotal = 0;
        for (Conta c : this.contas){
            saldoTotal += c.getSaldo();
        }
        return saldoTotal;
    }

    public void adicionar(Conta c){
        this.contas.add(c);
    }
    public void remover(Conta c){
        int index = 0;
        index = this.contas.indexOf(c);
        this.contas.remove(index);
    }

    public Conta localizar(int id){
        for(Conta c : contas){
            if(c.getId() == id)
                return c;
        }
        return null;
    }

    public ArrayList<Integer> ContasIds (){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Conta c : this.contas){
            ids.add(c.getId());
        }
        return ids;
    }
    
}
