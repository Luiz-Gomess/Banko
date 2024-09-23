package modelo;

public class ContaEspecial extends Conta{
    private double limite;

    public ContaEspecial(int id, String data, double limite) {
        super(id, data);
        this.limite = limite;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }


}
