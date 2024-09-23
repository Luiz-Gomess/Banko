package modelo;

import java.util.ArrayList;

public class Conta{
    private int id;
    private String data;
    private double saldo = 0;
    private ArrayList<Correntista> correntistas;

    public Conta(int id, String data){
        this.id = id;
        this.data = data;
        this.correntistas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public ArrayList<Correntista> getCorrentistas() {
        return correntistas;
    }

    public void creditar(double valor){
        this.saldo += valor;
    }
    public void debitar (double valor){
        this.saldo -= valor;
    }
    public void transferir (double valor, Conta destino){
        this.saldo -= valor;
        destino.creditar(valor);
    }

    public void adicionar (Correntista cr){
        this.correntistas.add(cr);
    }

    public void remover (Correntista cr){
        int index = 0;
        index = this.correntistas.indexOf(cr);
        this.correntistas.remove(index);
    }

    public Correntista localizar (String cpf){

        for(Correntista cr : correntistas){
            if(cr.getCpf().equals(cpf))
                return cr;
        }
        return null;
    }
}