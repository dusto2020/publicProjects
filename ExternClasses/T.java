package ExternClasses;

public class T<Z, S> {

    private Z zeile;
    private S spalte;

    public T(Z zeile, S spalte) {
        this.zeile = zeile;
        this.spalte = spalte;
    }

    public Z getZeile() {
        return zeile;
    }

    public void setZeile(Z zeile) {
        this.zeile = zeile;
    }

    public S getSpalte() {
        return spalte;
    }

    public void setSpalte(S spalte) {
        this.spalte = spalte;
    }

    @Override
    public String toString() {
        return "T{" +
                "zeile=" + zeile +
                ", spalte=" + spalte +
                '}';
    }
}
